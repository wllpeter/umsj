package com.tuniu.bi.umsj.exception;

import com.tuniu.bi.umsj.enums.ErrorCodeEnum;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 分隔符
     */
    private static final String MSG_SEPERATOR = "@_@";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        Response response;
        String errorMsg;
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
            errorMsg = "参数校验异常";
        } else if (e instanceof AbstractException) {
            AbstractException abstractException = (AbstractException) e;
            response = ResponseUtils.custom(false, abstractException.getCode(), abstractException.getMessage());
            errorMsg = "业务异常";

        } else {
            response = ResponseUtils.custom(false, ErrorCodeEnum.UNKNOWN_CODE.getCode(), e.getMessage());
            errorMsg = "系统错误";
        }
        LOGGER.error(errorMsg, e);
        return response;
    }

}
