package com.eastwood.common.router.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface Flags {
    int value() default 0;
}