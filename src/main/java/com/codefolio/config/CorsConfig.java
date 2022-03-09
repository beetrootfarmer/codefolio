package com.codefolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration(); 
		config.setAllowCredentials(true); //서버 응답시 json을 반환하겠다
		config.addAllowedOrigin("*"); //모든 ip에 응답을 허용하겠다
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다
		config.addAllowedMethod("*"); // 모든 post,get,put,delete, path 요청을 허용 
		source.registerCorsConfiguration("/controller/**", config);
		return new CorsFilter(source);
	}

}
