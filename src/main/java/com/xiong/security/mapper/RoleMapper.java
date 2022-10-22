package com.xiong.security.mapper;

import com.xiong.security.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【t_role】的数据库操作Mapper
* @createDate 2022-10-21 00:03:15
* @Entity com.xiong.security.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRoleListByUid();

}




