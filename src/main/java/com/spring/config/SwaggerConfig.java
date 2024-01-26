package com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI().info(info());
	}

	private Info info() {
		return new Info()
			.title("Spring Boot Migration")
			.description("Spring Boot v2 to v3 Migration Sample")
			.version("1.0");
	}
}
