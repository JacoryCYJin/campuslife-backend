package org.example.campuslifebackend.exception;

import org.example.campuslifebackend.common.enums.ResultEnum;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public BusinessException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}