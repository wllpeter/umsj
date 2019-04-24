package com.tuniu.bi.umsj.filter;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.utils.JwtUtils;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei21
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
        setAuthenticationManager(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        username = username.trim();
        password = password.trim();
        if (checkUsernameAndPassword(username, password)) {
            throw new AuthenticationServiceException(
                    "Authentication parameter username or password is blank");
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private boolean checkUsernameAndPassword(String username, String password) {
        return StringUtils.isEmpty(username) || StringUtils.isEmpty(password);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 将token写到返回值中
        String username = (String) authResult.getPrincipal();
        String token = JwtUtils.generateToken(username);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        Response<Map<String, String>> success = ResponseUtils.success(map);
        response.getWriter().write(JSONObject.toJSONString(success));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Response<Map<String, String>> error = ResponseUtils.custom(false, HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JSONObject.toJSONString(error));
    }
}
