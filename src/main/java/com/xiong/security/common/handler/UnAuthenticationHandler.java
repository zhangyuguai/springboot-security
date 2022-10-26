package com.xiong.security.common.handler;

import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.UnAuthCode;
import com.xiong.security.utils.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 * 处理认证时出现的异常:用户名或密码错误，账户被锁定，账户不可用，账户过期
 */
public class UnAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if ( e instanceof UsernameNotFoundException) {
            //用户名或密码错误
            ResponseUtil.out(httpServletResponse,new Result(UnAuthCode.UNAUTHORIZED));
        }else if(e instanceof AccountExpiredException){
            //账户过期
            ResponseUtil.out(httpServletResponse,new Result(UnAuthCode.ACCOUNTTIMEOUT));
        }else if(e instanceof DisabledException){
            //账户不可用
            ResponseUtil.out(httpServletResponse,new Result(UnAuthCode.ACCOUNTNOTUSE));
        }else if(e instanceof LockedException){
            //账户锁定
            ResponseUtil.out(httpServletResponse,new Result(UnAuthCode.ACCOUNTLOCK));

        }else if(e instanceof BadCredentialsException){
            ResponseUtil.out(httpServletResponse,new Result(UnAuthCode.TOKENNOTCORRECT));
        }else if(e instanceof InsufficientAuthenticationException){
            //token不可用(不正确)
            //token缺失
            ResponseUtil.out(httpServletResponse,new Result(UnAuthCode.TOKENNOTCORRECT));
        }
    }

}