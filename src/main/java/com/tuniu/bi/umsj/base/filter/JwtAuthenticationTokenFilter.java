package com.tuniu.bi.umsj.base.filter;

import com.tuniu.bi.umsj.base.constant.Symbol;
import com.tuniu.bi.umsj.base.dao.entity.RolesEntity;
import com.tuniu.bi.umsj.base.dao.entity.RolesParamEntity;
import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.dao.mapper.RolesMapper;
import com.tuniu.bi.umsj.base.service.UserService;
import com.tuniu.bi.umsj.base.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangwei21
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private UserService userService;

    private RolesMapper rolesMapper;

    public JwtAuthenticationTokenFilter(UserService userService, RolesMapper rolesMapper) {
        this.userService = userService;
        this.rolesMapper = rolesMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader("token");

            if (StringUtils.hasText(bearerToken) && JwtUtils.checkToken(bearerToken)) {
                String username = JwtUtils.getUsername(bearerToken);
                // 获取权限
                UserEntity userEntity = userService.init(username);
                // 查询角色
                List<GrantedAuthority> authorities = new ArrayList();
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
                List<GrantedAuthority> list = authorities.stream().distinct().collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, list);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }
}
