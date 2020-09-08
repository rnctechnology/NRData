package com.rnctech.nrdata.config;

/**
 * spring-boot Setting for Redis multi-instance support
 *
 * @Author Zilin Chen
 * @Date 2020/09/01
 */

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@Configuration
public class CacheConfig {

    @Value("${spring.redis.database: 0}")
    private int dbIndex;

    @Value("${spring.redis.host: 127.0.0.1}")
    private String host;

    @Value("${spring.redis.port: 6379}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout: 600}")
    private int timeout;
    
	@Bean
    @Primary
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(dbIndex);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(timeout));// 600ms connection timeout
        JedisPoolingClientConfigurationBuilder poolingConfig = jedisClientConfiguration.usePooling();
        
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;
    }
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(new StringRedisSerializer());
	    return template;
	}
}
