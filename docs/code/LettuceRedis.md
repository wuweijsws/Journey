````
package com.lettuce.conf;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * redis配置
 *
 * @Author: wuwei
 * @Date: 2019-05-07 11:41
 */
@Configuration
public class LettuceRedisConf {

    @Bean(name = "redisCangWuTemplate")
    public StringRedisTemplate redisCangwuTemplate(
            @Value("${redis.cangwu.host}") String hostName,
            @Value("${redis.cangwu.port}") int port,
            @Value("${redis.cangwu.password}") String password,
            @Value("${redis.cangwu.pool.max-idle}") int maxIdle,
            @Value("${redis.cangwu.pool.min-idle}") int minIdle,
            @Value("${redis.cangwu.pool.max-active}") int maxActive,
            @Value("${redis.cangwu.pool.max-wait}") long maxWaitMillis,
            @Value("${redis.cangwu.pool.timeout}") long timeout,
            @Value("${redis.cangwu.db.index}") int db) {
        StringRedisTemplate temple = new StringRedisTemplate();
        temple.setConnectionFactory(connectionFactory(hostName, port, password,
                maxIdle, minIdle, maxActive, maxWaitMillis, db, timeout));

        return temple;
    }

    /**
     * redis连接工厂
     * @param hostName 地址
     * @param port 端口
     * @param password 密码
     * @param maxIdle 最大空闲进程
     * @param minIdle 最小空闲进程
     * @param maxActive 线程最大存活时间
     * @param maxWaitMillis 最大等待时间
     * @param timeout 超时时间
     * @param db 几库
     * @return RedisConnectionFactory
     */
    private static RedisConnectionFactory connectionFactory(String hostName, int port,
                                                     String password, int maxIdle, int minIdle, int maxActive,
                                                     long maxWaitMillis, int db, long timeout) {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(db);
        return new LettuceConnectionFactory(redisStandaloneConfiguration, poolConfig(maxIdle, minIdle, maxActive, maxWaitMillis, timeout));
    }

    /**
     * 线程池
     * @param maxIdle 最大空闲进程
     * @param minIdle 最小空闲进程
     * @param maxActive 线程最大存活时间
     * @param maxWaitMillis 最大等待时间
     * @param timeout 超时时间
     * @return LettucePoolingClientConfiguration
     */
    private static LettucePoolingClientConfiguration poolConfig(int maxIdle, int minIdle, int maxActive,
                                                        long maxWaitMillis, long timeout) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);

        return LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .commandTimeout(Duration.ofMillis(timeout))
                .shutdownTimeout(Duration.ofMillis(1000L))
                .build();
    }

}

````