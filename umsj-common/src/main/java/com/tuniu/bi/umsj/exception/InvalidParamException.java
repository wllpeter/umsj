package com.tuniu.bi.umsj.exception;

/**
 * 参数错误异常
 *
 * @author zhangwei21
 */
public class InvalidParamException extends AbstractException {

    public static final int ERROR_CODE = 710003;

    private static final String MSG = "参数错误";

    public InvalidParamException() {
        super(MSG, ERROR_CODE);
    }

    public InvalidParamException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidParamException(String message, Throwable throwable) {
        super(message, ERROR_CODE, throwable);
    }
}
