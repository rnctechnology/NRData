package com.rnctech.nrdata.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Optional Redis instances into Spring
 * This for production environment
 */

//@Configuration
//@EnableCaching
public class OptionalRedisConfig extends RedisConfig {


    @Value("${spring.redis3.database}")
    private int dbIndex;

    @Value("${spring.redis3.host}")
    private String host;

    @Value("${spring.redis3.port}")
    private int port;

    @Value("${spring.redis3.password}")
    private String password;

    @Value("${spring.redis3.timeout}")
    private int timeout;

    /**
     * Configure redis optional connection factory
     */
    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        return createJedisConnectionFactory(dbIndex, host, port, password, timeout);
    }

    @Bean(name = "cacheRedisTemplate")
    public RedisTemplate<String, Serializable> cacheRedisTemplate() {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(cacheRedisConnectionFactory());
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

}
