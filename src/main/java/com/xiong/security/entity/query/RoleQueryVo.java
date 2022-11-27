package com.xiong.security.entity.query;

import com.xiong.security.entity.Role;
import lombok.Data;

/**
 * @author xsy
 * @date 2022/11/9 15:06
 * description:
 */
@Data
public class RoleQueryVo extends Role {
    /**
     * 页码
     */
    private Long pageNo=1L;
    /**
     * 每页数量
     */
    private Long pageSize=10L;

    /**
     * 用户id
     */
    private Long userId;

}
