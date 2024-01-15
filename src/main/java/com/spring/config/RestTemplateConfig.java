package com.spring.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom()
			.setConnectionRequestTimeout(2000)
			.setConnectTimeout(2000)
			.setSocketTimeout(1000)
			.build();
	}

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
		result.setMaxTotal(500);
		result.setDefaultMaxPerRoute(100);
		return result;
	}

	@Bean
	public HttpClient httpClient(RequestConfig requestConfig,
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
		SocketConfig socketConfig = SocketConfig.custom()
			.setSoTimeout(1000)
			.setSoKeepAlive(true)
			.setTcpNoDelay(true)
			.setSoReuseAddress(true)
			.build();

		return HttpClients.custom()
			.setConnectionManager(poolingHttpClientConnectionManager)
			.setDefaultRequestConfig(requestConfig)
			.setDefaultSocketConfig(socketConfig)
			.build();
	}

	@Bean
	public RestTemplate restTemplate(HttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		return new RestTemplate(requestFactory);
	}
}
