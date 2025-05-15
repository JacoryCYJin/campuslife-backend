package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 成就定义表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementPO {
    private Long id;
    private String nanoid;
    private String name;
    private String description;
    private String iconUrl;
    private Integer expReward;
    private Integer coinReward;
    private Integer isHidden;
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}