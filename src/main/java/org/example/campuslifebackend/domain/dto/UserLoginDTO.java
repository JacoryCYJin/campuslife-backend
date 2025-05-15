package org.example.campuslifebackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacory
 * @date 2025/5/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    /**
     * 唯一标识（手机号或openid）
     */
    private String identifier;
    
    /**
     * 凭证（密码或空）
     */
    private String credential;
}