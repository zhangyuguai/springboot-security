package com.xiong.security.common.utools.codeEnum;

/**
 * 统一返回状态码
 */
public enum ResultEnum{
    /* 成功 */
    SUCCESS(200, "成功"),

    /*网络异常、错误*/
    ERROR(500,"网络异常"),

    UNAUTHORIZED(401,"未登录,访问失败!");


    private int code;
    private String msg;

    ResultEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 根据code获取message
     *
     * @param code 状态码
     * @return msg
     */
    public static String getMsgByCode(Integer code) {
        for (ResultEnum ele : values()) {
            if (ele.getCode()==code) {
                return ele.getMsg();
            }
        }
        return null;
    }
}