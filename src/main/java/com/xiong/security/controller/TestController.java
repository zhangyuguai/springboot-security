package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.User;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author xsy
 * @date 2022/10/16
 * description:
 */
@RestController
@RequestMapping("/test")
public class TestController{

    @Autowired
    private UserService userService;

    @PostMapping
    public Result test(@RequestBody User user){
        System.out.println(user);
        return Result.success();
    }
}
