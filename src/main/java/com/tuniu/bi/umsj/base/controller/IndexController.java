package com.tuniu.bi.umsj.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("systemCode", "UMSJ");
        model.addAttribute("systemName", "平台管理系统");
        return "index/index";
    }

    @RequestMapping("/info")
    public String info(Model model) {
        model.addAttribute("systemCode", "UMSJ");
        return "index/info";
    }
}
