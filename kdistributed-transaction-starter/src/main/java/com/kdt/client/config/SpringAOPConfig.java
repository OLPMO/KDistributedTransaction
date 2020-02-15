package com.kdt.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


//用于定义配置类，可替换xml配置文件
@Configuration
//开启AspectJ 自动代理模式,如果不填proxyTargetClass=true，默认为false，
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class SpringAOPConfig {
    static {
        System.out.println("SpringAOPConfig has been injected!");
    }


}
