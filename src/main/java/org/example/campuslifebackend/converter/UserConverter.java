package org.example.campuslifebackend.converter;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.example.campuslifebackend.domain.dto.UserLoginDTO;
import org.example.campuslifebackend.domain.po.UserLoginPO;
import org.example.campuslifebackend.domain.po.UserPO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * @author Jacory
 * @date 2025/5/15
 */
@Component
public class UserConverter {
    public UserLoginVO convertUserPOToUserLoginVO(UserPO userPO, String phoneNumber, String token) {
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setNanoid(userPO.getNanoid());
        userLoginVO.setNickname(userPO.getNickname());
        userLoginVO.setAvatarUrl(userPO.getAvatarUrl());
        userLoginVO.setPhoneNumber(phoneNumber); // 如果phoneNumber为null或空字符串，表示未绑定手机号
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    public UserPO converterUserLoginDTOToUserPO(String nickname) {
        UserPO userPO = new UserPO();
        userPO.setNanoid(NanoIdUtils.randomNanoId());
        userPO.setNickname(nickname);
        userPO.setAvatarUrl("https://example.com/default-avatar.png");
        userPO.setCreateTime(LocalDateTime.now());
        userPO.setUpdateTime(LocalDateTime.now());
        userPO.setIsDeleted(0);
        return userPO;
    }

    public UserLoginPO converterUserLoginDTOToUserLoginPO(UserLoginDTO userLoginDTO, String userNanoid, int loginType, String credential) {
        UserLoginPO userLoginPO = new UserLoginPO();
        userLoginPO.setNanoid(NanoIdUtils.randomNanoId());
        userLoginPO.setUserNanoid(userNanoid);
        userLoginPO.setLoginType(loginType);
        userLoginPO.setIdentifier(userLoginDTO.getIdentifier());
        userLoginPO.setCredential(credential);
        userLoginPO.setCreateTime(LocalDateTime.now());
        userLoginPO.setUpdateTime(LocalDateTime.now());
        userLoginPO.setIsDeleted(0);
        return userLoginPO;
    }
}
