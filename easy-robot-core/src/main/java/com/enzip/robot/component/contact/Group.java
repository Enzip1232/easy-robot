package com.enzip.robot.component.contact;

import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.component.api.group.info.GetGroupMemberList;
import com.enzip.robot.component.api.manage.SendGroupMsg;
import com.enzip.robot.component.message.MessageReceipt;
import com.enzip.robot.component.message.Messages;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 群
 *
 * @author Enzip
 * @since 2023/8/22 9:02
 */
@Slf4j
@Getter
@AllArgsConstructor
public class Group {

    /**
     * 群号
     */
    private Long groupId;
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 群备注
     */
    private String groupMemo;
    /**
     * 群创建时间
     */
    private Long groupCreateTime;
    /**
     * 群等级
     */
    private String groupLevel;
    /**
     * 成员数
     */
    private Integer memberCount;
    /**
     * 最大成员数（群容量）
     */
    private Integer maxMemberCount;
    /**
     * 机器人
     */
    private Bot bot;

    private final Map<Long, GroupMember> groupMembers = new ConcurrentHashMap<>();

    /**
     * 刷新群成员信息
     */
    public void flushGroupMembers() {
        ApiResult apiResult = this.bot.getBotClient().invokeApi(new GetGroupMemberList(this.getGroupId()));
        JsonNode jsonNode = OMUtil.valueToTree(apiResult.getData());
        jsonNode.forEach(item -> {
            Long userId = OMUtil.asLong(item, "user_id");
            String nickname = OMUtil.asText(item, "nickname");
            String card = OMUtil.asText(item, "card");
            String sex = OMUtil.asText(item, "sex");
            Integer age = OMUtil.asInt(item, "age");
            String area = OMUtil.asText(item, "area");
            Long joinTime = OMUtil.asLong(item, "join_time");
            Long lastSentTime = OMUtil.asLong(item, "last_sent_time");
            String level = OMUtil.asText(item, "level");
            String role = OMUtil.asText(item, "role");
            Boolean unfriendly = OMUtil.asBoolean(item, "unfriendly");
            String title = OMUtil.asText(item, "title");
            Long titleExpireTime = OMUtil.asLong(item, "title_expire_time");
            Boolean cardChangeable = OMUtil.asBoolean(item, "card_changeable");
            Long shutUpTimestamp = OMUtil.asLong(item, "shut_up_timestamp");
            GroupMember groupMember = new GroupMember(this.getGroupId(), userId, nickname, card, sex, age, area, joinTime, lastSentTime, level, role, unfriendly, title, titleExpireTime, cardChangeable, shutUpTimestamp, this, this.bot);
            groupMembers.put(userId, groupMember);
        });
        log.info(String.format("[%s] flushGroupMembers success, total number of groupMembers: %s", this.groupName, groupMembers.size()));
    }

    public MessageReceipt sendBlocking(Messages messages) {
        ApiResult apiResult = this.bot.getBotClient().invokeApi(new SendGroupMsg(groupId, messages));
        return new MessageReceipt(bot, apiResult);
    }
}
