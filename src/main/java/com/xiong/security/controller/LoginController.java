package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.common.utools.codeEnum.ResultEnum;
import com.xiong.security.service.UserService;
import com.xiong.security.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login_p")
    public void login(HttpServletResponse response) throws IOException {
        ResponseUtil.out(response, Result.error(ResultEnum.UNAUTHORIZED.getCode(),ResultEnum.getMsgByCode(ResultEnum.UNAUTHORIZED.getCode())));
    }
}
