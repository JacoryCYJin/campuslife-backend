package org.example.campuslifebackend.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.annotation.Resource;
import org.example.campuslifebackend.converter.UserConverter;
import org.example.campuslifebackend.domain.dto.UserLoginDTO;
import org.example.campuslifebackend.domain.po.UserLoginPO;
import org.example.campuslifebackend.domain.po.UserPO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;
import org.example.campuslifebackend.mapper.UserMapper;
import org.example.campuslifebackend.service.UserService;
import org.example.campuslifebackend.service.WxService;
import org.example.campuslifebackend.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private WxService wxService;
    
    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private UserConverter userConverter;
    
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String identifier = userLoginDTO.getIdentifier();
        
        // 判断是否为手机号（简单判断：11位数字）
        if (identifier != null && identifier.matches("^1\\d{10}$")) {
            // 手机号登录
            return loginByPhone(userLoginDTO);
        } else {
            // 微信登录
            return loginByWx(userLoginDTO);
        }
    }
    
    @Override
    @Transactional
    public boolean bindPhone(String userNanoid, String phoneNumber, String password) {
        // 查询用户是否存在
        UserPO userPO = userMapper.getUserByNanoid(userNanoid);
        if (userPO == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 查询手机号是否已被绑定
        UserLoginPO existLoginPO = userMapper.getUserLoginByPhone(phoneNumber);
        if (existLoginPO != null) {
            throw new RuntimeException("该手机号已被绑定");
        }
        
        // 创建手机号登录信息
        UserLoginPO userLoginPO = new UserLoginPO();
        userLoginPO.setNanoid(NanoIdUtils.randomNanoId());
        userLoginPO.setUserNanoid(userNanoid);
        userLoginPO.setLoginType(1); // 手机号登录
        userLoginPO.setIdentifier(phoneNumber);
        userLoginPO.setCredential(DigestUtils.md5DigestAsHex(password.getBytes()));
        userLoginPO.setCreateTime(LocalDateTime.now());
        userLoginPO.setUpdateTime(LocalDateTime.now());
        userLoginPO.setIsDeleted(0);
        
        return userMapper.bindPhone(userLoginPO) > 0;
    }
    
    /**
     * 手机号登录
     */
    private UserLoginVO loginByPhone(UserLoginDTO userLoginDTO) {
        // 查询用户登录信息
        UserLoginPO userLoginPO = userMapper.getUserLoginByPhone(userLoginDTO.getIdentifier());
        
        // 如果用户不存在，则注册
        if (userLoginPO == null) {
            return registerByPhone(userLoginDTO);
        }
        
        // 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(userLoginDTO.getCredential().getBytes());
        if (!encryptedPassword.equals(userLoginPO.getCredential())) {
            throw new RuntimeException("密码错误");
        }
        
        // 查询用户信息
        UserPO userPO = userMapper.getUserByNanoid(userLoginPO.getUserNanoid());
        if (userPO == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 生成token
        String token = jwtUtils.generateToken(userPO.getNanoid());
        
        // 返回登录信息

        return userConverter.convertUserPOToUserLoginVO(userPO, userLoginDTO.getIdentifier(), token);
    }
    
    /**
     * 微信登录
     */
    private UserLoginVO loginByWx(UserLoginDTO userLoginDTO) {
        // 获取微信openid
        String openid = wxService.getWxOpenid(userLoginDTO.getIdentifier());
        if (openid == null) {
            throw new RuntimeException("获取微信openid失败");
        }
        
        // 查询用户登录信息
        UserLoginPO userLoginPO = userMapper.getUserLoginByWxOpenid(openid);
        
        // 如果用户不存在，则注册
        if (userLoginPO == null) {
            return registerByWx(openid);
        }
        
        // 查询用户信息
        UserPO userPO = userMapper.getUserByNanoid(userLoginPO.getUserNanoid());
        if (userPO == null) {
            throw new RuntimeException("用户不存在");
        }

        // 查询用户是否绑定了手机号
        UserLoginPO phoneLoginPO = userMapper.getPhoneLoginByUserNanoid(userPO.getNanoid());
        String phoneNumber = (phoneLoginPO != null) ? phoneLoginPO.getIdentifier() : null;

        // 生成token
        String token = jwtUtils.generateToken(userPO.getNanoid());

        // 返回登录信息
        return userConverter.convertUserPOToUserLoginVO(userPO, phoneNumber, token);
    }
    
    /**
     * 手机号注册
     */
    @Transactional
    public UserLoginVO registerByPhone(UserLoginDTO userLoginDTO) {
        String phoneNumber = userLoginDTO.getIdentifier();
        
        // 创建用户
        UserPO userPO = new UserPO();
        userPO.setNanoid(NanoIdUtils.randomNanoId());
        userPO.setNickname("用户" + phoneNumber.substring(7));
        userPO.setAvatarUrl("https://example.com/default-avatar.png");
        userPO.setCreateTime(LocalDateTime.now());
        userPO.setUpdateTime(LocalDateTime.now());
        userPO.setIsDeleted(0);
        userMapper.createUser(userPO);
        
        // 创建用户登录信息
        UserLoginPO userLoginPO = new UserLoginPO();
        userLoginPO.setNanoid(NanoIdUtils.randomNanoId());
        userLoginPO.setUserNanoid(userPO.getNanoid());
        userLoginPO.setLoginType(1); // 手机号登录
        userLoginPO.setIdentifier(phoneNumber);
        userLoginPO.setCredential(DigestUtils.md5DigestAsHex(userLoginDTO.getCredential().getBytes()));
        userLoginPO.setCreateTime(LocalDateTime.now());
        userLoginPO.setUpdateTime(LocalDateTime.now());
        userLoginPO.setIsDeleted(0);
        userMapper.createUserLogin(userLoginPO);
        
        // 生成token
        String token = jwtUtils.generateToken(userPO.getNanoid());
        
        // 返回登录信息
        return userConverter.convertUserPOToUserLoginVO(userPO, phoneNumber, token);
    }
    
    /**
     * 微信注册
     */
    @Transactional
    public UserLoginVO registerByWx(String openid) {
        // 创建用户
        UserPO userPO = new UserPO();
        userPO.setNanoid(NanoIdUtils.randomNanoId());
        userPO.setNickname("微信用户");
        userPO.setAvatarUrl("https://example.com/default-avatar.png");
        userPO.setCreateTime(LocalDateTime.now());
        userPO.setUpdateTime(LocalDateTime.now());
        userPO.setIsDeleted(0);
        userMapper.createUser(userPO);
        
        // 创建用户登录信息
        UserLoginPO userLoginPO = new UserLoginPO();
        userLoginPO.setNanoid(NanoIdUtils.randomNanoId());
        userLoginPO.setUserNanoid(userPO.getNanoid());
        userLoginPO.setLoginType(2); // 微信登录
        userLoginPO.setIdentifier(openid);
        userLoginPO.setCredential(openid);
        userLoginPO.setCreateTime(LocalDateTime.now());
        userLoginPO.setUpdateTime(LocalDateTime.now());
        userLoginPO.setIsDeleted(0);
        userMapper.createUserLogin(userLoginPO);
        
        // 生成token
        String token = jwtUtils.generateToken(userPO.getNanoid());
        
        // 返回登录信息
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setNanoid(userPO.getNanoid());
        userLoginVO.setNickname(userPO.getNickname());
        userLoginVO.setAvatarUrl(userPO.getAvatarUrl());
        userLoginVO.setPhoneNumber(null);
        userLoginVO.setToken(token);

        return userLoginVO;
    }
}
