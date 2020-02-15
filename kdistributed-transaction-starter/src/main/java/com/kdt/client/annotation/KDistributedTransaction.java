package com.kdt.client.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KDistributedTransaction {
    boolean isFinalTx() default false;
    boolean isStartTx() default false;
}
