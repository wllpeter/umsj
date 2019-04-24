package com.tuniu.bi.umsj.security;

import com.tuniu.bi.umsj.constant.Symbol;
import com.tuniu.bi.umsj.mapper.RolesMapper;
import com.tuniu.bi.umsj.mapper.entity.RolesEntity;
import com.tuniu.bi.umsj.mapper.entity.RolesParamEntity;
import com.tuniu.bi.umsj.mapper.entity.UserEntity;
import com.tuniu.bi.umsj.service.OaClientService;
import com.tuniu.bi.umsj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
