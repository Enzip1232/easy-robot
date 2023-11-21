package com.enzip.robot.component.event;

import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.event.component.support.BotSupport;
import com.enzip.robot.core.bot.BotFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Enzip
 * @since 2023/9/10 20:10
 */
@Data
@ToString
public abstract class BaseEvent implements BotSupport {

    /**
     * 事件发生的unix时间戳
     */
    @JsonProperty("time")
    private Long time;
    /**
     * 收到事件的机器人的 QQ 号
     */
    @JsonProperty("self_id")
    private Long selfId;
    /**
     * 表示该上报的类型, 消息, 消息发送, 请求, 通知, 或元事件
     */
    @JsonProperty("post_type")
    private String postType;

    @Override
    public Bot getBot() {
        return BotFactory.getBots().get(getSelfId());
    }
}
