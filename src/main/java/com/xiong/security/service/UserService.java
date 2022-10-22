package com.xiong.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.security.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author LENOVO
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-10-16 23:25:16
*/
public interface UserService extends IService<User> {

    Page<User> getPageUser(Integer pageNum, Integer pageSize, String userName);
}
