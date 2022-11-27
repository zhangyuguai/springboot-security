package com.xiong.security.entity.vo;

import com.xiong.security.entity.Permission;
import lombok.Data;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/9 22:01
 * description:
 */
@Data
public class RolePermissionVo {
    /**
     * 菜单数据
     */
    private List<Permission> permissionList;

    /**
     * 该角色原有分配的菜单数据
     */
    private Object [] checkList;

    /**
     * 该角色原有分配的菜单id数组
     *
     */
    private List<Long> checkedIds;
}
