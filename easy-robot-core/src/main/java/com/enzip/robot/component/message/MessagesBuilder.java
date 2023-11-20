package com.enzip.robot.component.message;

import cn.hutool.core.util.ReflectUtil;
import com.enzip.robot.component.message.element.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

/**
 * @author Enzip
 * @since 2023/10/8 14:59
 */
public class MessagesBuilder extends ArrayList<Message> {

    public MessagesBuilder text(String message) {
        this.add(new Text(message));
        return this;
    }

    public MessagesBuilder face(String id) {
        this.add(new Face(id));
        return this;
    }

    public MessagesBuilder at(Long qq) {
        this.add(new At(qq));
        return this;
    }

    public MessagesBuilder reply(Integer messageId, Long userId, String message) {
        this.add(new Reply(messageId));
        this.add(new At(userId));
        this.add(new At(userId));
        this.add(new Text(message));
        return this;
    }

    public MessagesBuilder image(String file) {
        this.add(new Image(file));
        return this;
    }

    public MessagesBuilder image(String file, String type) {
        this.add(new Image(file, type));
        return this;
    }

    public MessagesBuilder image(String file, String type, Integer id) {
        this.add(new Image(file, type, id));
        return this;
    }

    public MessagesBuilder poke(Long qq) {
        this.add(new Poke(qq));
        return this;
    }

    public MessagesBuilder video(String file) {
        this.add(new Video(file));
        return this;
    }

    public MessagesBuilder video(String file, String cover) {
        this.add(new Video(file, cover));
        return this;
    }

    public MessagesBuilder music(String type, String id) {
        this.add(new Music(type, id));
        return this;
    }

    public MessagesBuilder music(String type, String subtype, String url, String voice, String title) {
        this.add(new Music(type, subtype, url, voice, title));
        return this;
    }

    public MessagesBuilder music(String type, String subtype, String url, String voice, String title, String content, String image) {
        this.add(new Music(type, subtype, url, voice, title, content, image));
        return this;
    }

    public Messages build() {
        Messages messages = ReflectUtil.newInstance(Messages.class);
        messages.addAll(this);
        return messages;
    }
}
