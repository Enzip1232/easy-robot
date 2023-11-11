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
public class Image implements Message {

    private String file;
    private String type;
    private String subType;
    private String url;
    private Integer cache;
    private Integer id;
    private Integer c;

    public Image(String file) {
        this.file = file;
    }

    public Image(String file, String type) {
        this.file = file;
        this.type = type;
    }

    public Image(String file, String type, Integer id) {
        this.file = file;
        this.type = type;
        this.id = id;
    }


    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("image", OMUtil.writeValueAsString(this));
    }
}
