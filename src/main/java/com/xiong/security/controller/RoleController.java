package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Role;
import com.xiong.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping
    public Result getRoleList(){
        List<Role> roleList=null;
         roleList = (List<Role>) redisTemplate.opsForValue().get("roleList");
         //redis中查询到数据
        if (roleList == null) {
            //未查询到，则去数据库查询
            roleList = roleService.list();
            redisTemplate.opsForValue().set("roleList", roleList);
        }
        return new Result(roleList);
    }

    @GetMapping("/{userId}")
    public Result getRoleListByUid(@PathVariable("userId") String userId){
        List<String> roleListByUid=null;
        String prefix="userRole_";
        String redisRoleByUid=prefix+userId;
        roleListByUid = (List<String>) redisTemplate.opsForValue().get(redisRoleByUid);
        if (roleListByUid == null) {
            roleListByUid = roleService.getRoleListByUid(userId);
            redisTemplate.opsForValue().set(redisRoleByUid,roleListByUid);
        }
        return new Result(roleListByUid);
    }

//    @PostMapping()
//    public Result updateUserROle(@RequestBody String[] roleList){
//        System.out.println("---------------------------------"+roleList);
//        return null;
//    }
}
