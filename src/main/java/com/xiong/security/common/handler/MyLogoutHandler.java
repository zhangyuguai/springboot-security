package com.xiong.security.common.handler;

import com.xiong.security.utils.RedisUtil;
import com.xiong.security.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
public class MyLogoutHandler implements LogoutHandler {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if(token!=null){
            //删除token
            redisUtil.remove("token_"+token);
            String username = tokenManager.getUserInfoFromToken(token);
            //删除用户权限等信息
            redisUtil.remove(username);
        }
    }
}
