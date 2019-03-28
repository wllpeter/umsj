package com.tuniu.bi.umsj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangwei21
 */
@SpringBootApplication
@RestController
public class UmsjWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsjWebApplication.class, args);
    }

    @RequestMapping("/ping")
    public String ping() {
        // 获取jdk版本号
        String s = System.getProperty("java.version");
        String active = System.getProperty("spring.profiles.active");
        return "pong! " + s + "|" + active;

    }
}
