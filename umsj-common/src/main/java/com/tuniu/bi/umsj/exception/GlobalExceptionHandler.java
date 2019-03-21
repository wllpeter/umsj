package com.tuniu.bi.umsj.exception;

import com.tuniu.bi.umsj.enums.ErrorCodeEnum;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangwei21
 * 自定义异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        Response response;
        if (e instanceof AbstractException) {
            AbstractException abstractException = (AbstractException) e;
            response = ResponseUtils.custom(false, abstractException.getCode(), abstractException.getMessage());
        } else {
            response =ResponseUtils.custom(false, ErrorCodeEnum.UNKNOWN_CODE.getCode(), e.getMessage());
        }
        return response;
    }
}
