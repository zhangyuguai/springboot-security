package com.xiong.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.StatusCode;
import com.xiong.security.common.utools.codeEnum.ResultCode;
import com.xiong.security.entity.Menu;
import com.xiong.security.entity.Role;
import com.xiong.security.entity.User;
import com.xiong.security.mapper.MenuMapper;
import com.xiong.security.mapper.RoleMapper;
import com.xiong.security.service.UserService;
import com.xiong.security.utils.TokenManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
class SpringbootSecurityApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
    }

    @Test
    public void test01(){
        System.out.println(passwordEncoder.encode("123456"));
    }
    @Test
    public void test02(){
        List<String> roleListByUid = roleMapper.getRoleListByUid("1");
        System.out.println(roleListByUid);
        List<Menu> menuByUid = menuMapper.getMenuByUid("1");
        System.out.println(menuByUid);
    }

    @Test
    public void test03() throws JsonProcessingException {
        Result result = new Result(ResultCode.SUCCESS, "成功");
        String s = new ObjectMapper().writeValueAsString(result);
        System.out.println(s);
    }

    @Test
    public void test04() {
        User user = new User();
        user.setUserName("xiixxisadfdsfixix");
        userService.saveOrUpdate(user);
    }

    @Test
    public void test05() {
        List<Role> roleList = roleMapper.selectList(null);
//        redisTemplate.opsForValue().set("roleList",roleList);
        List<Role> roleList1 = (List<Role>) redisTemplate.opsForValue().get("roleList");
        System.out.println("----------------------------");
        System.out.println(roleList1);

    }


}
