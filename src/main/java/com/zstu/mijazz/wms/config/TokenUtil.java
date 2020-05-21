package com.zstu.mijazz.wms.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zstu.mijazz.wms.entity.Employee;

import java.util.Date;
import java.util.Map;

public class TokenUtil {
    private static final long EXPIRE = 60 * 60 * 1000;
    private static final String SALT = "mijazz";
    private static final String ISSUER = "sys";

    public static String sign(Employee employee){
        String token = null;
        try {
            Date expireDate = new Date(System.currentTimeMillis() + EXPIRE);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("emName", employee.getEmName())
                    .withClaim("emId", employee.getEmId().toString())
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(SALT));
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static Map<String, Claim> verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SALT)).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        }catch (Exception e){
            return null;
        }
    }
}
