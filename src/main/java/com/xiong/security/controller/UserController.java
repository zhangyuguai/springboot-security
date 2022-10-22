package com.xiong.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.User;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result getPageUser(@PathVariable Integer pageNum
                            ,@PathVariable Integer pageSize
                            ,@RequestParam(required = false) String userName){
        Page<User> pageUser = userService.getPageUser(pageNum, pageSize, userName);
        return Result.success(pageUser);
    }
}
