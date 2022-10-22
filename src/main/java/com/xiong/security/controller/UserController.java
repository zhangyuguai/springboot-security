package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsy
 * @date 2022/10/22
 * description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public Result getAllUser(){

    }
}
