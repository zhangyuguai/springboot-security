package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.Role;
import com.xiong.security.service.RoleService;
import com.xiong.security.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【t_role】的数据库操作Service实现
* @createDate 2022-10-21 00:03:15
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<String> getRoleListByUid(String userId) {
        return roleMapper.getRoleListByUid(userId);
    }

    @Override
    public List<Role> getRoleListByRoleName(List<String> userRoleList) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(userRoleList.size()>0,Role::getRoleName,userRoleList);
        List<Role> roleList = roleMapper.selectList(queryWrapper);
        return roleList;
    }
}




