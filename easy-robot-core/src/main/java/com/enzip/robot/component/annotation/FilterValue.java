package com.enzip.robot.component.annotation;

import java.lang.annotation.*;

/**
 * @author Enzip
 * @since 2023/10/14 21:48
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilterValue {
    String value() default "";

    boolean required() default true;
}
