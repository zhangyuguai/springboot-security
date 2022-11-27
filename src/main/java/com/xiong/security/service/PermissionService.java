package com.xiong.security.service;

import com.xiong.security.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.security.entity.query.PermissionQueryVo;
import com.xiong.security.entity.vo.RolePermissionVo;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_permission】的数据库操作Service
* @createDate 2022-11-03 23:12:39
*/
public interface PermissionService extends IService<Permission> {

    List<Permission> findPermissionListByUserId(Long userId);

    /**
     * 获取菜单列表
     * @param permissionQueryVo
     * @return
     */
    List<Permission> findPermissionsList(PermissionQueryVo permissionQueryVo);

    /**
     * 获取上级菜单
     * @return
     */
    List<Permission> findParentPermissionList();

    /**
     * 查询是否子菜单
     * @param id
     * @return
     */
    Boolean hasChildrenPermission(Long id);

    /**
     * 查询分配权限树列表
     * @param userId
     * @param roleId
     * @return
     */
    RolePermissionVo findPermissionTree(Long userId, Long roleId);
}
