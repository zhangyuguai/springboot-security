package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.ResultCode;
import com.xiong.security.entity.Menu;
import com.xiong.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author xsy
 * @date 2022/10/26 22:58
 * description:
 */

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/menuTree/{roleId}")
    public Result getMenuTreeByRoleId(@PathVariable String roleId) {

        HashMap<String, Object> map = new HashMap<>();
        List<Menu> menuList = null;
        List<String> menuIds = null;
        String prefix="menuByRoleID_";
        String menuKey="menuTree";
        //根据角色id查询的菜单id
        String redisMenuIdsKey=prefix+roleId;
        menuIds = (List<String>) redisTemplate.opsForValue().get(redisMenuIdsKey);
        if (menuIds==null){
            menuIds = menuService.getMenuByRoleId(roleId);
            redisTemplate.opsForValue().set(redisMenuIdsKey,menuIds);
        }
        map.put("menuIds", menuIds);
        menuList = (List<Menu>) redisTemplate.opsForValue().get(menuKey);
        if (menuList==null){
            menuList = menuService.getMenuTree();
            redisTemplate.opsForValue().set(menuKey,menuList);
        }
        map.put("menuList", menuList);
        return new Result(ResultCode.SUCCESS, map);
    }
}
