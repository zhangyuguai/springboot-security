package com.xiong.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.log.Log;
import com.xiong.security.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.security.entity.query.UserQueryVo;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2022-11-03 23:12:54
*/
public interface UserService extends IService<User> {

    IPage<User> findUserListByPage(IPage<User> page, UserQueryVo userQueryVo);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    boolean deleteUserById(Long userId);

    /**
     * 保存用户角色关系
     * @param userId
     * @param roleIds
     * @return
     */
    boolean saveUserRole(Long userId, List<Long> roleIds);
}
