package com.enzip.robot.component.annotation;

import com.enzip.robot.core.method.match.MatchType;

import java.lang.annotation.*;

/**
 * @author Enzip
 * @since 2023/10/9 20:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filter {

    boolean ifNullPass() default false;

    MatchType matchType() default MatchType.REGEX_MATCHES;

    Targets targets() default @Targets();

    String value() default "";


    @interface Targets {
        boolean atBot() default false;

        String[] authors() default {};

        String[] bots() default {};

        String[] groups() default {};
    }
}
