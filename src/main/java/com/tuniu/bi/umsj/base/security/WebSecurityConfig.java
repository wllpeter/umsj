package com.tuniu.bi.umsj.base.security;

import com.tuniu.bi.umsj.base.dao.mapper.RolesMapper;
import com.tuniu.bi.umsj.base.filter.CustomAccessDeniedHandler;
import com.tuniu.bi.umsj.base.filter.CustomAuthenticationEntryPoint;
import com.tuniu.bi.umsj.base.filter.JwtAuthenticationTokenFilter;
import com.tuniu.bi.umsj.base.filter.JwtLoginFilter;
import com.tuniu.bi.umsj.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zhangwei21
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 后期做成配置的，放到数据库中
     */
    private static final String[] PERMIT_URL = {
            "/",
            "/**/*.js",
            "/**/*.jpg",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.css",
            "/**/*.woff",
            "/**/*.ttf",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/**/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html",
            "/api/alert/**",
            "/api/fix/**",
            "/api/ping"
    };

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
                and().authorizeRequests().antMatchers(PERMIT_URL).permitAll().anyRequest().authenticated()
                .and().addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class).
                addFilterBefore(jwtAuthenticationTokenFilter(), JwtLoginFilter.class)
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }
}
