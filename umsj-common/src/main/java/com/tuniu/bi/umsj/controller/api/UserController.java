package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import com.tuniu.bi.umsj.service.OaClientService;
import com.tuniu.bi.umsj.service.UserService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.Response;
import com.tuniu.bi.umsj.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OaClientService oaClientService;

    @RequestMapping("/login")
    public Map<String, Object> index() {
        Map<String, Object> data = new HashMap<>();

        data.put("username", "lujian2");
        data.put("phone", "13401952376");
        data.put("age", 25);
        data.put("class", "三年级24班");

        return data;
    }

    @RequestMapping("/findUser")
    public ResponseEntity<UserEntity> findUser(@Param("id") Integer id) {
        UserEntity byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<UserEntity> login(@RequestBody @Valid User user) throws AbstractException {
        String username = user.getUsername();
        String password = user.getPassword();
        // ldap 认证
        oaClientService.checkOaAccount(username, password);
        // 自动创建账户信息
        UserEntity userEntity = userService.init(username);
        return ResponseUtils.success(userEntity);
    }
}
