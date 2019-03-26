package com.tuniu.bi.umsj.enums;

/**
 * 错误码枚举类
 *
 * @author zhangwei21
 */
public enum ErrorCodeEnum {

    /**
     * 操作成功返回码
     */
    OK(710000, "OK"),
    /**
     * 参数错误返回码
     */
    INVALID_PARAM_ERROR(710003, "参数错误"),

    /**
     * 未知错误
     */
    UNKNOWN_CODE(-1, "未知错误");

    /**
     * code
     */
    private Integer code;

    /**
     * 返回的信息
     */
    private String msg;


    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
