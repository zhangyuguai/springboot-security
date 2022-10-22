package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.Menu;
import com.xiong.security.service.MenuService;
import com.xiong.security.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【t_menu】的数据库操作Service实现
* @createDate 2022-10-20 20:08:52
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenusByUId() {

        return menuMapper.getMenuByUid();
    }
}




