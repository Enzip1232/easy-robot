package com.enzip.robot.utils;

import com.enzip.robot.component.message.Message;
import com.enzip.robot.component.message.Messages;
import com.enzip.robot.component.message.MessagesBuilder;
import com.enzip.robot.component.message.element.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Enzip
 * @since 2023/10/11 22:15
 */
@Slf4j
public class MessagesUtil {

    private MessagesUtil() {

    }

    public static Messages jsonToMessages(JsonNode jsonNode) {
        MessagesBuilder messagesBuilder = new MessagesBuilder();
        for (JsonNode node : jsonNode) {
            Message message = jsonToMessage(node);
            if (message != null) {
                messagesBuilder.add(message);
            }
        }
        return messagesBuilder.build();
    }

    public static String getPlainText(Messages messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Message message : messages) {
            if (message instanceof Text) {
                String text = ((Text) message).getText();
                stringBuilder.append(text);
            }
        }
        return stringBuilder.toString();
    }

    private static Message jsonToMessage(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        String type = OMUtil.asText(jsonNode, "type");
        if (type == null || "".equals(type)) {
            return null;
        }
        Class<? extends Message> messageClass = null;
        switch (type) {
            case "at":
                messageClass = At.class;
                break;
            case "contact":
                messageClass = Contact.class;
                break;
            case "face":
                messageClass = Face.class;
                break;
            case "forward":
                messageClass = Forward.class;
                break;
            case "image":
                messageClass = Image.class;
                break;
            case "location":
                messageClass = Location.class;
                break;
            case "music":
                messageClass = Music.class;
                break;
            case "node":
                messageClass = Node.class;
                break;
            case "poke":
                messageClass = Poke.class;
                break;
            case "record":
                messageClass = Record.class;
                break;
            case "reply":
                messageClass = Reply.class;
                break;
            case "shake":
                messageClass = Shake.class;
                break;
            case "share":
                messageClass = Share.class;
                break;
            case "text":
                messageClass = Text.class;
                break;
            case "video":
                messageClass = Video.class;
                break;
            default:
                log.error("unknown message type");
                break;
        }
        if (messageClass == null) {
            return null;
        }
        return OMUtil.convertValue(jsonNode.get("data"), messageClass);
    }
}
