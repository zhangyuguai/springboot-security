package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.entity.SysUser;
import com.xiong.security.service.RoleService;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiong.security.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //从数据库查询用户信息 并包装成UserDetails返回
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(username),User::getUserName,username);
        User user = userService.getOne(queryWrapper);
        if (user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<String> permissionValueList=roleService.getRoleListByUid(user.getUserId());
            return new SysUser(user,permissionValueList);
    }
}
