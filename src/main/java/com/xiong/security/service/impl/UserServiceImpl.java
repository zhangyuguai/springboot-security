package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.User;
import com.xiong.security.service.UserService;
import com.xiong.security.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author LENOVO
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-10-16 23:25:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




