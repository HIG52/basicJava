package com.example.basicsamplesite;

import com.example.basicsamplesite.infrastructure.config.interceptor.MenuAccessInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Web 설정 - CORS, 인터셉터 및 헤더 관련 설정
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    
    private final MenuAccessInterceptor menuAccessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(menuAccessInterceptor)
                .addPathPatterns("/**") // 모든 경로에 적용
                .excludePathPatterns(
                        "/admin/**",                 // 관리자 경로 제외
                        "/api/auth/**",              // 인증 관련 경로 제외
                        "/api/menu-permissions/my-menus",  // 내 메뉴 조회 제외
                        "/api/menu-permissions/check-access", // 권한 체크 제외
                        "/error",                    // 에러 페이지 제외
                        "/favicon.ico",              // 파비콘 제외
                        "/static/**",                // 정적 리소스 제외
                        "/",                         // 루트 경로 제외
                        "/login",                    // 로그인 페이지 제외
                        "/register"                  // 회원가입 페이지 제외
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.100.125:3000") // 리액트 개발 서버 주소 //http://192.168.100.125/
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 리액트 빌드된 정적 파일들 서빙
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/static/");
                
        // 리액트 SPA 라우팅 지원 - 모든 경로를 index.html로 포워딩
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        
                        // API 경로는 제외
                        if (resourcePath.startsWith("api/")) {
                            return null;
                        }
                        
                        return requestedResource.exists() && requestedResource.isReadable() 
                            ? requestedResource 
                            : new ClassPathResource("/static/index.html");
                    }
                });
    }

}
