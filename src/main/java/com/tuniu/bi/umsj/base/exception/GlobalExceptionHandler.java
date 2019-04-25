package com.tuniu.bi.umsj.base.exception;

import com.tuniu.bi.umsj.base.enums.ErrorCodeEnum;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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
        Response response = ResponseUtils.custom(false, ErrorCodeEnum.UNKNOWN_CODE.getCode(), e.getMessage());
        String errorMsg = "系统错误";
        LOGGER.error(errorMsg, e);
        return response;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            // 多个错误之间用@_@分隔
            stringBuilder.append(error.getDefaultMessage()).append(MSG_SEPERATOR);
        }
        // 删除最后一个
        if (stringBuilder.length() > MSG_SEPERATOR.length()) {
            stringBuilder.delete(stringBuilder.length() - MSG_SEPERATOR.length(), stringBuilder.length());
        }
        Response response = ResponseUtils.custom(false, ErrorCodeEnum.BUSINESS_ERROR.getCode(), stringBuilder.toString());
        String errorMsg = "参数校验异常";
        LOGGER.error(errorMsg, e);
        return response;
    }

    @ExceptionHandler(value = AbstractException.class)
    @ResponseBody
    public Response exceptionHandler(AbstractException e) {
        Response response = ResponseUtils.custom(false, e.getCode(), e.getMessage());
        String errorMsg = "业务异常";
        LOGGER.error(errorMsg, e);
        return response;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Response exceptionHandler(HttpServletResponse httpServletResponse, AccessDeniedException e) {
        Response response = ResponseUtils.custom(false, HttpStatus.FORBIDDEN.value(), e.getMessage());
        String errorMsg = "权限校验异常";
        LOGGER.error(errorMsg, e);
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return response;
    }
}
