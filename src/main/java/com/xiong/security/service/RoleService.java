package com.xiong.security.service;

import com.xiong.security.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【t_role】的数据库操作Service
* @createDate 2022-10-21 00:03:15
*/
public interface RoleService extends IService<Role> {

    List<String> getRoleListByUid(String userId);

    List<Role> getRoleListByRoleName(List<String> userRoleList);
}
