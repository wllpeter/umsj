package com.tuniu.bi.umsj.utils;

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
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("username", username);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(SignatureAlgorithm.HS512, SecurityConst.SIGNING_KEY)
                .compact();
        return "Bearer "+jwt; //jwt前面一般都会加Bearer
    }

    /**
     * 校验token
     * @param token
     */
    public static void validateToken(String token) {
        try {
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SecurityConst.SIGNING_KEY)
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody();
        }catch (Exception e){
            throw new CommonException("Invalid Token. "+e.getMessage());
        }
    }
}
