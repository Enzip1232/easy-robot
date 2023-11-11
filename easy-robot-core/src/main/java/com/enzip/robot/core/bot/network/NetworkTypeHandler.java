package com.enzip.robot.core.bot.network;

import com.enzip.robot.core.bot.BotProperties;

/**
 * 网络类型策略接口
 *
 * @author Enzip
 * @since 2023/8/3 9:36
 */
public interface NetworkTypeHandler {

    /**
     * 初始化
     */
    void init(BotProperties botProperties);
}
