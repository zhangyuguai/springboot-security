package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.Permission;
import com.xiong.security.entity.User;
import com.xiong.security.entity.query.PermissionQueryVo;
import com.xiong.security.entity.vo.RolePermissionVo;
import com.xiong.security.mapper.UserMapper;
import com.xiong.security.service.PermissionService;
import com.xiong.security.mapper.PermissionMapper;
import com.xiong.security.utils.MenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LENOVO
 * @description 针对表【sys_permission】的数据库操作Service实现
 * @createDate 2022-11-03 23:12:39
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Permission> findPermissionListByUserId(Long userId) {
        return permissionMapper.findPermissionListByUserId(userId);
    }

    @Override
    public List<Permission> findPermissionsList(PermissionQueryVo permissionQueryVo) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>();
        queryWrapper.orderByAsc(Permission::getOrderNum)
                .eq(!Objects.isNull(permissionQueryVo.getName()), Permission::getName, permissionQueryVo.getName());
        //查询菜单列表
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper);
        //生成菜单树
        List<Permission> menuTree = MenuTree.makeMenuTree(permissionList, 0L);
        return menuTree;
    }

    @Override
    public List<Permission> findParentPermissionList() {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        //只查询type为0的目录和1的菜单
        queryWrapper.in(Permission::getType, Arrays.asList(0, 1))
                .orderByAsc(Permission::getOrderNum);
        List<Permission> permissions = permissionMapper.selectList(queryWrapper);
        //构造顶级菜单信息，如果数据库中的菜单表没有数据，选择上级菜单时则显示顶级菜单
        Permission permission = new Permission();
        permission.setId(0L);
        permission.setParentId(-1L);
        permission.setLabel("顶级菜单");
        permissions.add(permission);//将顶级菜单添加到集合

        List<Permission> menuTree = MenuTree.makeMenuTree(permissions, -1L);
        return menuTree;
    }

    @Override
    public Boolean hasChildrenPermission(Long id) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!Objects.isNull(id), Permission::getParentId, id);
        List<Permission> permissions = permissionMapper.selectList(queryWrapper);

        //判断数量是否大于0，如果大于0则表示存在
        if (permissionMapper.selectCount(queryWrapper) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public RolePermissionVo findPermissionTree(Long userId, Long roleId) {

        //1. 查询当前登录用户的信息。
        User user = userMapper.selectById(userId);
        //2. 判断当前登录用户是否是管理员，如果是管理员，则查询所有的权限信息；如果不是管理员，则
        //需要根据当前用户Id查询出当前用户所拥有的权限信息。
        List<Permission> list=null;
        if (user!=null&&!ObjectUtils.isEmpty(user.getIsAdmin())&&user.getIsAdmin()==1){
            //是管理员,查出所有权限
            list = baseMapper.selectList(null);
        }else {
            //不是管理员，用户已拥有的权限
            list= baseMapper.findPermissionListByUserId(userId);
        }
        //3.生成权限树
        List<Permission> permissionList = MenuTree.makeMenuTree(list, 0L);
        //4.查询要分配角色的原有权限
        List<Permission> rolePermissions = baseMapper.findPermissionListByRoleId(roleId);

       List<Long> rolePermissionIds= Optional.ofNullable(rolePermissions).orElse(new ArrayList<>())
                .stream().map(Permission::getId).collect(Collectors.toList());
        //要分配角色的权限id数组
        RolePermissionVo rolePermissionVo = new RolePermissionVo();
        rolePermissionVo.setPermissionList(permissionList);
        rolePermissionVo.setCheckedIds(rolePermissionIds);
//        rolePermissionVo.setCheckedList(rolePermissions);
        return rolePermissionVo;
    }
}




