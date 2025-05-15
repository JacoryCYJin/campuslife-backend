package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户游戏化数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGameProfilePO {
    private Long id;
    private String nanoid;
    private String userNanoid;
    private Long exp;
    private Integer level;
    private Integer coins;
    private LocalDate lastCheckIn;
    private Integer checkInStreak;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}