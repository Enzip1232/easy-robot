package com.enzip.robot.component.annotation;

import java.lang.annotation.*;

/**
 * @author Enzip
 * @since 2023/9/9 22:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Listener {

    int priority() default 500;

}
