package com.tuniu.bi.umsj.exception;

public class CommonException extends Exception {

    public static final int ERROR_CODE = 710000;
    public static final String SQL_EXIST = "SQL别名已存在!";
    public static final String DATA_LIMIT = "已限制一分钟调用次数10次!";
    public static final String BAD_PARAM = "参数类型不匹配!";

    public CommonException() {
        super("OK");
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CommonException(Object data, String message) {
        super(message);
    }

    public CommonException(Object data, String message, Throwable throwable) {
        super(message, throwable);
    }
}
