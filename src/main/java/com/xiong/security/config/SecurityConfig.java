package com.xiong.security.config;

import com.xiong.security.filter.LoginFilter;
import com.xiong.security.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService myUserDetailService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login_p").loginProcessingUrl("/login")
                .usernameParameter("userName")
                .passwordParameter("password").failureHandler(
                        //设置认证失败回调
                        (request, response, exception) -> {
                    //设置响应内容格式
                    response.setContentType("application/json;charset=utf-8");

                }).successHandler(
                        //设置认证成功回调
                        (request, response, authentication) -> {

                }).and()
                        .authorizeRequests().antMatchers("/login").permitAll();

        http.addFilter(loginFilter(authenticationManager()));
        //允许跨域
        http.cors();
        http.csrf().disable();
    }


    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager){
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManager);
        return loginFilter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
