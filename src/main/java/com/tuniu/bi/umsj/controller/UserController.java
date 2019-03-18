package com.tuniu.bi.umsj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/users")
    public String index(Model model) {

        model.addAttribute("title", "用户管理");
        return "user/index";
    }

}
