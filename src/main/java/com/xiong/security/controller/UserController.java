package com.xiong.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.ResultCode;
import com.xiong.security.entity.User;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result getPageUser(@PathVariable Integer pageNum
                            ,@PathVariable Integer pageSize
                            ,@RequestParam(required = false) String userName){
        Page<User> pageUser = userService.getPageUser(pageNum, pageSize, userName);
        return new Result(pageUser);
    }


    @PostMapping
    public Result addOrUpdateUser(@RequestBody User user){

        boolean flag = false;
        User userById = userService.getById(user.getUserId());
        if (userById!=null){
            //更新
            flag = userService.updateById(user);
        }else{
            //密码加密
            String password = user.getPassword();
            password = passwordEncoder.encode(password);
            user.setPassword(password);
            //添加
            flag = userService.save(user);
        }
        if(flag){
            return new Result(ResultCode.SUCCESS,user);
        }
        return new Result(ResultCode.ERROR);
    }

    @PostMapping("/delete")
    public Result deleteUser(@RequestBody List<String> idList){
        boolean flag = userService.removeByIds(idList);
        if(flag){
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.ERROR);
    }
}
