package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.RolePermission;
import com.xiong.security.service.RolePermissionService;
import com.xiong.security.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author LENOVO
* @description 针对表【sys_role_permission】的数据库操作Service实现
* @createDate 2022-11-03 23:12:51
*/
@Service
@Transactional
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




