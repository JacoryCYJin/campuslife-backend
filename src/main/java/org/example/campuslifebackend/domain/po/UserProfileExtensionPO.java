package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户扩展信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileExtensionPO {
    private Long id;
    private String nanoid;
    private String userNanoid;
    private String schoolNanoid;
    private String school;
    private String grade;
    private String bio;
    private String tags; // JSON类型在Java中可以用String表示
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}