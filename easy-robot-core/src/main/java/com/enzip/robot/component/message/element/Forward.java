package com.enzip.robot.component.message.element;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.utils.FormatUtil;
import com.enzip.robot.utils.OMUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Enzip
 * @since 2023/10/8 14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forward implements Message {

    private String id;

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("forward", OMUtil.writeValueAsString(this));
    }
}
