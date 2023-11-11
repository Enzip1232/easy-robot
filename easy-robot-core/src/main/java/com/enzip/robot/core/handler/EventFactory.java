package com.enzip.robot.core.handler;

import com.enzip.robot.core.handler.message.GroupMessageEventHandler;
import com.enzip.robot.core.handler.message.PrivateMessageEventHandler;
import com.enzip.robot.core.handler.notice.GroupPokeEventHandler;
import com.enzip.robot.utils.OMUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 事件策略接口
 *
 * @author Enzip
 * @since 2023/9/12 9:45
 */
@Slf4j
public class EventFactory {

    private static volatile EventFactory INSTANCE = null;

    /**
     * 网络类型策略集合
     */
    private static final Map<String, Supplier<EventHandler>> EVENT_HANDLERS = new HashMap<>();

    static {
        EVENT_HANDLERS.put(GroupMessageEventHandler.class.getSimpleName(), GroupMessageEventHandler::new);
        EVENT_HANDLERS.put(PrivateMessageEventHandler.class.getSimpleName(), PrivateMessageEventHandler::new);
        EVENT_HANDLERS.put(GroupPokeEventHandler.class.getSimpleName(), GroupPokeEventHandler::new);
    }

    private EventFactory() {
    }

    public static EventFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (EventFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EventFactory();
                }
            }
        }
        return INSTANCE;
    }

    public void handle(String message) {
        JsonNode jsonNode = OMUtil.readTree(message);
        for (Supplier<EventHandler> eventHandlerSupplier : EVENT_HANDLERS.values()) {
            EventHandler eventHandler = eventHandlerSupplier.get();
            eventHandler.handle(jsonNode);
        }
    }
}
