package com.enzip.robot.component.message.element;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.component.message.Messages;
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
public class Node implements Message {

    private Integer id;
    private String name;
    private Long uin;
    private Messages message;
    private Long seq;

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("node", OMUtil.writeValueAsString(this));
    }
}
