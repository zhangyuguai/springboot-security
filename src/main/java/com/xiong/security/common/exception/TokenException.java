package com.xiong.security.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author xsy
 * @date 2022/10/25 0:12
 * description:
 */
public class TokenException extends AuthenticationException {

    public TokenException(String msg) {
        super(msg);
    }
}
