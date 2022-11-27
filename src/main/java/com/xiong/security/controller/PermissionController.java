package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Permission;
import com.xiong.security.entity.query.PermissionQueryVo;
import com.xiong.security.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/7 17:01
 * description:
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取菜单列表
     * @param permissionQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result getPermissionList(PermissionQueryVo permissionQueryVo){
        List<Permission> permissionsList = permissionService.findPermissionsList(permissionQueryVo);
        return new Result(permissionsList);
    }

    /**
     * 查询上级菜单列表
     */
    @GetMapping("/parent/list")
    public Result getParentPermissionList(){
        List<Permission> parentPermissionList = permissionService.findParentPermissionList();
        return new Result(parentPermissionList);
    }

    /**
     * 添加菜单
     * @param permission
     * @return
     */
    @PostMapping("/addMenu")
    public Result add(@RequestBody Permission permission){
        if (permissionService.save(permission)) {
            return new Result(200,"添加菜单成功!",null);
        }
        return new Result(500,"添加菜单失败!",null);
    }

    /**
     * 修改菜单
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Permission permission){
        if (permissionService.updateById(permission)) {
            return new Result(200,"菜单修改成功",null);
        }
        return new Result(500,"菜单修改失败!",null);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        if (permissionService.removeById(id)) {
            return new Result(200,"菜单删除成功!",null);
        }
        return new Result(500,"菜单删除失败",null);
    }

    /**
     * 检查菜单下是否有子菜单
     * @param id
     * @return
     */
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        if (permissionService.hasChildrenPermission(id)) {
            return new Result(200,"菜单下有子菜单",null,false);
        }
        return new Result(200,null,null,true);
    }


}
