package org.example.campuslifebackend.converter;

import org.example.campuslifebackend.domain.po.UserPO;
import org.example.campuslifebackend.domain.vo.UserLoginVO;
import org.springframework.stereotype.Component;

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
}
