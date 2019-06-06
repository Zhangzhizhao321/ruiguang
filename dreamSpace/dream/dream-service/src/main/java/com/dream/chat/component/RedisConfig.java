package com.dream.chat.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisUrl;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.timeout}")
    private Integer redisTimeout;


    @Value("${spring.redis.database}")
    private Integer dataBase;

    @Value("${spring.redis.password}")
    private String password;

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = this.jedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config, redisUrl, redisPort,redisTimeout,password,dataBase);
        return jedisPool;
    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(dataBase);
        redisStandaloneConfiguration.setHostName(redisUrl);
        redisStandaloneConfiguration.setPort(redisPort);
        //System.out.println(password);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }
}
