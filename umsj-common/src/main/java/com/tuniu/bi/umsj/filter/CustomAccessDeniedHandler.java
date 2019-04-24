package com.tuniu.bi.umsj.filter;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangwei21
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Response<Object> custom = ResponseUtils.custom(false, HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JSONObject.toJSONString(custom));
    }
}
