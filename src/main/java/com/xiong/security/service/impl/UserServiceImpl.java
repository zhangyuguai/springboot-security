package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.User;
import com.xiong.security.service.UserService;
import com.xiong.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author LENOVO
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-10-16 23:25:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public Page<User> getPageUser(Integer pageNum, Integer pageSize, String userName) {
        Page<User> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userName),User::getUserName,userName);
        userMapper.selectPage(pageInfo,queryWrapper);
        return pageInfo;
    }
}




