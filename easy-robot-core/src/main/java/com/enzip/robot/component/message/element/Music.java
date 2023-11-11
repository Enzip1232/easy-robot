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
public class Music implements Message {

    private String id;
    private String type;
    private String url;
    private String audio;
    private String title;
    private String content;
    private String image;

    public Music(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public Music(String type, String url, String audio, String title) {
        this.type = type;
        this.url = url;
        this.audio = audio;
        this.title = title;
    }

    public Music(String type, String url, String audio, String title, String content, String image) {
        this.type = type;
        this.url = url;
        this.audio = audio;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("music", OMUtil.writeValueAsString(this));
    }
}
