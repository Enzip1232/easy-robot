package com.enzip.robot.component.event.message;

import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.contact.Group;
import com.enzip.robot.component.event.component.support.GroupSupport;
import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

/**
 * @author Enzip
 * @since 2023/9/10 22:24
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class GroupMessageEvent extends MessageEvent implements GroupSupport {

    /**
     * 群号
     */
    @JsonProperty("group_id")
    private Long groupId;
    /**
     * 匿名信息, 如果不是匿名消息则为 null
     */
    @JsonProperty("anonymous")
    private Object anonymous;

    public static boolean support(JsonNode jsonNode) {
        return ("message".equals(OMUtil.asText(jsonNode, "post_type"))
                && "group".equals(OMUtil.asText(jsonNode, "message_type")));
    }

    @Override
    public Group getGroup() {
        return BotFactory.getBots().get(getSelfId()).getGroups().get(getGroupId());
    }
}
