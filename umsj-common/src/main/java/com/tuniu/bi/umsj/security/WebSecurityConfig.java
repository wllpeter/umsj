package com.tuniu.bi.umsj.security;

import com.tuniu.bi.umsj.filter.CustomAccessDeniedHandler;
import com.tuniu.bi.umsj.filter.CustomAuthenticationEntryPoint;
import com.tuniu.bi.umsj.filter.JwtAuthenticationTokenFilter;
import com.tuniu.bi.umsj.filter.JwtLoginFilter;
import com.tuniu.bi.umsj.mapper.RolesMapper;
import com.tuniu.bi.umsj.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zhangwei21
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 后期做成配置的，放到数据库中
     */
    private static final String[] PERMIT_URL = {"/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/api/alert/**","/api/fix/**"};

    @Autowired
    private AuthenticationProvider customAuthenticationProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RolesMapper rolesMapper;

    @Bean
    public JwtLoginFilter jwtLoginFilter() throws Exception {
        return new JwtLoginFilter(authenticationManager());
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(userService, rolesMapper);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().authorizeRequests().antMatchers("/api/user/login", "/api/fix/**", "/api/alert/**", "/api/ping").permitAll().anyRequest().authenticated()
        .and().addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class).
         addFilterBefore(jwtAuthenticationTokenFilter(), JwtLoginFilter.class)
        .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }
}
