package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Role;
import com.xiong.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xsy
 * @date 2022/10/22 15:52
 * description:
 */
@RestController
@RequestMapping("/role")
//具有管理员权限才能访问
@PreAuthorize("hasAnyAuthority('ROLE_admin')")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping
    public Result getRoleList(){
        List<Role> roleList = roleService.list();
        return new Result(roleList);
    }

    @GetMapping("/{userId}")
    public Result getRoleListByUid(@PathVariable("userId") String userId){
        List<String> roleListByUid = roleService.getRoleListByUid(userId);
        return new Result(roleListByUid);
    }

//    @PostMapping()
//    public Result updateUserROle(@RequestBody String[] roleList){
//        System.out.println("---------------------------------"+roleList);
//        return null;
//    }
}
