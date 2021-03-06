package com.keith.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    PARAM_ERROR(1, "参数错误"),
    CAR_EMPTY(2, "购物车为空"),
        ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
