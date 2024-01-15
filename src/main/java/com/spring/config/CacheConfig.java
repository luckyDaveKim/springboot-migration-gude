package com.spring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.spring.cache.ClassMethodNameKeyGenerator;

@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
		factory.setConfigLocation(new ClassPathResource("/ehcache.xml"));
		factory.setShared(true);

		try {
			return factory.getObject();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to create an native ehCache cache manager", e);
		}
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new ClassMethodNameKeyGenerator();
	}
}
