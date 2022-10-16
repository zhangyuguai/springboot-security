package com.xiong.security;

import com.xiong.security.entity.User;
import com.xiong.security.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootSecurityApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        User user = userMapper.selectAll();
        System.out.println(user);
    }


}
