package com.zstu.mijazz.wms.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zstu.mijazz.wms.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenUtil {

    @Autowired
    Redis4TokenUtil redis4TokenUtil;

    private static final long EXPIRE = 60 * 60 * 1000;  // AKA 1hour
    private static final String SALT = "SALT";
    private static final String ISSUER = "SYS";

    public String sign(Employee employee) {
        String token = null;
        try {
            Date expireDate = new Date(System.currentTimeMillis() + EXPIRE);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("emName", employee.getEmName())
                    .withClaim("emId", employee.getEmId().toString())
                    .withClaim("isAdmin", employee.isAdmin())
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(SALT));
            redis4TokenUtil.putJWT(employee.getEmId().toString(), token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public Map<String, Claim> verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SALT)).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            if (!redis4TokenUtil.hasJWT(claims.get("emId").asString())) {
                return null;
            }
            return jwt.getClaims();
        } catch (Exception e) {
            return null;
        }
    }
}
