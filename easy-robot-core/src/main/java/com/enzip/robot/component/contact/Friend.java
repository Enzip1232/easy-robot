package com.enzip.robot.component.contact;

import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.component.api.manage.SendGroupMsg;
import com.enzip.robot.component.api.manage.SendPrivateMsg;
import com.enzip.robot.component.message.MessageReceipt;
import com.enzip.robot.component.message.Messages;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 好友
 *
 * @author Enzip
 * @since 2023/8/21 17:30
 */
@Slf4j
@Getter
@AllArgsConstructor
public class Friend {

    /**
     * Q号
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 备注名
     */
    private String remark;
    /**
     * 机器人
     */
    private Bot bot;

    public MessageReceipt sendBlocking(Messages messages) {
        ApiResult apiResult = this.bot.getBotClient().invokeApi(new SendPrivateMsg(userId, messages));
        return new MessageReceipt(bot, apiResult);
    }
}
