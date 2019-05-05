package com.tuniu.bi.umsj;

import com.tuniu.bi.umsj.base.annotation.RequestLimit;
import com.tuniu.bi.umsj.base.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangwei21
 */
@SpringBootApplication
@RestController
public class UmsjWebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsjWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UmsjWebApplication.class, args);
    }

    @RequestLimit(count = 5)
    @RequestMapping("/api/ping")
    //@PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    public String ping() {
        // 获取jdk版本号
        String s = System.getProperty("java.version");
        String active = System.getProperty("spring.profiles.active");
        return "你好pong! " + s + "|" + active;

    }

    //@Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
