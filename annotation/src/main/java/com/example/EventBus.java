package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xigua on 2017/9/28.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface EventBus {
}
