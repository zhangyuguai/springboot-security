package com.xiong.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiong.security.mapper")
public class SpringbootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityApplication.class, args);
    }

}
