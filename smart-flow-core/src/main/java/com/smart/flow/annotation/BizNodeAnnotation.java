package com.smart.flow.annotation;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizNodeAnnotation {
    String[] nodeId() default {};
}
