package com.enzip.robot.core.bot;

import com.enzip.robot.core.Constants;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 机器人配置
 *
 * @author Enzip
 * @since 2023/8/2 15:58
 */
@Data
@Accessors(chain = true)
public class BotProperties {

    /**
     * 连接类型
     * 对应NetWorkType类
     * ws:websocket正向连接
     * wsr:websocket反向连接
     */
    private String type;
    /**
     * 连接地址
     */
    private String url;
}
