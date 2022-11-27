package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.entity.Permission;
import com.xiong.security.entity.SysUser;
import com.xiong.security.service.PermissionService;
import com.xiong.security.service.RoleService;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.xiong.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Service
@Transactional
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    //从数据库查询用户信息 并包装成UserDetails返回
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(username),User::getUsername,username);
        User user = userService.getOne(queryWrapper);
        if (user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //查询用户的拥有的权限列表
        List<Permission> permissionList =
                permissionService.findPermissionListByUserId(user.getId());
        //获取权限编码
        List<String> permissionValueList = permissionList.stream()
                .filter(item -> item != null)
                .map(item -> item.getCode()).filter(item -> item != null)
                .collect(Collectors.toList());

        return new SysUser(user,permissionValueList);
    }
}