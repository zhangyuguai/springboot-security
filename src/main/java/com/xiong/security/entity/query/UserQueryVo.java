package com.xiong.security.entity.query;

import com.xiong.security.entity.User;
import lombok.Data;

import java.net.UnknownServiceException;

/**
 * @author xsy
 * @date 2022/11/10 15:25
 * description:
 */
@Data
public class UserQueryVo extends User {

    private Long pageNo=1L;
    private Long pageSize=10L;
}
