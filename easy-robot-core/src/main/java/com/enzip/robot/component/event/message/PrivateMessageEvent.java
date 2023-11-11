package com.enzip.robot.component.event.message;

import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.component.contact.Friend;
import com.enzip.robot.component.contact.Group;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

/**
 * @author Enzip
 * @since 2023/9/10 20:20
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class PrivateMessageEvent extends MessageEvent {

    /**
     * 接收者 QQ 号
     */
    @JsonProperty("target_id")
    private Long targetId;
    /**
     * 临时会话来源
     */
    @JsonProperty("temp_source")
    private Integer tempSource;

    @Setter(AccessLevel.PRIVATE)
    private Bot bot;

    @Setter(AccessLevel.PRIVATE)
    private Friend friend;

    public static boolean support(JsonNode jsonNode) {
        return ("message".equals(OMUtil.asText(jsonNode, "post_type"))
                && "private".equals(OMUtil.asText(jsonNode, "message_type")));
    }
}
