package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户登录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginPO {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 公开ID
     */
    private String nanoid;
    
    /**
     * 关联用户nanoid
     */
    private String userNanoid;
    
    /**
     * 登录类型(1手机号，2微信)
     */
    private Integer loginType;
    
    /**
     * 唯一标识(手机号or openid)
     */
    private String identifier;
    
    /**
     * 凭证(密码或空)
     */
    private String credential;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 是否删除
     */
    private Integer isDeleted;
}