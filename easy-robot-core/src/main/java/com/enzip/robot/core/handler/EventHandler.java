package com.enzip.robot.core.handler;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Enzip
 * @since 2023/9/10 15:55
 */
public interface EventHandler {

    void handle(JsonNode jsonNode);
}
