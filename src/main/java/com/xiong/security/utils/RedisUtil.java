package com.xiong.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xsy
 * @date 2022/11/5 16:00
 * description:
 */
@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

   public  Object get(String key){
       return redisTemplate.opsForValue().get(key);
   }

   public void set(String key,Object value){
       redisTemplate.opsForValue().set(key,value);
   }

   public void remove(String key){
       redisTemplate.delete(key);
   }

    //存缓存
    public void set(String key ,String value,Long timeOut){
        redisTemplate.opsForValue().set(key,value,timeOut, TimeUnit.SECONDS);
    }

}
