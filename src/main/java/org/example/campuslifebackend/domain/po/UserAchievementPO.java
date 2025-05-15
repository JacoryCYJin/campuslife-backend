package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户成就进度表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAchievementPO {
    private Long id;
    private String nanoid;
    private String userNanoid;
    private String achievementNanoid;
    private Integer progress;
    private Integer isUnlocked;
    private LocalDateTime unlockTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}