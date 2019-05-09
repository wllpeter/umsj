package com.tuniu.bi.umsj.base.advice;

import com.tuniu.bi.umsj.base.vo.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class DefaultResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getDeclaringClass().isAnnotationPresent(RestController.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Response response = new Response(true, 0, "");
        if (o == null) {
            return response;
        } else if (o instanceof Response) {
            response = (Response) o;
        } else {
            response.setData(o);
        }
        return response;
    }
}
