package com.neo.lesson.config;

import com.neo.lesson.manage.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册JWT Token拦截器
 *
 * @author neo
 * @since 2025-02-28
 */
@Configuration
public class JWTConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/v1/lesson/**", "/v1/student/**")
                .excludePathPatterns("/v1/login/**");
    }
}
