package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import com.tuniu.bi.umsj.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
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
}
