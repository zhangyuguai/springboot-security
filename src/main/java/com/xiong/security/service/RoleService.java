package com.xiong.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiong.security.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.security.entity.query.RoleQueryVo;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2022-11-03 23:12:44
*/
public interface RoleService extends IService<Role> {

    List<String> getRoleListByUid(Long id);

    Role getRoleByUid(Long uid);

    /**
     * 根据用户查询角色列表
     * @param page
     * @param roleQueryVo
     * @return
     */
    IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo roleQueryVo);


    /**
     * 检查角色是否被使用
     * @param id
     * @return
     */
    boolean check(Long id);

    /**
     * 删除角色
     * @param id
     * @return
     */
    boolean deleteRole(Long id);

    boolean saveRolePermission(Long roleId,List<Long> permissionIds);

    List<Long> findRoleIdByUserId(Long userId);
}
