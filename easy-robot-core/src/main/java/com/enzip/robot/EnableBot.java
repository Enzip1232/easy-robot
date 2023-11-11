package com.enzip.robot;

import com.enzip.robot.core.BotApplicationRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Enzip
 * @since 2023/8/1 14:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({BotApplicationRegistrar.class})
public @interface EnableBot {
}
