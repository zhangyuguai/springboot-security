package com.xiong.security.config;

import com.xiong.security.common.handler.MyLogoutHandler;
import com.xiong.security.common.handler.UnAuthenticationHandler;
import com.xiong.security.common.handler.UnAuthorityHandler;
import com.xiong.security.filter.JwtAuthenticationTokenFilter;
import com.xiong.security.filter.LoginFilter;
import com.xiong.security.utils.DefaultPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService myUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationEntryPoint UnAuthEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .authorizeRequests().antMatchers("/login","/logout_s").permitAll().anyRequest()
                .authenticated();

        http.addFilter(loginFilter(authenticationManager()))
                .addFilterBefore(jwtAuthenticationTokenFilter(), LogoutFilter.class);


        http.exceptionHandling()
                //认证失败调用
                .authenticationEntryPoint(UnAuthEntryPoint)
                //授权失败调用
                        .accessDeniedHandler(accessDeniedHandler);

        http.logout().logoutUrl("/api/user/logout").logoutSuccessUrl("/logout_s").addLogoutHandler(logoutHandler());
        //允许跨域
        http.cors();
        http.csrf().disable();
    }


    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
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
    public PasswordEncoder passwordEncoder() {
        return new DefaultPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        return new JwtAuthenticationTokenFilter(authenticationManager());
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new MyLogoutHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new UnAuthenticationHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new UnAuthorityHandler();
    }


}
