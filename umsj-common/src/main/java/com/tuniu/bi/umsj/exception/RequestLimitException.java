package com.tuniu.bi.umsj.exception;

/**
 * @author zhangwei21
 */
public class RequestLimitException extends AbstractException {

    public static final int ERROR_CODE = 710008;

    public RequestLimitException() {
        super("HTTP请求超出设定的频次", ERROR_CODE);
    }

    public RequestLimitException(String message) {
        super(message, ERROR_CODE);
    }


    public RequestLimitException(String message, Throwable throwable) {
        super(message, ERROR_CODE, throwable);
    }

}
