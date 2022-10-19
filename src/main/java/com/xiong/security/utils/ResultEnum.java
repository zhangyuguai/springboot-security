package com.xiong.security.utils;

/**
 * 统一返回状态码
 */
public enum ResultEnum{
    /* 成功 */
    SUCCESS(200, "成功"),

    /*网络异常、错误*/
    ERROR(500,"网络异常"),


    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失");

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