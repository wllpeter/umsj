package com.tuniu.bi.umsj.base.utils;

import com.tuniu.bi.umsj.base.constant.SecurityConfigConst;
import com.tuniu.bi.umsj.base.exception.CommonException;
import io.jsonwebtoken.Claims;
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
        Jwts.parser()
                .setSigningKey(SecurityConfigConst.key)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    /**
     * 校验token
     *
     * @param token
     */
    public static String getUsername(String token) {
        String username = "";
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConfigConst.key)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            username = (String) claims.get("username");
        } catch (Exception e) {
            throw new CommonException("Invalid Token. " + e.getMessage());
        }
        return username;
    }


    /**
     * 校验token
     *
     * @param token
     */
    public static boolean checkToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConfigConst.key)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            return true;
        } catch (Exception e) {
            throw new CommonException("Invalid Token. " + e.getMessage());
        }
    }
}
