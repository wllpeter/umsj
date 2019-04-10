package com.tuniu.bi.umsj;

import com.tuniu.bi.umsj.annotation.RequestLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author zhangwei21
 */
@SpringBootApplication
@RestController
public class UmsjWebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsjWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UmsjWebApplication.class, args);
    }

    @RequestLimit(count = 5)
    @RequestMapping("/ping")
    public String ping() {
        // 获取jdk版本号
        String s = System.getProperty("java.version");
        String active = System.getProperty("spring.profiles.active");
        return "pong! " + s + "|" + active;

    }

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void run(String... args) throws Exception {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String bean : beans) {
            LOGGER.info("================" + appContext.getBean(bean).getClass());
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UmsjWebApplication.class);
    }

}
