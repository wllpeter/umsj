package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.utils.JwtUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基本的controller
 */
public class BaseController {
    /**
     * 从request中获取用户信息
     * @param request
     * @return
     */
    public String getUsernameFromToken(HttpServletRequest request) throws AbstractException {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new CommonException("token参数为空");
        }
        return JwtUtils.getUsername(token);
    }
}
