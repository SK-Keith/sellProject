package com.keith.common.model;

import lombok.Data;

@Data
public class ResultVO<T> {
    // 状态码
    private Integer code;

    // 信息
    private String msg;

    // 具体内容
    private T data;
}
