package com.xiong.security.common.utools.codeEnum;

import com.xiong.security.common.utools.StatusCode;
import lombok.Getter;

/**
 * 统一返回状态码
 */
@Getter
public enum ResultCode implements StatusCode {
    /* 成功 */
    SUCCESS(200, "成功"),

    /*网络异常、错误*/
    ERROR(500,"网络异常"),

    UNAUTHORIZED(401,"未登录,访问失败!");


    private int code;
    private String msg;

    ResultCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}