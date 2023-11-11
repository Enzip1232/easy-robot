package com.enzip.robot.component.api.manage;

import com.enzip.robot.component.api.BaseApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Enzip
 * @since 2023/11/6 15:10
 */
public class DeleteMsg extends BaseApi {

    private final DeleteMsg.Param param;

    public DeleteMsg(Long messageId) {
        this.param = new DeleteMsg.Param();
        this.param.messageId = messageId;
    }

    @Override
    public String getAction() {
        return "delete_msg";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Data
    private static class Param {

        /**
         * 消息ID
         */
        @JsonProperty("message_id")
        private Long messageId;
    }
}
