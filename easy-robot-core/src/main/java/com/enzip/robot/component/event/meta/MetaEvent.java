package com.enzip.robot.component.event.meta;

import com.enzip.robot.component.event.BaseEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Enzip
 * @since 2023/9/12 16:18
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class MetaEvent extends BaseEvent {

    /**
     * 元数据类型
     */
    @JsonProperty("meta_event_type")
    private String metaEventType;
}
