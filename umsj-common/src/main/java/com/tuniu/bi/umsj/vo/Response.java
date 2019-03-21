package com.tuniu.bi.umsj.vo;

/**
 * 自定义返回类型
 * @param <T>
 * @author zhangwei21
 */
public class Response<T> {
    /**
     * 默认的成功返回码
     */
    public static final int OK_CODE = 710000;

    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public Response() {
    }

    public Response(Boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;

    }
    public Response(Boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
