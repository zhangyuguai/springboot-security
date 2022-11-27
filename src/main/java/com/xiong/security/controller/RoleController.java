package com.xiong.security.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Role;
import com.xiong.security.entity.dto.RolePermissionDTO;
import com.xiong.security.entity.query.RoleQueryVo;
import com.xiong.security.entity.vo.RolePermissionVo;
import com.xiong.security.service.PermissionService;
import com.xiong.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xsy
 * @date 2022/11/9 15:29
 * description:
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;
    /**
     * 分页查询角色列表
     * @param roleQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result pageList(RoleQueryVo roleQueryVo){
        //创建page对象
        IPage page=new Page(roleQueryVo.getPageNo(), roleQueryVo.getPageSize());
        //查询分页
        page = roleService.findRoleListByUserId(page, roleQueryVo);

        return new Result(page);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Role role){
        if (roleService.save(role)) {
            return new Result(200,"添加角色成功",null,null);
        }
        return new Result(500,"添加角色失败",null,null);
    }
    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping("/update")
    public Result updateRole(@RequestBody Role role){
        if (roleService.updateById(role)) {
            return new Result(200,"修改角色成功",null,null);
        }
        return new Result(500,"修改角色失败",null,null);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteRole(@PathVariable Long id){
        if (roleService.deleteRole(id)) {
            return new Result(200,"删除角色成功",null);
        }
        return new Result(500,"删除角色失败",null);
    }

    /**
     * 检查角色是否被其他用户使用
     * @param id
     * @return
     */
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        if (roleService.check(id)) {
            return new Result(200,"该角色已被其他用户使用，无法删除",null,false);
        }
        return new Result(200,null,null,true);
    }

    /**
     * 分配权限-保存权限数据
     */
    @PostMapping("/saveRoleAssign")
    public Result saveRoleAssign(@RequestBody RolePermissionDTO rolePermissionDTO){
        if (roleService.saveRolePermission(rolePermissionDTO.getRoleId(),rolePermissionDTO.getPermissionList())) {
            return new Result(200,"保存成功",null);
        }
        return new Result(500,"保存失败",null);
    }
    /**
     * 分配权限-查询权限树数据
     */
    @GetMapping("/getAssignPermissionTree")
    public Result getAssignPermissionTree( Long userId,Long roleId){
        RolePermissionVo rolePermissionVo = permissionService.findPermissionTree(userId, roleId);
        return new Result(rolePermissionVo);
    }

}
