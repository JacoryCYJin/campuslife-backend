package org.example.campuslifebackend.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.annotation.Resource;
import org.example.campuslifebackend.converter.UserConverter;
import org.example.campuslifebackend.domain.dto.UserLoginDTO;
import org.example.campuslifebackend.domain.po.UserLoginPO;
import org.example.campuslifebackend.domain.po.UserPO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;
import org.example.campuslifebackend.exception.BusinessException;
import org.example.campuslifebackend.common.enums.ResultEnum;
import org.example.campuslifebackend.common.utils.JudgeUtils;
import org.example.campuslifebackend.common.utils.JwtUtils;
import org.example.campuslifebackend.common.utils.RandomUtil;
import org.example.campuslifebackend.mapper.UserMapper;
import org.example.campuslifebackend.service.UserService;
import org.example.campuslifebackend.service.WxService;
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
        Integer loginType = userLoginDTO.getLoginType();
        
        // 根据登录类型判断
        if (loginType != null && loginType == 1) {
            // 验证手机号格式
            JudgeUtils.validatePhone(userLoginDTO.getIdentifier());
            // 手机号登录
            return loginByPhone(userLoginDTO);
        } else if (loginType != null && loginType == 2) {
            // 微信登录
            return loginByWx(userLoginDTO);
        } else {
            // 登录类型不正确
            throw new BusinessException(ResultEnum.PARAM_ERROR, "登录类型不正确");
        }
    }
    
    @Override
    public UserLoginVO register(UserLoginDTO userLoginDTO) {
        Integer loginType = userLoginDTO.getLoginType();
        
        // 根据登录类型判断
        if (loginType != null && loginType == 1) {
            // 验证手机号格式
            JudgeUtils.validatePhone(userLoginDTO.getIdentifier());
            // 手机号注册
            return registerByPhone(userLoginDTO);
        } else if (loginType != null && loginType == 2) {
            // 微信注册
            String openid = wxService.getWxOpenid(userLoginDTO.getIdentifier());
            if (openid == null) {
                throw new BusinessException(ResultEnum.SYSTEM_ERROR);
            }
            return registerByWx(openid);
        } else {
            // 登录类型不正确
            throw new BusinessException(ResultEnum.PARAM_ERROR, "登录类型不正确");
        }
    }
    
    @Override
    @Transactional
    public boolean bindPhone(String userNanoid, String phoneNumber, String password) {
        // 查询用户是否存在
        UserPO userPO = userMapper.getUserByNanoid(userNanoid);
        if (userPO == null) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS);
        }

        // 查询手机号是否已被绑定
        UserLoginPO existLoginPO = userMapper.getUserLoginByPhone(phoneNumber);
        if (existLoginPO != null) {
            throw new BusinessException(ResultEnum.PHONE_ALREADY_BOUND);
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

        // 如果用户不存在，抛出异常
        if (userLoginPO == null) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS);
        }

        // 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(userLoginDTO.getCredential().getBytes());
        if (!encryptedPassword.equals(userLoginPO.getCredential())) {
            throw new BusinessException(ResultEnum.PASSWORD_ERROR);
        }

        // 查询用户信息
        UserPO userPO = userMapper.getUserByNanoid(userLoginPO.getUserNanoid());
        if (userPO == null) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS);
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
            throw new BusinessException(ResultEnum.SYSTEM_ERROR);
        }

        // 查询用户登录信息
        UserLoginPO userLoginPO = userMapper.getUserLoginByWxOpenid(openid);

        // 如果用户不存在，抛出异常
        if (userLoginPO == null) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS);
        }

        // 查询用户信息
        UserPO userPO = userMapper.getUserByNanoid(userLoginPO.getUserNanoid());
        if (userPO == null) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS);
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
        // 先检查手机号是否已被注册
        UserLoginPO existingUser = userMapper.getUserLoginByPhone(userLoginDTO.getIdentifier());
        if (existingUser != null) {
            // 如果用户已存在，抛出异常
            throw new BusinessException(ResultEnum.USER_ALREADY_EXISTS);
        }

        // 用户不存在，创建新用户
        String nickname = "用户" + RandomUtil.randomString(6); // 生成随机昵称

        // 创建用户
        UserPO userPO = userConverter.converterUserLoginDTOToUserPO(nickname);
        userMapper.createUser(userPO);

        // 创建用户登录信息
        UserLoginPO userLoginPO = userConverter.converterUserLoginDTOToUserLoginPO(
                userLoginDTO,
                userPO.getNanoid(),
                1, // 手机号登录类型
                DigestUtils.md5DigestAsHex(userLoginDTO.getCredential().getBytes())
        );
        userMapper.createUserLogin(userLoginPO);

        // 生成token并返回
        String token = jwtUtils.generateToken(userPO.getNanoid());
        return userConverter.convertUserPOToUserLoginVO(userPO, userLoginDTO.getIdentifier(), token);
    }

    /**
     * 微信注册
     */
    @Transactional
    public UserLoginVO registerByWx(String openid) {
        // 创建用户
        UserPO userPO = userConverter.converterUserLoginDTOToUserPO("微信用户");
        userMapper.createUser(userPO);

        // 创建用户登录信息
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setIdentifier(openid);
        userLoginDTO.setCredential(openid);
        
        UserLoginPO userLoginPO = userConverter.converterUserLoginDTOToUserLoginPO(
                userLoginDTO,
                userPO.getNanoid(),
                2, // 微信登录类型
                openid
        );
        userMapper.createUserLogin(userLoginPO);

        // 生成token
        String token = jwtUtils.generateToken(userPO.getNanoid());

        // 返回登录信息
        return userConverter.convertUserPOToUserLoginVO(userPO, null, token);
    }
}
