package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.service.UserService;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.Response;
import com.tuniu.bi.umsj.uds.dao.entity.InfoEntity;
import com.tuniu.bi.umsj.uds.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @Autowired
    private InfoService infoService;

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
            throw new CommonException("参数错误");
        }
        userService.supplyUserInfo(usernames);
        return ResponseUtils.success();
    }

    /**
     * 补充用户信息
     * @return
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public Response getInfo(@Param("id") Integer id) throws AbstractException {
        InfoEntity byPk = infoService.findByPk(id);
        return ResponseUtils.success(byPk);
    }

    /**
     * 补充用户信息
     * @return
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Response getUser(@Param("id") Integer id) throws AbstractException {
        UserEntity byId = userService.findById(id);
        return ResponseUtils.success(byId);
    }

    /**
     * 补充用户信息
     * @return
     */
    @RequestMapping(value = "/insertInfo", method = RequestMethod.POST)
    public Response insertInfo(@RequestBody InfoEntity infoEntity) throws AbstractException {
        int insert = infoService.insert(infoEntity);
        return ResponseUtils.success(insert);
    }
}
