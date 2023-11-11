package com.enzip.robot.component.message;

import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.component.api.manage.DeleteMsg;
import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Enzip
 * @since 2023/11/6 14:53
 */
@Getter
public class MessageReceipt {

    /**
     * 机器人
     */
    private final Bot bot;

    /**
     * 状态, 表示 API 是否调用成功, 如果成功, 则是 OK
     */
    private final String status;

    /**
     * 消息ID
     */
    private final Long messageId;

    public MessageReceipt(Bot bot, ApiResult apiResult) {
        JsonNode jsonNode = OMUtil.valueToTree(apiResult.getData());
        this.bot = bot;
        this.status = apiResult.getStatus();
        this.messageId = OMUtil.asLong(jsonNode, "message_id");
    }

    public boolean isSuccess() {
        return "OK".equals(this.status);
    }

    public void deleteBlocking() {
        bot.getBotClient().invokeApi(new DeleteMsg(this.messageId));
    }

    public void deleteBlocking(Long time) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable task = this::deleteBlocking;
        executorService.schedule(task, time, TimeUnit.SECONDS);
        executorService.shutdown();
    }
}
