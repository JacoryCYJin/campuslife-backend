package org.example.campuslifebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.campuslifebackend.domain.po.UserLoginPO;
import org.example.campuslifebackend.domain.po.UserPO;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    /**
     * 通过手机号查询用户登录信息
     * @param phoneNumber 手机号
     * @return 用户登录信息
     */
    UserLoginPO getUserLoginByPhone(@Param("phoneNumber") String phoneNumber);
    
    /**
     * 通过微信openid查询用户登录信息
     * @param openid 微信openid
     * @return 用户登录信息
     */
    UserLoginPO getUserLoginByWxOpenid(@Param("openid") String openid);
    
    /**
     * 通过用户nanoid查询用户信息
     * @param userNanoid 用户nanoid
     * @return 用户信息
     */
    UserPO getUserByNanoid(@Param("userNanoid") String userNanoid);
    
    /**
     * 查询用户是否绑定了手机号
     * @param userNanoid 用户nanoid
     * @return 用户登录信息
     */
    UserLoginPO getPhoneLoginByUserNanoid(@Param("userNanoid") String userNanoid);
    
    /**
     * 创建用户
     * @param userPO 用户信息
     * @return 影响行数
     */
    int createUser(UserPO userPO);
    
    /**
     * 创建用户登录信息
     * @param userLoginPO 用户登录信息
     * @return 影响行数
     */
    int createUserLogin(UserLoginPO userLoginPO);
    
    /**
     * 更新用户信息
     * @param userPO 用户信息
     * @return 影响行数
     */
    int updateUser(UserPO userPO);
    
    /**
     * 绑定手机号
     * @param userLoginPO 用户登录信息
     * @return 影响行数
     */
    int bindPhone(UserLoginPO userLoginPO);
}
