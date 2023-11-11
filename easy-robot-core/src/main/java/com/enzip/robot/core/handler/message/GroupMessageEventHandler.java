package com.enzip.robot.core.handler.message;

import cn.hutool.core.util.ReflectUtil;
import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.contact.Group;
import com.enzip.robot.component.event.message.GroupMessageEvent;
import com.enzip.robot.component.message.MessagesContent;
import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.core.handler.EventHandler;
import com.enzip.robot.core.method.MethodFactory;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Enzip
 * @since 2023/9/17 20:55
 */
@Slf4j
public class GroupMessageEventHandler implements EventHandler {

    @Override
    public void handle(JsonNode jsonNode) {
        if (!GroupMessageEvent.support(jsonNode)) {
            return;
        }
        GroupMessageEvent groupMessageEvent = OMUtil.convertValue(jsonNode, GroupMessageEvent.class);

        Bot bot = BotFactory.getBots().get(groupMessageEvent.getSelfId());
        Group group = bot.getGroups().get(groupMessageEvent.getGroupId());
        MessagesContent messagesContent = new MessagesContent(groupMessageEvent);

        ReflectUtil.setFieldValue(groupMessageEvent, "bot", bot);
        ReflectUtil.setFieldValue(groupMessageEvent, "group", group);
        ReflectUtil.setFieldValue(groupMessageEvent, "messagesContent", messagesContent);

        MethodFactory.methodEventHandle(groupMessageEvent);
    }
}
