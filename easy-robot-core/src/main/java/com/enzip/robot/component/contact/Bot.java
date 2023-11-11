package com.enzip.robot.component.contact;

import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.component.api.account.GetLoginInfo;
import com.enzip.robot.component.api.friend.info.GetFriendList;
import com.enzip.robot.component.api.group.info.GetGroupList;
import com.enzip.robot.core.bot.network.BotClient;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 机器人
 *
 * @author Enzip
 * @since 2023/8/2 15:11
 */
@Slf4j
@Getter
public class Bot {

    /**
     * 机器人客户端
     */
    private final BotClient botClient;
    /**
     * Q号
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final Map<Long, Friend> friends = new ConcurrentHashMap<>();
    private final Map<Long, Group> groups = new ConcurrentHashMap<>();

    public Bot(BotClient botClient) {
        this.botClient = botClient;
    }

    /**
     * 刷新机器人信息
     */
    public void flushBotInfo() {
        ApiResult apiResult = botClient.invokeApi(new GetLoginInfo());
        JsonNode jsonNode = OMUtil.valueToTree(apiResult.getData());
        this.userId = OMUtil.asLong(jsonNode, "user_id");
        this.nickname = OMUtil.asText(jsonNode, "nickname");
        log.info(String.format("[%s] flushBotInfo success", this.nickname));
    }

    /**
     * 刷新好友列表
     */
    public void flushFriends() {
        ApiResult apiResult = botClient.invokeApi(new GetFriendList());
        JsonNode jsonNode = OMUtil.valueToTree(apiResult.getData());
        jsonNode.forEach(item -> {
            Long userId = OMUtil.asLong(item, "user_id");
            String nickname = OMUtil.asText(item, "nickname");
            String remark = OMUtil.asText(item, "remark");
            Friend friend = new Friend(userId, nickname, remark, this);
            friends.put(userId, friend);
        });
        log.info(String.format("[%s] flushFriends success, total number of friends: %s", this.nickname, friends.size()));
    }

    /**
     * 刷新机器人群信息
     */
    public void flushGroups() {
        ApiResult apiResult = botClient.invokeApi(new GetGroupList());
        JsonNode jsonNode = OMUtil.valueToTree(apiResult.getData());
        jsonNode.forEach(item -> {
            Long groupId = OMUtil.asLong(item, "group_id");
            String groupName = OMUtil.asText(item, "group_name");
            String groupMemo = OMUtil.asText(item, "group_memo");
            Long groupCreateTime = OMUtil.asLong(item, "group_create_time");
            String groupLevel = OMUtil.asText(item, "group_level");
            Integer memberCount = OMUtil.asInt(item, "member_count");
            Integer maxMemberCount = OMUtil.asInt(item, "max_member_count");
            Group group = new Group(groupId, groupName, groupMemo, groupCreateTime, groupLevel, memberCount, maxMemberCount, this);
            groups.put(groupId, group);
        });
        log.info(String.format("[%s] flushGroups success, total number of groups: %s", this.nickname, groups.size()));
    }
}
