package com.kdt.client.config;

import com.kdt.client.interceptor.KTxRestTemplateInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {"com.kdt.client.aspect","com.kdt.client.config",
        "com.kdt.client.interceptor","com.kdt.client.customizer","com.kdt.client.listener"})
public class KDistTxAutoConfig {
    static {
        System.out.println("KDistTxAutoConfig has been injected!");
    }

//    @ConditionalOnBean(RestTemplate.class)
//    @Bean
//    public KTxRestTemplateInterceptor ktxRestTemplateInterceptor(){
//        return new KTxRestTemplateInterceptor();
//    }
}
