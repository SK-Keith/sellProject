package com.keith.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class MsgTestController {

    @RequestMapping("/getMsg")
    public String getMsg() {
        return "hello, keith! ";
    }
}
