package com.tuniu.bi.umsj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UmsjApplication {

    public static void main(String[] args) {
        // 完全禁用 devtools
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(UmsjApplication.class, args);
    }

    @RequestMapping("/ping")
    public String ping() {
        return "pong! ";
    }
}
