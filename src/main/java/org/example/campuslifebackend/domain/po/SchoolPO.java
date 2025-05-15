package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学校信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolPO {
    private Long id;
    private String nanoid;
    private String name;
    private String code;
    private String logoUrl;
    private String location;
    private Integer isVerified;
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}