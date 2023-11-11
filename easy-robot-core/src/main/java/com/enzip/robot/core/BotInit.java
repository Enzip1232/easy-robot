package com.enzip.robot.core;

import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.core.method.MethodFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * 机器人初始化
 *
 * @author Enzip
 * @since 2023/8/2 15:28
 */
@Slf4j
public class BotInit implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MethodFactory.initMethod();
        BotFactory.initBot();
        log.info("botInit running.");
    }
}
