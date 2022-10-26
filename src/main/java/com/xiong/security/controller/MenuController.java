package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.ResultCode;
import com.xiong.security.entity.Menu;
import com.xiong.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/menuTree/{roleId}")
    public Result getMenuTreeByRoleId(@PathVariable String roleId){

        HashMap<String, Object> map = new HashMap<>();
        List<String> menuIds = menuService.getMenuByRoleId(roleId);
        map.put("menuIds",menuIds);
        List<Menu> menuList= menuService.getMenuTree();
        map.put("menuList",menuList);
        return new Result(ResultCode.SUCCESS,map);
    }
}
