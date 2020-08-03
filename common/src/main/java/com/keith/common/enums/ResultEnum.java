package com.keith.common.enums;

import lombok.Getter;

/**
*   @Description: 结果集枚举
*
*   @Author: SK-Keith
*   @Date: 2020/8/2
*/
@Getter
public enum ResultEnum {
    LOGIN_FAIL(1, "登录失败"),
    ROLE_ERROR(2, "角色权限有误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
