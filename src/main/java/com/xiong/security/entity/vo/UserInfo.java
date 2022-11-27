package com.xiong.security.entity.vo;

import com.xiong.security.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xsy
 * @date 2022/11/4 12:43
 * description:
 * 返回的用户信息
 */
@Data
public class UserInfo implements Serializable {
    //用户ID
    private Long id;
    //用户名称
    private String name;
    //头像
    private String avatar;
    //介绍
    private String introduction;
    //角色权限集合
    private List<String> roles;

    /**
     * 用户对应的菜单
     */
    private List<RouterVo> menus;



}
