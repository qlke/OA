package com.evistek.oa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/9
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {
    String description() default "";
}
