package com.xiong.security.filter;

import com.xiong.security.common.exception.CustomerAuthenticationException;
import com.xiong.security.common.handler.UnAuthenticationHandler;
import com.xiong.security.utils.RedisUtil;
import com.xiong.security.utils.TokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LENOVO
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthenticationEntryPoint unAuthenticationHandler;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //进行身份的检验
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ( request.getRequestURI().equals("/logout_s")) {
            chain.doFilter(request, response);
            return;
        }
        if (!request.getRequestURI().equals("/api/user/login")){
            try {
                //尝试获取当前用户的身份信息
                UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
                if (authentication != null) {
                    //如果不为空，将身份信息存入权限上下文之中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (AuthenticationException e) {
                unAuthenticationHandler.commence(request, response, e);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    //尝试从redis中获取权限信息
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {


        //判断token是否携带
        String token = request.getHeader("token");
        //如果请求头部没有获取到token，则从请求的参数中进行获取
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (token == null) {
            //请求头中不存在token的信息，抛出自定义异常
            throw new CustomerAuthenticationException("token不存在!");
        }

        //将redis中的token拿出来验证当前token是否一致
        String tokenKey = "token_" + token;
        String redisToken = (String) redisUtil.get(tokenKey);
        //如果token和Redis中的token不一致，则验证失败
        if (Objects.isNull(redisToken)){
            throw new CustomerAuthenticationException("token验证失败");
        }

        //验证token是否过期
        if (tokenManager.isTokenExpired(token)) {
            //token已过期，从异常中获取用户信息
            String username = tokenManager.getUserInfoFromToken(token);
            //从redis中删除token
            tokenManager.removeToken(username);
            //删除用户相关信息
            tokenManager.removeToken("token_"+token);
            throw new CustomerAuthenticationException("token已过期");
        }


        String username = null;
        //解析token获取用户名
        username = tokenManager.getUserInfoFromToken(token);

        if (username != null) {
            List<String> permissionValueList = (List<String>) redisUtil.get(username);
            if (permissionValueList != null) {
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                for (String peritoneums : permissionValueList) {
                    SimpleGrantedAuthority user = new SimpleGrantedAuthority(peritoneums);
                    authorities.add(user);
                }
                return new UsernamePasswordAuthenticationToken(username, token, authorities);
            }
        } else {
            throw new CustomerAuthenticationException("token验证失败");
        }
        return null;

    }
}