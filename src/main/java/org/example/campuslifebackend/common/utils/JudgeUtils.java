package org.example.campuslifebackend.common.utils;

import org.example.campuslifebackend.common.enums.ResultEnum;
import org.example.campuslifebackend.exception.BusinessException;

/**
 * @author Jacory
 * @date 2025/5/16
 */
public class JudgeUtils {

    /**
     * 判断是否为手机号（简单规则：以1开头的11位数字）
     * @param identifier 输入的标识符
     * @return 是否为手机号
     */
    public static boolean isPhone(String identifier) {
        return identifier != null && identifier.matches("^1\\d{10}$");
    }

    /**
     * 验证手机号格式，不合法则抛出异常
     * @param phoneNumber 手机号
     * @throws BusinessException 手机号格式不正确时抛出异常
     */
    public static void validatePhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new BusinessException(ResultEnum.PARAM_ERROR);
        }
        if (!isPhone(phoneNumber)) {
            throw new BusinessException(ResultEnum.PARAM_ERROR, "手机号格式不正确");
        }
    }

    /**
     * 判断是否为微信 openid（可选扩展）
     * 实际中微信 openid 由微信定义规则，这里仅示例
     */
    public static boolean isWxOpenId(String identifier) {
        return identifier != null && identifier.startsWith("wx"); // 可根据实际业务规则修改
    }
}
