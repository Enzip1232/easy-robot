package com.enzip.robot.component.message.element;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.utils.FormatUtil;
import com.enzip.robot.utils.OMUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Enzip
 * @since 2023/10/16 21:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Message {

    private String file;

    private String cover;

    private Integer c;

    private Integer cache;

    public Video(String file) {
        this.file = file;
    }

    public Video(String file, Integer cache) {
        this.file = file;
        this.cache = cache;
    }

    public Video(String file, String cover) {
        this.file = file;
        this.cover = cover;
    }

    public Video(String file, String cover, Integer cache) {
        this.file = file;
        this.cover = cover;
        this.cache = cache;
    }

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("video", OMUtil.writeValueAsString(this));
    }
}
