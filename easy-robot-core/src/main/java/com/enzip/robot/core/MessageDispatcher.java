package com.enzip.robot.core;

import cn.hutool.core.util.StrUtil;
import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.core.bot.network.ws.WSBotClient;
import com.enzip.robot.core.handler.EventFactory;
import com.enzip.robot.utils.OMUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 消息调度器
 *
 * @author Enzip
 * @since 2023/8/16 8:48
 */
@Slf4j
public class MessageDispatcher {

    public static void handler(String message) {
        Map<?, ?> map = OMUtil.readValue(message, Map.class);
        if (map.containsKey("echo") && map.containsKey("status") && map.containsKey("retcode") && map.containsKey("data")) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> executeApi(message));
            future.exceptionally(throwable -> {
                throwable.printStackTrace();
                log.error("event execute error: {}", throwable.getMessage());
                return null;
            });
        } else if (map.containsKey("time") && map.containsKey("self_id") && map.containsKey("post_type")) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> executeEvent(message));
            future.exceptionally(throwable -> {
                throwable.printStackTrace();
                log.error("event execute error: {}", throwable.getMessage());
                return null;
            });
        }
    }

    private static void executeApi(String message) {
        try {
            ApiResult apiResult = OMUtil.readValue(message, ApiResult.class);
            CompletableFuture<ApiResult> completableFuture = WSBotClient.getCompletableFutureMap().get(apiResult.getEcho());
            if (completableFuture != null) {
                completableFuture.complete(apiResult);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void executeEvent(String message) {
        EventFactory.getInstance().handle(message);
        if (!StrUtil.contains(message, "meta_event")) {
            System.out.println(message);
        }
    }
}
