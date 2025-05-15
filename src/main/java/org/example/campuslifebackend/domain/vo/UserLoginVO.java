package org.example.campuslifebackend.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录返回VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {
    /**
     * 用户nanoid
     */
    private String nanoid;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户头像
     */
    private String avatarUrl;
    
    /**
     * 手机号（为null或空字符串表示未绑定）
     */
    private String phoneNumber;
    
    /**
     * JWT令牌
     */
    private String token;
}
