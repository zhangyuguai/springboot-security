package com.xiong.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiong.security.entity.SysUser;
import com.xiong.security.entity.User;
import com.xiong.security.utils.ResponseUtil;
import com.xiong.security.ResBean.Result;
import com.xiong.security.utils.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginFilter(){
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
    }

    //获取用户名和密码包装成UsernamePasswordAuthenticationToken，之后进行认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user  = new ObjectMapper().readValue(request.getInputStream(),User.class);
            log.info("获取到的user信息:{}",user);
            String username = user.getUserName();
            username = (username != null) ? username : "";
            username = username.trim();
            String password = user.getPassword();
            password = (password != null) ? password : "";
            //封装为UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            //进行认证
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }

    //认证成功之后调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获取登陆成功之后用户信息
        SysUser sysUser = (SysUser) authResult.getPrincipal();
        List<String> permissionValueList = sysUser.getPermissionValueList();
        //生成token
        String token = tokenManager.createToken(sysUser.getUsername());
        //将token放入redis中
        redisTemplate.opsForValue().set(sysUser.getUsername(),permissionValueList);
        //利用response写出token给前端
        ResponseUtil.out(response, Result.success(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("TokenLoginFilter-unsuccessfulAuthentication：认证失败！");
        // 响应给前端调用处
        ResponseUtil.out(response, Result.error());
    }
}
