package com.enzip.robot.component.message.element;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.utils.FormatUtil;
import com.enzip.robot.utils.OMUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Enzip
 * @since 2023/10/10 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class At implements Message {

    private Long qq;

    private String name;

    public At(Long qq) {
        this.qq = qq;
    }

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("at", OMUtil.writeValueAsString(this));
    }
}
