package com.tuniu.bi.umsj.utils;

import com.tuniu.bi.umsj.constant.SecurityConfigConst;
import com.tuniu.bi.umsj.constant.SecurityConst;
import com.tuniu.bi.umsj.exception.CommonException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei21
 */
public class JwtUtils {

    /**
     * 生成token
     *
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", username);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConfigConst.expire))
                .signWith(SignatureAlgorithm.HS512, SecurityConfigConst.key)
                .compact();
        return "Bearer " + jwt;
    }

    /**
     * 校验token
     *
     * @param token
     */
    public static void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConst.SIGNING_KEY)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
        } catch (Exception e) {
            throw new CommonException("Invalid Token. " + e.getMessage());
        }
    }
}
