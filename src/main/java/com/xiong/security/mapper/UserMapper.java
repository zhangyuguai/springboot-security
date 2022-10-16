package com.xiong.security.mapper;

import com.xiong.security.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author LENOVO
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2022-10-16 23:25:16
* @Entity com.xiong.security.entity.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from t_user")
    User selectAll();
}




