package com.kdt.client.aspect;

import com.kdt.client.annotation.KDistributedTransaction;
import com.kdt.client.proxy.ConnectionProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class DataSourceAspect {
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint pjp) throws Throwable {
        //before
        Connection connection = (Connection) pjp.proceed();

        return new ConnectionProxy(connection);

    }
}
