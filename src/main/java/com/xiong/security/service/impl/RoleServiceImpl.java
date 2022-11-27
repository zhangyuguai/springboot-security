package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.Role;
import com.xiong.security.entity.User;
import com.xiong.security.entity.query.RoleQueryVo;
import com.xiong.security.mapper.UserMapper;
import com.xiong.security.service.RoleService;
import com.xiong.security.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2022-11-03 23:12:44
*/
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户id获取角色
     * @param uid
     * @return
     */
    @Override
    public List<String> getRoleListByUid(Long uid) {
        return roleMapper.getRoleListByUid(uid);
    }

    @Override
    public Role getRoleByUid(Long uid) {
        return roleMapper.getRoleByUid(uid);
    }

    /**
     * 根据用户id查询角色列表
     * @param page
     * @param roleQueryVo
     * @return
     */
    @Override
    public IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo roleQueryVo) {
        //条件构造器
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!ObjectUtils.isEmpty(roleQueryVo.getRoleName()),Role::getRoleName, roleQueryVo.getRoleName());

        //排序
        queryWrapper.orderByAsc(Role::getId);
        //根据用户id查询用户
        User user = userMapper.selectById(roleQueryVo.getUserId());
        //用户不为空，且用户不为管理员，则只能查询自己创建的角色
        if (user!=null&&!ObjectUtils.isEmpty(user.getIsAdmin())&&user.getIsAdmin()!=1){
            queryWrapper.eq(Role::getCreateUser, roleQueryVo.getUserId());
        }
        return baseMapper.selectPage(page,queryWrapper);
    }

    /**
     * 检查角色是否被用户所使用的
     * @param id
     * @return
     */
    @Override
    public boolean check(Long id) {
        if (roleMapper.getRoleCountByRoleId(id)>0) {
            return true;
        }
        return false;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public boolean deleteRole(Long id) {
        //删除角色权限关系
        baseMapper.deleteRolePermissionByRoleId(id);
        //删除角色
        return  roleMapper.deleteById(id)>0;
    }

    /**
     * 分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @Override
    public boolean saveRolePermission(Long roleId, List<Long> permissionIds) {
        //删除角色之前的权限
        roleMapper.deleteRolePermissionByRoleId(roleId);
        //保存角色新权限
        return roleMapper.saveRolePermission(roleId,permissionIds);
    }

    /**
     * 根据用户ID查询该用户拥有的角色ID
     *
     * @param userId
     * @return
     */
    @Override
    public List<Long> findRoleIdByUserId(Long userId) {

        return baseMapper.findRoleIdByUserId(userId);
    }


}




