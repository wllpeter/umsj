package com.tuniu.bi.umsj.base.entitydo;

/**
 * 抽象返回类
 *
 * @author zhangwei21
 */
public abstract class AbstractResponseDO {

    private Boolean success;

    private Integer errorCode;

    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
