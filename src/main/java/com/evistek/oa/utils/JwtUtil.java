package com.evistek.oa.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/4
 */
public class JwtUtil {
    //token失效期, 单位：天
    public static final int EXPIRATION = 1;

    //加密算法
    private static Algorithm algorithm = Algorithm.HMAC256("secret");

    //生成token
    public static String getToken(String email) {
        String token = "";
        try {
            //过期时间
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.DAY_OF_YEAR, EXPIRATION);
            Date expireTime = nowTime.getTime();
            token = JWT.create()
                    .withClaim("email", email)
                    .withExpiresAt(expireTime)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            System.out.println(exception);
        }
        return token;
    }

    //从token中获取email
    public static String getEmail(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            boolean isExpired = jwt.getExpiresAt().before(new Date());
            if (!isExpired) {
                return jwt.getClaim("email").asString();
            }
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
