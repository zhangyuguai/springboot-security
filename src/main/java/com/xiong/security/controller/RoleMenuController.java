package com.xiong.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.ResultCode;
import com.xiong.security.entity.RoleMenu;
import com.xiong.security.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xsy
 * @date 2022/10/27 15:19
 * description:
 */
@RestController
@RequestMapping("/menuRole")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RedisTemplate redisTemplate;

    //修改角色权限菜单
    @PutMapping("update/{roleId}")
    public Result updateRoleMenu(@RequestBody List<String> menuList , @PathVariable String roleId){
        //将对应的roleId 和menuId封装成RoleMenu对象
        ArrayList<RoleMenu> roleMenus = new ArrayList<>(menuList.size());
        for (String menuId : menuList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRid(roleId);
            roleMenu.setMid(menuId);
            roleMenus.add(roleMenu);
        }
        //删除原本的用户角色
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(roleId),RoleMenu::getRid,roleId);
        roleMenuService.remove(queryWrapper);

        //插入新的角色菜单
        if ( roleMenuService.saveBatch(roleMenus)){
            String prefix="menuByRoleID_";
            //根据角色id查询的菜单id
            String redisMenuIdsKey=prefix+roleId;
            //删除redis中存储的权限id对应菜单
            redisTemplate.delete(redisMenuIdsKey);
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.ERROR);
    }
}
