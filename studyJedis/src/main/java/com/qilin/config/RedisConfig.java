package com.qilin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());

        return template;
    }

    /*@Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        //spring cache注解序列化配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer((RedisSerializer<String>) redisTemplate.getKeySerializer()))        //key序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))    //value序列化方式
                .entryTtl(Duration.ofSeconds(60));  //默认缓存过期时间

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(Objects.requireNonNull(redisTemplate.getConnectionFactory())).cacheDefaults(redisCacheConfiguration).build();
        return redisCacheManager;
    }*/


}
