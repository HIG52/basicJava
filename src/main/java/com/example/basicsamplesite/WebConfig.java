package com.example.basicsamplesite;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 설정 - CORS 및 헤더 관련 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 리액트 개발 서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders(
                    "Content-Type", 
                    "Authorization", 
                    "X-Requested-With", 
                    "Accept", 
                    "Cache-Control"
                )
                .exposedHeaders("Authorization") // 응답 헤더 노출
                .allowCredentials(true)
                .maxAge(3600); // preflight 요청 캐시 시간 (1시간)
    }

}
