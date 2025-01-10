package com.train.framework.dict.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存配置
 */
@Configuration
public class CacheConfig {

    @Bean("dictLocalCache")
    public Cache<String, Object> dictLocalCache() {
        return Caffeine.newBuilder()
                // 指定时间内没有被创建/覆盖，则指定时间过后，再次访问时，会去刷新该缓存
                // 在新值没有到来之前，始终返回旧值
                .expireAfterWrite(300, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(100000)
                .build();
    }
}
