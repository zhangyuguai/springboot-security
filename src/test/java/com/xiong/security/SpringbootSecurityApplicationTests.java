package com.xiong.security;

import com.xiong.security.entity.Menu;
import com.xiong.security.mapper.MenuMapper;
import com.xiong.security.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringbootSecurityApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;
    @Test
    void contextLoads() {
    }

    @Test
    public void test01(){
        System.out.println(passwordEncoder.encode("123456"));
    }
    @Test
    public void test02(){
        List<String> roleListByUid = roleMapper.getRoleListByUid();
        System.out.println(roleListByUid);
        List<Menu> menuByUid = menuMapper.getMenuByUid();
        System.out.println(menuByUid);
    }
}
