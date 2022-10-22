package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Role;
import com.xiong.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xsy
 * @date 2022/10/22 15:52
 * description:
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping
    public Result getRoleList(){
        List<Role> roleList = roleService.list();
        return Result.success(roleList);
    }

    @GetMapping("/{userId}")
    public Result getRoleListByUid(@PathVariable("userId") String userId){
        List<String> roleListByUid = roleService.getRoleListByUid(userId);
        return Result.success(roleListByUid);
    }

//    @PostMapping()
//    public Result updateUserROle(@RequestBody String[] roleList){
//        System.out.println("---------------------------------"+roleList);
//        return null;
//    }
}
