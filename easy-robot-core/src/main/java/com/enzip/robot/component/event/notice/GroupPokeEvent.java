package com.enzip.robot.component.event.notice;

import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.contact.Group;
import com.enzip.robot.component.contact.GroupMember;
import com.enzip.robot.component.event.BaseEvent;
import com.enzip.robot.component.event.component.Sender;
import com.enzip.robot.component.event.component.support.GroupSupport;
import com.enzip.robot.component.event.component.support.SenderSupport;
import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

/**
 * @author Enzip
 * @since 2023/11/8 19:35
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class GroupPokeEvent extends BaseEvent implements GroupSupport , SenderSupport {

    /**
     * 提示类型
     */
    @JsonProperty("sub_type")
    private String subType;

    /**
     * 群号
     */
    @JsonProperty("group_id")
    private Long groupId;

    /**
     * 发送者 QQ 号
     */
    @JsonProperty("user_id")
    private Long userId;

    /**
     * 发送者 QQ 号
     */
    @JsonProperty("sender_id")
    private Long senderId;

    /**
     * 被戳者 QQ 号
     */
    @JsonProperty("target_id")
    private Long targetId;

    public static boolean support(JsonNode jsonNode) {
        return ("notice".equals(OMUtil.asText(jsonNode, "post_type"))
                && "poke".equals(OMUtil.asText(jsonNode, "sub_type"))
                && OMUtil.asText(jsonNode, "group_id") != null);
    }

    @Override
    public Group getGroup() {
        return BotFactory.getBots().get(getSelfId()).getGroups().get(getGroupId());
    }

    @Override
    public Sender getSender() {
        GroupMember groupMember = BotFactory.getBots().get(getSelfId()).getGroups().get(getGroupId()).getGroupMembers().get(getSenderId());
        return new Sender(groupMember);
    }
}
