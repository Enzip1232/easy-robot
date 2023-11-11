package com.enzip.robot.component.event.meta;

import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Enzip
 * @since 2023/9/12 16:20
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class LifeCycleEvent extends MetaEvent {

    /**
     * 子类型
     */
    @JsonProperty("sub_type")
    private String subType;

    public static boolean support(JsonNode jsonNode) {
        return ("meta_event".equals(OMUtil.asText(jsonNode, "post_type"))
                && "lifecycle".equals(OMUtil.asText(jsonNode, "meta_event_type")));
    }
}
