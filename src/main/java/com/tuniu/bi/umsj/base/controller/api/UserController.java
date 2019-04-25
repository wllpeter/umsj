package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.service.OaClientService;
import com.tuniu.bi.umsj.base.service.UserService;
import com.tuniu.bi.umsj.base.utils.JwtUtils;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.Response;
import com.tuniu.bi.umsj.base.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei21
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OaClientService oaClientService;

    @RequestMapping("/findUser")
    public ResponseEntity<UserEntity> findUser(@Param("id") Integer id) {
        UserEntity byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }

    /**
     * 登录（先注释，目前已经在拦截器中拦截）
     * @param user
     * @return Response<Map<String, String>>
     */
    //@RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response< Map<String, String>> login(@RequestBody @Valid User user) throws AbstractException {
        String username = user.getUsername();
        String password = user.getPassword();
        // ldap 认证
        oaClientService.checkOaAccount(username, password);
        // 自动创建账户信息
        UserEntity userEntity = userService.init(username);
        // 返回token
        String token = JwtUtils.generateToken(username);
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ResponseUtils.success(map);
    }
}
