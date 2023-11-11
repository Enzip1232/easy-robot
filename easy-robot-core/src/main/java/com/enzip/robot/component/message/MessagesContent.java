package com.enzip.robot.component.message;

import com.enzip.robot.component.event.message.MessageEvent;
import com.enzip.robot.utils.MessagesUtil;
import com.enzip.robot.utils.OMUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Enzip
 * @since 2023/10/11 22:07
 */
@Slf4j
@Getter
public class MessagesContent {

    private final Integer messageId;

    private final Messages messages;

    private final String plainText;

    public MessagesContent() {
        this.messageId = null;
        this.messages = new MessagesBuilder().build();
        this.plainText = null;
    }

    public MessagesContent(Integer messageId, Messages messages) {
        this.messageId = messageId;
        this.messages = messages;
        this.plainText = MessagesUtil.getPlainText(messages);
    }

    public MessagesContent(MessageEvent messageEvent){
        this.messageId = messageEvent.getMessageId();
        this.messages = MessagesUtil.jsonToMessages(OMUtil.valueToTree(messageEvent.getMessage()));
        this.plainText = MessagesUtil.getPlainText(messages);
    }
}
