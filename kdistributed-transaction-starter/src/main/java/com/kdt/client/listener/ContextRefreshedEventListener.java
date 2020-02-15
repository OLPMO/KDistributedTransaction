package com.kdt.client.listener;

import com.kdt.client.interceptor.KTxRestTemplateInterceptor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            RestTemplate restTemplate = event.getApplicationContext().getBean(RestTemplate.class);
            if(restTemplate != null){
                restTemplate.getInterceptors().add(new KTxRestTemplateInterceptor());
            }
        } catch (Exception ignored){
            //ignore
        }

    }
}
