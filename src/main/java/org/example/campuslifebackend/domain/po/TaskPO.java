package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务定义表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPO {
    private Long id;
    private String nanoid;
    private String name;
    private String description;
    private String taskType; // ENUM类型在Java中可以用String或枚举类型表示
    private Integer expReward;
    private Integer coinReward;
    private Integer maxCompletions;
    private Integer isActive;
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}