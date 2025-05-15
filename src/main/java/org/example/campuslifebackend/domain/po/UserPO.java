package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPO {
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 公开ID
     */
    private String nanoid;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像链接
     */
    private String avatarUrl;
    
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
