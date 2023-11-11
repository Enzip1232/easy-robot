package com.enzip.robot.core;


import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.core.method.MethodFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 机器人应用注册器
 *
 * @author Enzip
 * @since 2023/8/1 17:11
 */
public class BotApplicationRegistrar implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                MethodFactory.class.getName(),
                BotFactory.class.getName(),
                BotInit.class.getName()
        };
    }
}
