package com.tuniu.bi.umsj.controller;

import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import com.tuniu.bi.umsj.vo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 *
 * @author zhangwei21
 */
@RestController
@RequestMapping("/user")
public class UserContorller {

    @Value("${ldap.context.factory}")
    private String factory;

    @Value("${ldap.provider.url}")
    private String providerUrl;

    @Value("${ldap.security.authentication}")
    private String authentication;

    @Value("${ldap.security.principal.suffix}")
    private String suffix;

    /**
     * 登陆
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<User> login(@RequestBody User user) {
        return ResponseUtils.success();
    }
}
