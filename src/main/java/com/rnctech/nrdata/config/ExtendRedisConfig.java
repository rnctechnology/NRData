package com.rnctech.nrdata.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * extended Redis instances into Spring
 * This for production environment
 */

//@Configuration
//@EnableCaching
public class ExtendRedisConfig extends RedisConfig {


    @Value("${spring.redis2.database}")
    private int dbIndex;

    @Value("${spring.redis2.host}")
    private String host;

    @Value("${spring.redis2.port}")
    private int port;

    @Value("${spring.redis2.password}")
    private String password;

    @Value("${spring.redis2.timeout}")
    private int timeout;

    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        return createJedisConnectionFactory(dbIndex, host, port, password, timeout);
    }

    @Bean(name = "cacheRedisTemplate")
    public RedisTemplate<String, Serializable> cacheRedisTemplate() {
        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
        template.setConnectionFactory(cacheRedisConnectionFactory());
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

}
