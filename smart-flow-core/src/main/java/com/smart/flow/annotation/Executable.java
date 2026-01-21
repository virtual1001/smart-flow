package com.smart.flow.annotation;

import java.lang.annotation.*;

/**
 * @Author pw
 * @Date 2025/11/25 11:18
 * @Version 1.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Executable {
    String tagName();
}
