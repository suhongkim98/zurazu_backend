package com.zurazu.zurazu_backend.config;

import com.zurazu.zurazu_backend.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePatterns = Arrays.asList(
                "/admin/login","/admin/register","/admin/refreshToken","/member/login","/member/register","/member/refreshToken");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/upload") // s3업로드 테스트용 경로, 나중에 삭제하장
                .excludePathPatterns("/deleteTest") // s3 테스트용 경로, 나중에 삭제하장
                .excludePathPatterns(excludePatterns); // 경로지정하기
    }

}