package com.enzip.robot.core.handler.notice;

import cn.hutool.core.util.ReflectUtil;
import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.contact.Group;
import com.enzip.robot.component.event.notice.GroupPokeEvent;
import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.core.handler.EventHandler;
import com.enzip.robot.core.method.MethodFactory;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Enzip
 * @since 2023/11/9 16:06
 */
@Slf4j
public class GroupPokeEventHandler implements EventHandler {

    @Override
    public void handle(JsonNode jsonNode) {
        if (!GroupPokeEvent.support(jsonNode)) {
            return;
        }
        GroupPokeEvent groupPokeEvent = OMUtil.convertValue(jsonNode, GroupPokeEvent.class);

        Bot bot = BotFactory.getBots().get(groupPokeEvent.getSelfId());
        Group group = bot.getGroups().get(groupPokeEvent.getGroupId());

        ReflectUtil.setFieldValue(groupPokeEvent, "bot", bot);
        ReflectUtil.setFieldValue(groupPokeEvent, "group", group);

        MethodFactory.methodEventHandle(groupPokeEvent);
    }
}
