package com.tuniu.bi.umsj.exception;

public class CommonException extends AbstractException {

    public static final int ERROR_CODE = 710007;

    public static final String SQL_EXIST = "SQL别名已存在!";
    public static final String DATA_LIMIT = "已限制一分钟调用次数10次!";
    public static final String BAD_PARAM = "参数类型不匹配!";

    public CommonException() {
        super("逻辑处理异常", ERROR_CODE);
    }

    public CommonException(String message) {
        super(message, ERROR_CODE);
    }

    public CommonException(String message, Throwable throwable) {
        super(message, ERROR_CODE, throwable);
    }

}
