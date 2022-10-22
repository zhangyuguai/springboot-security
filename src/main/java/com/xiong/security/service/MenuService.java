package com.xiong.security.service;

import com.xiong.security.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【t_menu】的数据库操作Service
* @createDate 2022-10-20 20:08:52
*/
public interface MenuService extends IService<Menu> {

    List<Menu> getMenusByUId(String userId);
}
