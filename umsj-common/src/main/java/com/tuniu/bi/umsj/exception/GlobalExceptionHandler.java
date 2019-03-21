package com.tuniu.bi.umsj.exception;

import com.tuniu.bi.umsj.enums.ErrorCodeEnum;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangwei21
 * 自定义异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 分隔符
     */
    private static final String MSG_SEPERATOR = "@_@";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        Response response;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                // 多个错误之间用@_@分隔
                stringBuilder.append(error.getDefaultMessage()).append(MSG_SEPERATOR);
            }
            // 删除最后一个
            if (stringBuilder.length() > MSG_SEPERATOR.length()) {
                stringBuilder.delete(stringBuilder.length() - MSG_SEPERATOR.length(), stringBuilder.length());
            }
            response = ResponseUtils.custom(false, ErrorCodeEnum.INVALID_PARAM_ERROR.getCode(), stringBuilder.toString());
        } else if (e instanceof AbstractException) {
            AbstractException abstractException = (AbstractException) e;
            response = ResponseUtils.custom(false, abstractException.getCode(), abstractException.getMessage());
        } else {
            response = ResponseUtils.custom(false, ErrorCodeEnum.UNKNOWN_CODE.getCode(), e.getMessage());
        }
        return response;
    }
}
