package com.tuniu.bi.umsj.base.exception;

/**
 * @author zhangwei21
 */
public class CommonException extends AbstractException {

    private static final int ERROR_CODE = 1;

    private static final String MSG = "逻辑处理异常";

    public CommonException() {
        super(MSG, ERROR_CODE);
    }

    public CommonException(String message) {
        super(message, ERROR_CODE);
    }


    public CommonException(String message, Throwable throwable) {
        super(message, ERROR_CODE, throwable);
    }

}
