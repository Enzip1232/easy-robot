package com.enzip.robot.component.message.element;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.utils.FormatUtil;
import com.enzip.robot.utils.OMUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Enzip
 * @since 2023/10/16 21:24
 */
@Data
@NoArgsConstructor
public class Record implements Message {

    private String file;

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("record", OMUtil.writeValueAsString(this));
    }
}
