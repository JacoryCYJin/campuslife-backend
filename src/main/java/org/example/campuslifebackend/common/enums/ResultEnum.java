package org.example.campuslifebackend.common.enums;

/**
 * 结果枚举类
 */
public enum ResultEnum {
    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    USER_ALREADY_EXISTS(1001, "用户已存在"),
    USER_NOT_EXISTS(1002, "用户不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    PHONE_ALREADY_BOUND(1004, "手机号已被绑定"),
    SYSTEM_ERROR(500, "系统错误");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}