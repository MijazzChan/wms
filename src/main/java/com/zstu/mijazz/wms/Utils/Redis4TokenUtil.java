package com.zstu.mijazz.wms.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author MijazzChan
 * @stuID ZSTU.2017326603075
 * Created on 5/27/2020.
 */
@Service
public class Redis4TokenUtil {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void putJWT(String emId, String token) {
        redisTemplate.opsForValue().set(emId, token, 60 * 60);
    }

    public void killJWT(String emId) {
        redisTemplate.delete(emId);
    }

    public Boolean hasJWT(String emId) {
        return redisTemplate.hasKey(emId);
    }
}
