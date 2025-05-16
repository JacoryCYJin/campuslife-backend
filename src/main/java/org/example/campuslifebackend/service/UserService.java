package org.example.campuslifebackend.service;

import org.example.campuslifebackend.domain.dto.UserLoginDTO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO 登录参数
     * @return 登录结果
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);
    
    /**
     * 用户注册
     * @param userLoginDTO 注册参数
     * @return 注册结果
     */
    UserLoginVO register(UserLoginDTO userLoginDTO);
    
    /**
     * 绑定手机号
     * @param userNanoid 用户nanoid
     * @param phoneNumber 手机号
     * @param password 密码
     * @return 绑定结果
     */
    boolean bindPhone(String userNanoid, String phoneNumber, String password);
}
