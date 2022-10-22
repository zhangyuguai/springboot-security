package com.xiong.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Role;
import com.xiong.security.entity.UserRole;
import com.xiong.security.service.RoleService;
import com.xiong.security.service.UserRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xsy
 * @date 2022/10/22 21:08
 * description:
 * 用户角色
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    //添加用户的角色
    @PostMapping("/{userId}")
    public Result insertUserRole(@PathVariable String userId, @RequestBody List<String> userRoleList){
        //根据接收到的用户角色名称，查出对应的roleId
        List<Role> roleList= roleService.getRoleListByRoleName(userRoleList);
        //将对应的roleId 和userId封装成userRole对象
        ArrayList<UserRole> userRoles = new ArrayList<>(roleList.size());
        for (Role role : roleList) {
            UserRole userRole = new UserRole();
            userRole.setUid(userId);
            userRole.setRid(role.getRoleId());
            userRoles.add(userRole);
        }
        //删除原本的用户角色
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userId),UserRole::getUid,userId);
        userRoleService.remove(queryWrapper);

        //插入新的用户角色
        if ( userRoleService.saveBatch(userRoles)){
            return Result.success();
        }

        return Result.error("更新用户权限失败!");
    }
}
