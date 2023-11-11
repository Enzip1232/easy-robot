package com.enzip.robot.core.handler.message;

import cn.hutool.core.util.ReflectUtil;
import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.contact.Friend;
import com.enzip.robot.component.event.message.PrivateMessageEvent;
import com.enzip.robot.component.message.MessagesContent;
import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.core.handler.EventHandler;
import com.enzip.robot.core.method.MethodFactory;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Enzip
 * @since 2023/9/17 21:12
 */
public class PrivateMessageEventHandler implements EventHandler {

    @Override
    public void handle(JsonNode jsonNode) {
        if (!PrivateMessageEvent.support(jsonNode)) {
            return;
        }
        PrivateMessageEvent privateMessageEvent = OMUtil.convertValue(jsonNode, PrivateMessageEvent.class);

        Bot bot = BotFactory.getBots().get(privateMessageEvent.getSelfId());
        Friend friend = bot.getFriends().get(privateMessageEvent.getUserId());
        MessagesContent messagesContent = new MessagesContent(privateMessageEvent);

        ReflectUtil.setFieldValue(privateMessageEvent, "bot", bot);
        ReflectUtil.setFieldValue(privateMessageEvent, "friend", friend);
        ReflectUtil.setFieldValue(privateMessageEvent, "messagesContent", messagesContent);

        MethodFactory.methodEventHandle(privateMessageEvent);
    }
}
