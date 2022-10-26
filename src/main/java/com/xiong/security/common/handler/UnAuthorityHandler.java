package com.xiong.security.common.handler;

import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.UnAuthCode;
import com.xiong.security.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xsy
 * @date 2022/10/23 20:13
 * description:
 * 授权失败处理器
 */
public class UnAuthorityHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ResponseUtil.out(response,new Result(UnAuthCode.UNAUTHCODE));
    }
}
