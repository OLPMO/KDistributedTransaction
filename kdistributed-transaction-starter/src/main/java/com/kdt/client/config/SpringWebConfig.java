package com.kdt.client.config;

import com.kdt.client.interceptor.KTxHttpRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    static {
        System.out.println("SpringWebConfig has been injected!");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns - 用于添加拦截规则
        // excludePathPatterns - 用户排除拦截
        registry.addInterceptor(new KTxHttpRequestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html", "/");
    }
}
