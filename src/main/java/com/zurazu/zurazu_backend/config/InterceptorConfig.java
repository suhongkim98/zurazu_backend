package com.zurazu.zurazu_backend.config;

import com.zurazu.zurazu_backend.interceptor.AdminAuthInterceptor;
import com.zurazu.zurazu_backend.interceptor.MemberAuthInterceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final AdminAuthInterceptor adminAuthInerceptor;
    private final MemberAuthInterceptor memberAuthInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthInerceptor) //admin 권한이 필요한 요청은 모두 /admin을 엔드포인트로
        		.addPathPatterns("/admin/**")
        		.excludePathPatterns("/admin/register")
        		.excludePathPatterns("/admin/login")
        		.excludePathPatterns("/admin/refreshToken");
        
        registry.addInterceptor(memberAuthInterceptor) // 멤버 권한이 필요한 요청은 모두 /member을 엔드포인트로
        		.addPathPatterns("/member/**")
        		.excludePathPatterns("/member/register")
        		.excludePathPatterns("/member/login")
        		.excludePathPatterns("/member/refreshToken");
    }

}