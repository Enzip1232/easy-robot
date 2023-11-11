package com.enzip.robot.component.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Enzip
 * @since 2023/9/10 20:10
 */
@Data
@ToString
public abstract class BaseEvent {

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
}
