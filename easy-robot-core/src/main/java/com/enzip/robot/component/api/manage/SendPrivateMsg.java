package com.enzip.robot.component.api.manage;

import com.enzip.robot.component.api.BaseApi;
import com.enzip.robot.component.message.Messages;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Enzip
 * @since 2023/10/8 16:10
 */
public class SendPrivateMsg extends BaseApi {

    private final SendPrivateMsg.Param param;

    public SendPrivateMsg(Long userId, Messages messages) {
        this.param = new Param();
        this.param.userId = userId;
        this.param.message = OMUtil.readTree(messages.toJsonString());
    }

    @Override
    public String getAction() {
        return "send_private_msg";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Data
    private static class Param {

        /**
         * 对方 QQ 号
         */
        @JsonProperty("user_id")
        private Long userId;

        /**
         * 主动发起临时会话时的来源群号(可选, 机器人本身必须是管理员/群主)
         */
        @JsonProperty("group_id")
        private Long groupId;

        /**
         * 要发送的内容
         */
        private Object message;

        /**
         * 消息内容是否作为纯文本发送 ( 即不解析 CQ 码 ) , 只在 message 字段是字符串时有效
         */
        @JsonProperty("auto_escape")
        private Boolean autoEscape;
    }
}
