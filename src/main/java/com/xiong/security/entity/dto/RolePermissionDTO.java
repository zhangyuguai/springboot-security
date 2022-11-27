package com.xiong.security.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/10 0:21
 * description:
 */
@Data
public class RolePermissionDTO {
    private Long roleId;

    private List<Long> permissionList;
}
