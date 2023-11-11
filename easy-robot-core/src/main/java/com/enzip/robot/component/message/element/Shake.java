package com.enzip.robot.component.message.element;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.utils.FormatUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Enzip
 * @since 2023/10/16 21:24
 */
@Data
@NoArgsConstructor
public class Shake implements Message {

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("shake", null);
    }
}
