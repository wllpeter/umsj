package com.tuniu.bi.umsj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author zhangwei21
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 后期做成配置的，放到数据库中
     */
    private static final String[] PERMIT_URL = {"/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/api/alert/**","/api/fix/**"};

    @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
//        http.formLogin().permitAll().
//                and().authorizeRequests().antMatchers(PERMIT_URL).permitAll().
//                //and().authorizeRequests().antMatchers("/ping").hasAnyAuthority("SYS_ADMIN").
//                and().authorizeRequests().anyRequest().authenticated().
//                and().httpBasic().
//                and().csrf().disable();
    }
}
