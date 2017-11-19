package com.cfw.plugins.http.annotation;

import com.cfw.plugins.netty.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestPath {

    String value() default "";

    HttpMethod method() default HttpMethod.GET;
}
