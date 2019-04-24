package com.tuniu.bi.umsj.filter;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Response<Object> custom = ResponseUtils.custom(false, HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JSONObject.toJSONString(custom));
    }
}
