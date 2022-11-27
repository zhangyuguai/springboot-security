package com.xiong.security.entity.dto;

import com.mysql.cj.log.Log;
import lombok.Data;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/11 23:05
 * description:
 */
@Data
public class UserRoleDto {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private List<Long> roleIds;

}
