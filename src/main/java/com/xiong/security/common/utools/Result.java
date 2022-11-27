package com.xiong.security.common.utools;

import com.xiong.security.common.utools.codeEnum.ResultCode;
import lombok.Data;

/**
 * 封装统一返回实体类
 * 继承HashMap 可随时put自定义key-value
 */
@Data
public class Result {
    /**
     * 状态码
     */
    public Integer code;

    /**
     * 消息
     */
    public String msg;

    //vue-admin用error会被拦截
    private Boolean success;

    /**
     * 数据对象
     */
    private Object data;

    public Result() {
    }

    //只返回状态码
    public Result(StatusCode statusCode) {
        this.code= statusCode.getCode();
        this.msg=statusCode.getMsg();
        this.data=null;
    }


    //返回指定的返回码,数据对象
    public Result(StatusCode statusCode,Object data){
        this.code=statusCode.getCode();
        this.msg=statusCode.getMsg();
        this.data=data;
    }

    //手动设置返回
    public Result(int code, String msg, Object data) {
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    //手动设置返回
    public Result(int code, String msg, Object data,Boolean success) {
        this.code=code;
        this.msg=msg;
        this.data=data;
        this.success=success;
    }

    // 默认返回成功状态码，数据对象
    public Result(Object data){
        this.code= ResultCode.SUCCESS.getCode();
        this.msg= ResultCode.SUCCESS.getMsg();
        this.data=data;
    }





}