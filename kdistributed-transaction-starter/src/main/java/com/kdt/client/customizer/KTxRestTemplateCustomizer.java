package com.kdt.client.customizer;

import com.kdt.client.interceptor.KTxRestTemplateInterceptor;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KTxRestTemplateCustomizer implements RestTemplateCustomizer {
    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new KTxRestTemplateInterceptor());
    }
}
