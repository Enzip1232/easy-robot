package com.enzip.robot.component.message;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Enzip
 * @since 2023/10/8 15:27
 */
@Slf4j
public class Messages extends ArrayList<Message> {

    private Messages() {
    }

    public String toJsonString() {
        return this.stream().map(Message::toJsonString).collect(Collectors.joining(",", "[", "]"));
    }
}
