package com.suixingpay.meeting.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Huang Yafeng
 * @date: Created in 2019/12/8 18:50
 */
@Slf4j
@Component
public class RedisClient {

    public static final long TOKEN_EXPIRES_SECOND = 60 * 60;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @description 向redis中设值
     * @author Huang Yafeng
     * @date 2019/12/10 17:41
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            log.info("redis中set异常：{ }", e);
        }
        return result;
    }

    /**
     * @description 向redis中设置，同时设置过期时间
     * @author Huang Yafeng
     * @date 2019/12/10 17:42
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, String value, long time) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            expire(key, time);
            result =  true;
        } catch (Exception e) {
            log.info("redis中set异常：{ }", e);
        }
        return result;
    }

    /**
     * @description 获取redis中的值
     * @author Huang Yafeng
     * @date 2019/12/10 17:42
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        try {
            result = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.info("redis中get异常：{ }", e);
        }
        return result;

    }

    /**
     * @description 设置key的过期时间
     * @author Huang Yafeng
     * @date 2019/12/10 17:40
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        boolean result = false;
        try {
            if(time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
                result = true;
            }
        } catch (Exception e) {
            log.info("redis中expire异常：{ }", e);
        }
        return result;
    }

    /**
     * @description 根据key删除对应value
     * @author Huang Yafeng
     * @date 2019/12/10 17:40
     * @param key
     * @return
     */
    public boolean remove(String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            log.info("redis中remove异常：{ }", e);
        }
        return result;
    }

}
