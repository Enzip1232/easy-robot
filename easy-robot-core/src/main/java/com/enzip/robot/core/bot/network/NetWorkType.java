package com.enzip.robot.core.bot.network;

/**
 * 网络类型
 *
 * @author Enzip
 * @since 2023/8/3 9:28
 */
public interface NetWorkType {
    /**
     * 正向websocket
     */
    String WSF = "ws";
    /**
     * 反向websocket
     */
    String WSR = "ws-reverse";
    /**
     * http
     */
    String HTTP = "http";
}
