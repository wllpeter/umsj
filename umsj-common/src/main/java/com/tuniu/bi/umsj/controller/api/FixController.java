package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangwei21
 */
@RestController
@RequestMapping("/api/fix")
public class FixController {

    @Autowired
    private UserService userService;

    /**
     * 同步oaUser的信息到User
     *
     * @return
     */
    @RequestMapping(value = "syncOaUser", method = RequestMethod.POST)
    public String syncOaUser() {
        userService.syncOaUserInfo();
        return "同步成功";
    }
}
