package com.tuniu.bi.umsj.base.annotation;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author zhangwei21
 */
public class RequestDataMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_ENC = "UTF-8";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestData.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String json = parseParameters(webRequest);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSONObject.parseObject(json, parameter.getParameterType());
    }

    /**
     * 解析request
     * 1.请求是get，入参是json或者base64，将参数转换成对应的bean
     * 2.请求是post，body是json或者base64，将参数转换成对应的bean
     *
     * @return json
     */
    protected String parseParameters(NativeWebRequest nativeRequest) throws IOException {
        HttpServletRequest servletRequest = nativeRequest.getNativeRequest(HttpServletRequest.class);
        String method = servletRequest.getMethod();

        if ("GET".equals(method) || "DELETE".equals(method)) {
            return getQueryString(servletRequest);
        }

        StringBuilder jsonstr = new StringBuilder();
        String line;
        BufferedReader br = servletRequest.getReader();

        while ((line = br.readLine()) != null) {
            jsonstr.append(line);
        }

        String str = jsonstr.toString();

        if (StringUtils.isEmpty(str)) {
            return null;
        }
        if (Base64.isBase64(str)) {
            return new String(Base64.decodeBase64(str), DEFAULT_ENC);
        } else {
            return str;
        }
    }

    /**
     * 解析get请求参数
     *
     * @return json
     */
    protected String getQueryString(HttpServletRequest request) {
        String queryStr = request.getQueryString();
        if (StringUtils.isEmpty(queryStr)) {
            return null;
        }
        String decodedStr;
        try {
            decodedStr = URLDecoder.decode(queryStr, DEFAULT_ENC);
            if (StringUtils.hasText(decodedStr)) {
                // 是否base64
                if (Base64.isBase64(decodedStr)) {
                    return new String(Base64.decodeBase64(decodedStr), DEFAULT_ENC);
                } else {
                    return decodedStr;
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }

        return null;
    }

}
