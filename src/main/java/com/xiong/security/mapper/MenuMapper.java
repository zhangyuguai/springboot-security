package com.xiong.security.mapper;

import com.xiong.security.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【t_menu】的数据库操作Mapper
* @createDate 2022-10-20 20:08:52
* @Entity com.xiong.security.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuByUid(@Param("userId") String userId);
}




