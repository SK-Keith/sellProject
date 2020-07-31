package com.keith.controller;

import com.keith.form.OrderForm;
import com.keith.form.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@Controller
@RequestMapping("/user")
public class TestController {

    @PostMapping("/addUser")
    public Object addUser(@RequestBody  OrderForm orderForm) {
        Response res = new Response();
        res.setCode(200);
        res.setData("success");
        res.setMessage("good ideas");
        return res;
    }
}
