package com.enzip.robot.component.event.meta;

import com.enzip.robot.component.event.BaseEvent;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Enzip
 * @since 2023/9/12 16:13
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class HeartbeatEvent extends MetaEvent {

    /**
     * 应用程序状态
     */
    @JsonProperty("status")
    private Object status;
    /**
     * 距离上一次心跳包的时间(单位是毫秒)
     */
    @JsonProperty("interval")
    private Long interval;

    public static boolean support(JsonNode jsonNode) {
        return ("meta_event".equals(OMUtil.asText(jsonNode, "post_type"))
                && "heartbeat".equals(OMUtil.asText(jsonNode, "meta_event_type")));
    }
}
