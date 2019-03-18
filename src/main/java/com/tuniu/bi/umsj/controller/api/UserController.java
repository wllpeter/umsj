package com.tuniu.bi.umsj.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @RequestMapping("/login")
    public Map<String, Object> index() {
        Map<String, Object> data = new HashMap<>();

        data.put("username", "lujian2");
        data.put("phone", "13401952376");
        data.put("age", 25);
        data.put("class", "三年级24班");

        return data;
    }
}
