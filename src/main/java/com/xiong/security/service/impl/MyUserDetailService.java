package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.entity.SysUser;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiong.security.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    //从数据库查询用户信息 并包装成UserDetails返回
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(username),User::getUserName,username);
        User user = userService.getOne(queryWrapper);
        user.setPassword("$2a$10$nxmRVQ1UJRrri/mY5dl.c.CP10j9nS6XBGARBiOrxHU8YW4HXAp1m");
        ArrayList<String> permissionValueList = new ArrayList<>();
        permissionValueList.add("ROLE_ADMIN");
        if (user!=null){
            return new SysUser(user,permissionValueList);
        }
        return new SysUser();
    }
}
