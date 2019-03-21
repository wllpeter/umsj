package com.tuniu.bi.umsj.exception;

/**
 * 业务异常类
 *
 * @author zhangwei21
 */
public abstract class AbstractException extends Exception {



    private final int code;

    public AbstractException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AbstractException(String message, int code, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
