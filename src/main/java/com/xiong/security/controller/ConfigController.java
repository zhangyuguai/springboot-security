package com.xiong.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Menu;
import com.xiong.security.entity.SysUser;
import com.xiong.security.entity.User;
import com.xiong.security.service.MenuService;
import com.xiong.security.service.UserService;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xsy
 * @date 2022/10/20
 * description:
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    public AuthenticationManager authenticationManager;
    //登陆之后获取用户
    @GetMapping("/user")
    public Result getUserInfo(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userName),User::getUserName,userName);
        User user = userService.getOne(queryWrapper);
        return Result.success(user);
    }


    @GetMapping("/initMenu")
    public Result getMenuWithChildren(){
        List<Menu> menuList = menuService.getMenusByUId();
        return Result.success(menuList);
    }
}
