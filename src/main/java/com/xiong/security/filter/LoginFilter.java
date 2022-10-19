package com.xiong.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiong.security.entity.User;
import com.xiong.security.utils.ResponseUtil;
import com.xiong.security.ResBean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginFilter(){
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
    }

    //获取用户名和密码包装成UsernamePasswordAuthenticationToken，之后进行认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user=new User();
        try {
            user  = new ObjectMapper().readValue(request.getInputStream(),User.class);
            log.info("获取到的user信息:{}",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username = user.getUserName();
        username = (username != null) ? username : "";
        username = username.trim();
        String password = user.getPassword();
        password = (password != null) ? password : "";
        //封装为UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        //进行认证
        return authenticationManager.authenticate(authRequest);
    }

    //认证成功之后调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获取登陆成功之后用户信息
        SecurityContextHolder.getContext().setAuthentication(authResult);
        //生成token

        //将token放入redis中

        //利用response写出token给前端
        ResponseUtil.out(response, Result.success(authResult));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
