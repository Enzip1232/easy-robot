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
    private String subtype;
    private String url;
    private String voice;
    private String title;
    private String content;
    private String image;

    public Music(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public Music(String type, String subtype, String url, String voice, String title) {
        this.type = type;
        this.subtype = subtype;
        this.url = url;
        this.voice = voice;
        this.title = title;
    }

    public Music(String type,String subtype, String url, String voice, String title, String content, String image) {
        this.type = type;
        this.subtype = subtype;
        this.url = url;
        this.voice = voice;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    @Override
    public String toJsonString() {
        return FormatUtil.toMessageJsonStr("music", OMUtil.writeValueAsString(this));
    }
}
