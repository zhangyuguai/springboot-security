package com.xiong.security.mapper;

import com.xiong.security.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_permission】的数据库操作Mapper
* @createDate 2022-11-04 00:48:45
* @Entity com.xiong.security.entity.Permission
*/
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户查询所对应的菜单
     * @param userId
     * @return
     */
    List<Permission> findPermissionListByUserId(@Param("userId") Long userId);


    /**
     * 根据角色查询所对应的菜单
     * @param roleId
     * @return
     */
    List<Permission> findPermissionListByRoleId(@Param("roleId") Long roleId);
}




