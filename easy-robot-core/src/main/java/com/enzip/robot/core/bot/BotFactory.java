package com.enzip.robot.core.bot;

import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.core.Constants;
import com.enzip.robot.core.bot.network.NetWorkFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 机器人工厂
 *
 * @author Enzip
 * @since 2023/8/1 17:01
 */
@Slf4j
public class BotFactory implements EnvironmentAware {

    private static final Map<Long, Bot> BOTS = new ConcurrentHashMap<>();

    private static ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(Environment environment) {
        if (environment instanceof ConfigurableEnvironment) {
            BotFactory.environment = (ConfigurableEnvironment) environment;
            log.info("botFactory setEnvironment.");
        }
    }

    public static void initBot() {
        List<BotProperties> botProperties = loadBotProperties();
        if (!botProperties.isEmpty()) {
            NetWorkFactory.getInstance().initBotNetwork(botProperties);
        }
    }

    private static List<BotProperties> loadBotProperties(){
        String configKey = Constants.EASY_ROBOT;
        Binder binder = Binder.get(BotFactory.environment);
        List<BotProperties> botProperties = new ArrayList<>();
        try {
            botProperties.addAll(binder.bind(configKey, Bindable.listOf(BotProperties.class)).get());
        } catch (NoSuchElementException e) {
            try {
                botProperties.add(binder.bind(configKey, Bindable.of(BotProperties.class)).get());
            } catch (NoSuchElementException ex) {
                log.error("initialize bot could not get the configuration.");
            }
        }
        return botProperties;
    }

    public static Map<Long, Bot> getBots() {
        return BOTS;
    }
}
