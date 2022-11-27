package com.xiong.security.mapper;

import com.mysql.cj.log.Log;
import com.xiong.security.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2022-11-04 00:48:53
* @Entity com.xiong.security.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRoleListByUid(@Param("uid") Long uid);

    Role getRoleByUid(@Param("uid") Long uid);

    //查询被使用角色数量
    @Select("select count(*)from sys_user_role where role_id=#{roleId}")
    Long getRoleCountByRoleId(@Param("roleId") Long id);

    //删除角色权限信息
    @Delete("delete from sys_role_permission where role_id =#{roleId}")
    void deleteRolePermissionByRoleId(@Param("roleId")Long roleId);

    /**
     * 保存角色权限关系
     * @param roleId
     * @param permissionIds
     * @return
     */
    boolean saveRolePermission(Long roleId, List<Long> permissionIds);

    /**
     * 根据用户ID查询该用户拥有的角色ID
     * @param userId
     * @return
     */
    @Select("select role_id from `sys_user_role` where user_id = #{userId}")
    List<Long> findRoleIdByUserId(Long userId);
}




