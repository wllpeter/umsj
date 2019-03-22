package com.tuniu.bi.umsj.exception;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.tuniu.bi.umsj.enums.ErrorCodeEnum;
import com.tuniu.bi.umsj.executor.AsyncExecutorService;
import com.tuniu.bi.umsj.mapper.ExceptionLogMapper;
import com.tuniu.bi.umsj.mapper.entity.ExceptionLogEntity;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author zhangwei21
 * 自定义异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private AsyncExecutorService asyncExecutorService;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    /**
     * 分隔符
     */
    private static final String MSG_SEPERATOR = "@_@";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e, HttpServletRequest request) {
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
        ExceptionLogTask exceptionLogTask = new ExceptionLogTask(exceptionLogMapper, createExceptionLog(e, request));
        asyncExecutorService.execute(exceptionLogTask);
        return response;
    }

    /**
     * 创建异常日志类
     *
     * @return
     */
    private ExceptionLogEntity createExceptionLog(Exception e, HttpServletRequest request) {
        ExceptionLogEntity exceptionLogEntity = new ExceptionLogEntity();
        exceptionLogEntity.setIp(getRemortIP(request));
        exceptionLogEntity.setExceptionMsg(getStackTrace(e));
        int type = e instanceof AbstractException ? 0 : 1;
        exceptionLogEntity.setExceptionType(type);
        return exceptionLogEntity;
    }

    /**
     * 获取堆栈信息,避免e.getMessage
     *
     * @param throwable
     * @return
     */
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String ret;
        try {
            throwable.printStackTrace(pw);
            ret =  sw.toString();
        } finally {
            pw.close();
        }
        return ret;
    }


    /**
     * 获取远程访问主机ip地址
     *
     * @param request
     * @return
     */
    private String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
