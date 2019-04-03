package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.InvalidParamException;
import com.tuniu.bi.umsj.service.UserService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @RequestMapping(value = "/syncOaUser", method = RequestMethod.POST)
    public Response syncOaUser() {
        userService.syncOaUserInfo();
        return ResponseUtils.success();
    }

    /**
     * 补充用户信息
     * @return
     */
    @RequestMapping(value = "/supplyUserInfo", method = RequestMethod.POST)
    public Response supplyUserInfo(@RequestBody List<String> usernames) throws AbstractException {
        if (CollectionUtils.isEmpty(usernames)) {
            throw new InvalidParamException("参数错误");
        }
        userService.supplyUserInfo(usernames);
        return ResponseUtils.success();
    }
}
