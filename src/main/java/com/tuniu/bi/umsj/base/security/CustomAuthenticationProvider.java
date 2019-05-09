package com.tuniu.bi.umsj.base.security;

import com.tuniu.bi.umsj.base.constant.Symbol;
import com.tuniu.bi.umsj.base.dao.entity.RolesEntity;
import com.tuniu.bi.umsj.base.dao.entity.RolesParamEntity;
import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.dao.mapper.RolesMapper;
import com.tuniu.bi.umsj.base.service.OaClientService;
import com.tuniu.bi.umsj.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangwei21
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private OaClientService oaClientService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserEntity userEntity;
        List<GrantedAuthority> authorities = new ArrayList();
        try {
            oaClientService.checkOaAccount(username, password);
            userEntity = userService.init(username);
            if (userEntity == null) {
                throw new UsernameNotFoundException("用户名找不到用户!");
            }
            // 查询角色
            String roleCodes = userEntity.getRoleCodes();
            if (!StringUtils.isEmpty(roleCodes)) {
                List<String> codes = Arrays.asList(roleCodes.split(Symbol.COMMA));
                RolesParamEntity rolesParamEntity = new RolesParamEntity();
                rolesParamEntity.setCodes(codes);
                List<RolesEntity> many = rolesMapper.findMany(rolesParamEntity);
                for (RolesEntity rolesEntity : many) {
                    String actions = rolesEntity.getActions();
                    if (!StringUtils.isEmpty(actions)) {
                        String[] split = actions.split(Symbol.COMMA);
                        for (String tmp : split) {
                            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(tmp);
                            authorities.add(simpleGrantedAuthority);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
        List<GrantedAuthority> list = authorities.stream().distinct().collect(Collectors.toList());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), list);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
