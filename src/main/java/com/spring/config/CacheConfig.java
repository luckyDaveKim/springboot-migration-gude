package com.spring.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.spring.cache.ClassMethodNameKeyGenerator;

@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
	@Bean
	@ConfigurationProperties(prefix = "spring.cache.caffeine")
	public Map<String, String> cacheTtlByNameMap() {
		return new HashMap<>();
	}

	@Bean
	public List<CaffeineCache> caffeineCacheList() {
		return cacheTtlByNameMap()
			.entrySet()
			.stream()
			.map(cacheEntry -> new CaffeineCache(cacheEntry.getKey(), Caffeine.from(cacheEntry.getValue()).build()))
			.toList();
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caffeineCacheList());
		return cacheManager;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new ClassMethodNameKeyGenerator();
	}
}
