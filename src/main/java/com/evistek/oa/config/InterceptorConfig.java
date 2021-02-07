package com.evistek.oa.config;

import com.evistek.oa.interceptor.ApiInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/2
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private ApiInterceptor apiInterceptor;

    public InterceptorConfig(ApiInterceptor apiInterceptor) {
        this.apiInterceptor = apiInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
                .excludePathPatterns("/api/v1/login/**", "/resource/annex/**", "/resource/excel/**");
    }
}
