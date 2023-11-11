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
public class Reply implements Message {

    private Integer id;
    private String text;
    private Long qq;
    private Long time;
    private Long seq;

    public Reply(Integer id) {
        this.id = id;
    }

    public Reply(String text, Long qq, Long time, Long seq) {
        this.text = text;
        this.qq = qq;
        this.time = time;
        this.seq = seq;
    }

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("reply", OMUtil.writeValueAsString(this));
    }
}
