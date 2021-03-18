package com.rest.api.config;

import com.rest.api.common.CasheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisConfig {

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // null value 캐시안함
                .entryTtl(Duration.ofSeconds(CasheKey.DEFAULT_EXPIRE_SEC)) // 캐시의 기본 유효시간 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())); // redis 캐시 데이터 저장방식을 StringSeriallizer로 지정

// 캐시키별 default 유효시간 설정
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CasheKey.USER, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CasheKey.USER_EXPIRE_SEC)));
        cacheConfigurations.put(CasheKey.BOARD, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CasheKey.BOARD_EXPIRE_SEC)));
        cacheConfigurations.put(CasheKey.POST, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CasheKey.POST_EXPIRE_SEC)));
        cacheConfigurations.put(CasheKey.POSTS, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CasheKey.POST_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}