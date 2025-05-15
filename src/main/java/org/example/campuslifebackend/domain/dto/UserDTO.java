package org.example.campuslifebackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Jacory
 * @date 2025/5/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nanoid;
    private String nickname;
    private String avatarUrl;
    private LocalDateTime createTime;
}
