```
package com.kaishustory.lettuce.conf;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
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
public class LettuceRedisConf extends CachingConfigurerSupport {

    @Value("${redis.cangwu.host}") String hostName;
    @Value("${redis.cangwu.port}") int port;
    @Value("${redis.cangwu.password}") String password;
    @Value("${redis.cangwu.pool.max-idle}") int maxIdle;
    @Value("${redis.cangwu.pool.min-idle}") int minIdle;
    @Value("${redis.cangwu.pool.max-active}") int maxActive;
    @Value("${redis.cangwu.pool.max-wait}") long maxWaitMillis;
    @Value("${redis.cangwu.pool.timeout}") long timeout;
    @Value("${redis.cangwu.db.index}") int db;

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        //以锁写入的方式创建RedisCacheWriter对象
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory());
        //创建默认缓存配置对象
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new RedisCacheManager(writer, config);
    }

    @Bean(name = "redisCangWuTemplate")
    public StringRedisTemplate redisCangwuTemplate() {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    /**
     * redis连接工厂
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory connectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(db);

        return new LettuceConnectionFactory(redisStandaloneConfiguration, poolConfig());
    }

    /**
     * 线程池
     * @return LettucePoolingClientConfiguration
     */
    @Bean
    public LettucePoolingClientConfiguration poolConfig() {
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


```