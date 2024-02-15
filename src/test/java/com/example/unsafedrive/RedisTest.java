package com.example.unsafedrive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void mapCacheTest(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("k",1);
        map.put("c",2);
        redisTemplate.opsForHash().putAll("key",map);

        Map<Object, Object> resultMap = redisTemplate.opsForHash().entries("key");
        System.out.println("123");
        System.out.println("123");
    }
}
