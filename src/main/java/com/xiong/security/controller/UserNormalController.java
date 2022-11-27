package com.xiong.security.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Role;
import com.xiong.security.entity.User;
import com.xiong.security.entity.dto.UserRoleDto;
import com.xiong.security.entity.query.RoleQueryVo;
import com.xiong.security.entity.query.UserQueryVo;
import com.xiong.security.service.RoleService;
import com.xiong.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/10 15:29
 * description:
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/normal/user")
public class UserNormalController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    /**
     * 用户分页
     * @param userQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result pageList(UserQueryVo userQueryVo){
        //构造分页对象
        IPage<User> page=new Page<>(userQueryVo.getPageNo(),userQueryVo.getPageSize());

        userService.findUserListByPage(page,userQueryVo);

        return new Result(page);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('sys:user:add')")
    public Result addUser(@RequestBody User user){
        //检查用户名是否已被使用
        User userByUsername = userService.getUserByUsername(user.getUsername());
        if (userByUsername!=null) {
            return new Result(200,"当前用户名太火爆了，请换一个试试!",null,false);
        }
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.save(user)) {
            return new Result(200,"添加用户成功",null,true);
        }
        return new Result(200,"添加用户失败",null,false);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('sys:user:update')")
    public Result updateUser(@RequestBody User user){
        //检查用户名是否已被使用
        User item = userService.getUserByUsername(user.getUsername());
        if (item!=null&& !item.getId().equals(user.getId())){
            return new Result(200,"用户名已被占用!",null,false);
        }
        if (userService.updateById(user)) {
            return new Result(200,"修改用户成功",null,true);
        }
        return new Result(200,"修改用户失败",null,false);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('sys:user:delete')")
    public Result deleteUser(@PathVariable Long id){

        if (userService.deleteUserById(id)) {
            return new Result(200,"删除用户成功",null,true);
        }
        return new Result(200,"删除用户失败",null,false);
    }

    /**
     * 获取分配角色列表
     * @param roleQueryVo
     * @return
     */
    @GetMapping("/getRoleListForAssign")
    public Result getRoleListForAssign(RoleQueryVo roleQueryVo){
        //创建分页对象
        IPage<Role> page = new Page<Role>(roleQueryVo.getPageNo(),
                roleQueryVo.getPageSize());
        //调用查询方法
        roleService.findRoleListByUserId(page,roleQueryVo);
        //返回数据
        return new Result(page);
    }

    /**
     * 根据用户ID查询该用户拥有的角色列表
     * @param userId
     * @return
     */
    @GetMapping("/getRoleByUserId/{userId}")
    public Result getRoleByUserId(@PathVariable Long userId){
        //调用根据用户ID查询该用户拥有的角色ID的方法
        List<Long> roleIds = roleService.findRoleIdByUserId(userId);
        return new Result(roleIds);
    }

    /**
     * 保存用户角色关系
     * @param userRoleDto
     * @return
     */
    @PostMapping("/saveUserRole")
    @PreAuthorize("hasAnyRole('sys:user:add')")
    public Result saveUserRole(@RequestBody UserRoleDto userRoleDto){
        if (userService.saveUserRole(userRoleDto.getUserId(),userRoleDto.getRoleIds())) {
            return new Result(200,"角色分配成功",null,true);
        }
        return new Result(200,"角色分配失败",null,false);
    }

}
