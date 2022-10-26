package com.xiong.security.common.utools.codeEnum;

import com.xiong.security.common.utools.StatusCode;
import lombok.Getter;

/**
 * @author xsy
 * @date 2022/10/23 22:13
 * description:
 */
@Getter
public enum UnAuthCode implements StatusCode {

    //用户密码不存在
    UNAUTHORIZED(401,"用户名或密码不存在"),

    //用户权限不足
    UNAUTHCODE(403,"用户权限不足"),

    //账户过期
    ACCOUNTTIMEOUT(401,"账户已过期"),

    //token过期
    TOKENOVERDUE(401,"token过期"),

    //账户不可用
    ACCOUNTNOTUSE(401,"账户不可用"),

    //账户锁定
    ACCOUNTLOCK(401,"账户锁定"),

    //token不可用(不正确)
    TOKENNOTCORRECT(401,"token不正确");

    private final int code;

    private final String msg;


    UnAuthCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
