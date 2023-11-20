package com.enzip.robot.component.event.message;

import com.enzip.robot.component.event.BaseEvent;
import com.enzip.robot.component.message.Messages;
import com.enzip.robot.component.message.MessagesContent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Enzip
 * @since 2023/9/10 21:18
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class MessageEvent extends BaseEvent {

    /**
     * 消息类型
     */
    @JsonProperty("message_type")
    private String messageType;
    /**
     * 表示消息的子类型
     */
    @JsonProperty("sub_type")
    private String subType;
    /**
     * 消息 ID
     */
    @JsonProperty("message_id")
    private Integer messageId;
    /**
     * 发送者 QQ 号
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     * 消息链
     */
    @JsonProperty("message")
    private Object message;
    /**
     * CQ 码格式的消息
     */
    @JsonProperty("raw_message")
    private String rawMessage;
    /**
     * 字体
     */
    @JsonProperty("font")
    private Integer font;
    /**
     * 发送者信息
     */
    @JsonProperty("sender")
    private Object sender;

    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    private MessagesContent messagesContent;
}
