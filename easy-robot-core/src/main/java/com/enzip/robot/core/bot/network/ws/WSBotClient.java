package com.enzip.robot.core.bot.network.ws;

import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.component.api.BaseApi;
import com.enzip.robot.core.bot.network.BotClient;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * websocket机器人客户端
 *
 * @author Enzip
 * @since 2023/8/9 8:49
 */
@Slf4j
@Data
@RequiredArgsConstructor
public class WSBotClient implements BotClient {

    private static final Map<String, CompletableFuture<ApiResult>> COMPLETABLE_FUTURE_MAP = new ConcurrentHashMap<>();

    /**
     * 通道
     */
    private final Channel channel;

    public static Map<String, CompletableFuture<ApiResult>> getCompletableFutureMap() {
        return COMPLETABLE_FUTURE_MAP;
    }

    @Override
    public ApiResult invokeApi(BaseApi baseApi) {
        channel.writeAndFlush(new TextWebSocketFrame(baseApi.toJsonStr()));
        CompletableFuture<ApiResult> completableFuture = new CompletableFuture<>();
        COMPLETABLE_FUTURE_MAP.put(baseApi.getEcho(), completableFuture);
        ApiResult apiResult = getApiResult(baseApi.getEcho());
        if (apiResult == null || !"ok".equals(apiResult.getStatus())) {
            throw new RuntimeException(String.format("execute api error: %s", apiResult));
        }
        return apiResult;
    }

    private ApiResult getApiResult(String echo) {
        CompletableFuture<ApiResult> completableFuture = COMPLETABLE_FUTURE_MAP.get(echo);
        if (completableFuture == null) {
            return null;
        }
        try {
            ApiResult apiResult = completableFuture.get(1, TimeUnit.MINUTES);
            COMPLETABLE_FUTURE_MAP.remove(echo);
            return apiResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
