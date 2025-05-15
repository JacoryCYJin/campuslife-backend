package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户任务进度表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskPO {
    private Long id;
    private String nanoid;
    private String userNanoid;
    private String taskNanoid;
    private Integer completions;
    private LocalDateTime lastCompletedAt;
    private LocalDateTime createdAt;
}