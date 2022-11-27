package com.xiong.security.mapper;

import com.xiong.security.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LENOVO
 * @description 针对表【sys_user】的数据库操作Mapper
 * @createDate 2022-11-04 00:49:04
 * @Entity com.xiong.security.entity.User
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 删除用户角色关系
     *
     * @param userId
     * @return
     */
    @Delete("delete from sys_user_role where user_id=#{userId}")
    int deleteUserRole(@Param("userId") Long userId);

    /**
     * 保存用户角色关系
     *
     * @param userId
     * @param roleIds
     * @return
     */
    int saveUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}




