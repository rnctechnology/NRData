package com.rnctech.nrdata.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * spring-boot Setting for Redis multi-instance support
 *
 * @Author Zilin Chen
 * @Date 2020/09/07
 */

//@EnableCaching
//@Configuration
public class RedisConfig {
    @Value("${spring.redis.pool.max-active}")
    private int redisPoolMaxActive;

    @Value("${spring.redis.pool.max-wait}")
    private int redisPoolMaxWait;

    @Value("${spring.redis.pool.max-idle}")
    private int redisPoolMaxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int redisPoolMinIdle;

    /**
     * Configuration Key Generation
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(o.getClass().getName())
                        .append(method.getName());
                for (Object object : objects) {
                    stringBuilder.append(object.toString());
                }
                return stringBuilder.toString();
            }
        };
    }

    /**
     * Create redis connection factory
     */
    public JedisConnectionFactory createJedisConnectionFactory(int dbIndex, String host, int port, String password, int timeout) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(dbIndex);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(timeout));// 600ms connection timeout
        JedisPoolingClientConfigurationBuilder poolingConfig = jedisClientConfiguration.usePooling();
        poolingConfig.poolConfig(setPoolConfig(redisPoolMaxIdle, redisPoolMinIdle, redisPoolMaxActive, redisPoolMaxWait, true));
        
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return jedisConFactory;
    }
    
    /**
     * Setting connection pool properties
     */
    public JedisPoolConfig setPoolConfig(int maxIdle, int minIdle, int maxActive, int maxWait, boolean testOnBorrow) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }
   

    /**
     * Configuring Cache Manager
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = RedisCacheManager.create(createJedisConnectionFactory(0,"localhost",6379,"Admin123", 600));//new RedisCacheManager(redisTemplate);
        return redisCacheManager;
    }

    /**
     * Setting the serialization mode of RedisTemplate
     */
    public void setSerializer(RedisTemplate<String, Serializable> redisTemplate) {
    	redisTemplate.afterPropertiesSet();
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    }
}