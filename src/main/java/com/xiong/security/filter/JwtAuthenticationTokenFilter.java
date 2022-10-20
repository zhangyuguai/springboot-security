package com.xiong.security.filter;

import com.xiong.security.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LENOVO
 */

public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenManager tokenManager;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //进行身份的检验
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getRequestURI().equals("/login_p")){
            chain.doFilter(request,response);
            return;
        }
        //尝试获取当前用户的身份信息
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication!=null){
            //如果不为空，将身份信息存入权限上下文之中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
       chain.doFilter(request,response);
    }
    //尝试从redis中获取权限信息
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){


        String token = request.getHeader("token");

        if(token==null){
            return null;
        }

        //解析token获取用户名
        String userName = tokenManager.getUserInfoFromToken(token);

        if (userName!=null){
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            if (permissionValueList!=null){
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                for (String peritoneums : permissionValueList) {
                    SimpleGrantedAuthority user = new SimpleGrantedAuthority(peritoneums);
                    authorities.add(user);
                }
                return new UsernamePasswordAuthenticationToken(userName,token,authorities);
            }
        }

       return null;

    }
}