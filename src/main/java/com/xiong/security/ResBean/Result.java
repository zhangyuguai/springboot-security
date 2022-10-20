package com.xiong.security.ResBean;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */

import com.xiong.security.common.utools.codeEnum.ResultEnum;

import java.util.HashMap;

/**
 * 封装统一返回实体类
 * 继承HashMap 可随时put自定义key-value
 */
public class Result extends HashMap<String,Object> {
    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 消息 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    public Result() {
    }

    public Result(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public Result(Integer code, String msg, Object obj) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (obj!=null)
        {
            super.put(DATA_TAG, obj);
        }
    }

    public static Result success(){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }
    public static Result success(Object obj){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),obj);
    }
    public static Result error(){
        return new Result(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
    }
    public static Result error(String msg){
        return new Result(ResultEnum.ERROR.getCode(),msg);
    }
    public static Result error(Integer code,String msg){
        return new Result(code,msg);
    }

}
