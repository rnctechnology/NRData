package com.rnctech.nrdata.test;

/* 
* @Author Zilin Chen
* @Date 2020/09/05
*/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.rnctech.nrdata.model.Person;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource(name = "defaultRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "extendRedisConfig")
    private RedisTemplate<String, Object> exdRedisTemplate;

    @Test
    public void stringRedisTest() {
        redisTemplate.opsForValue().set("name", "admin");
        exdRedisTemplate.opsForValue().set("address", "123 abcd road, San Jose CA 95120");

    }

    @Test
    public void objectRedisTest() {
        redisTemplate.opsForValue().set("person", new Person(1l, "Androw", "Chen", "yahoo", "Male"));
        exdRedisTemplate.opsForValue().set("person", new Person(2l, "Wilson", "John", "google", "Female"));
    }
    
}
