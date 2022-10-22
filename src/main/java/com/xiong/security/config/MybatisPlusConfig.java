package com.xiong.security.config;
 
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * @author LENOVO
 */ //1.表示这个类 是一个配置类  目的: 封装对象-交给Spring容器管理
@Configuration
@MapperScan("com.xiong.security.mapper")
public class MybatisPlusConfig {
 
    // @Bean 将方法的返回值对象,交给Spring容器管理
    //MP分页机制  Mysql分页语句/Oracle分页语句  为了实现功能复用 需要手动配置
    //根据数据库类型不同 之后动态的生成Sql  MP才能调用分页对象
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //定义分页拦截器对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}