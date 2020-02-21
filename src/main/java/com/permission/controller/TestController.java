package com.permission.controller;

import com.permission.annotation.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: shenke
 * @date: 2020/2/21 21:25
 * @description: 测试接口
 */
@RestController
@ResponseBody
@Permission
public class TestController {

    @GetMapping("/test1")
    public String test1 () {
        return "hello world";
    }

    @GetMapping("/test2")
    public String test2 () {
        return "你好";
    }

}
