package com.spring.config;

import static org.apache.hc.core5.util.Timeout.*;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom()
			.setConnectionRequestTimeout(ofMilliseconds(3000))
			.build();
	}

	@Bean
	public ConnectionConfig connectionConfig() {
		return ConnectionConfig.custom()
			.setConnectTimeout(ofMilliseconds(2000))
			.setSocketTimeout(ofMilliseconds(1000))
			.build();
	}

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(ConnectionConfig connectionConfig) {
		return PoolingHttpClientConnectionManagerBuilder.create()
			.setMaxConnTotal(600)
			.setMaxConnPerRoute(400)
			.setDefaultConnectionConfig(connectionConfig)
			.setDefaultSocketConfig(SocketConfig.custom()
				.setSoTimeout(ofMilliseconds(2000))
				.setSoKeepAlive(false)
				.setTcpNoDelay(true)
				.setSoReuseAddress(true)
				.build())
			.build();
	}

	@Bean
	public HttpClient httpClient(RequestConfig requestConfig,
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
		return HttpClients.custom()
			.setDefaultRequestConfig(requestConfig)
			.setConnectionManager(poolingHttpClientConnectionManager)
			.build();
	}

	@Bean
	public RestTemplate restTemplate(HttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		return new RestTemplate(requestFactory);
	}
}
