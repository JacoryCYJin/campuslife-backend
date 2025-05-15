package org.example.campuslifebackend.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学校地标表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolLandmarkPO {
    private Long id;
    private String nanoid;
    private String schoolNanoid;
    private String name;
    private String description;
    private String locationGeo; // POINT类型在Java中可以用String表示，或者使用特定的地理坐标类
    private String imageUrl;
    private Integer isCheckpoint;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}