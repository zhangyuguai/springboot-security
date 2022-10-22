package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.UserRole;
import com.xiong.security.service.UserRoleService;
import com.xiong.security.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author LENOVO
* @description 针对表【t-user_role】的数据库操作Service实现
* @createDate 2022-10-22 21:07:35
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




