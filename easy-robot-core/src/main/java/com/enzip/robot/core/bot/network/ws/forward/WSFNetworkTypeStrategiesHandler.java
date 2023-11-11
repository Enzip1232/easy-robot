package com.enzip.robot.core.bot.network.ws.forward;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.enzip.robot.component.contact.Bot;
import com.enzip.robot.core.bot.BotFactory;
import com.enzip.robot.core.MessageDispatcher;
import com.enzip.robot.core.bot.network.BotClient;
import com.enzip.robot.core.bot.network.ws.WSBotClient;
import io.netty.channel.*;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * websocket正向网络类型策略处理器
 *
 * @author Enzip
 * @since 2023/8/9 11:37
 */
@Slf4j
public class WSFNetworkTypeStrategiesHandler extends SimpleChannelInboundHandler<Object> {

    private final WSFNetworkTypeHandler wsfNetworkTypeStrategies;

    private WebSocketClientHandshaker handShaker;
    private boolean shutdown = false;

    public WSFNetworkTypeStrategiesHandler(WSFNetworkTypeHandler wsfNetworkTypeStrategies) {
        this.wsfNetworkTypeStrategies = wsfNetworkTypeStrategies;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI(wsfNetworkTypeStrategies.getBotProperties().getUrl());
        handShaker = WebSocketClientHandshakerFactory.newHandshaker(
                uri, WebSocketVersion.V13, null, false, EmptyHttpHeaders.INSTANCE, 1280000);
        handShaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Map<Long, Bot> bots = BotFactory.getBots();
        bots.forEach((id, bot) -> {
            BotClient botClient = bot.getBotClient();
            if (botClient instanceof WSBotClient) {
                WSBotClient wsBotClient = (WSBotClient) botClient;
                if (wsBotClient.getChannel().id().asLongText().equals(ctx.channel().id().asLongText())) {
                    bots.remove(id);
                }
            }
        });
        if (shutdown) {
            return;
        }
        wsfNetworkTypeStrategies.connection();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handShaker.isHandshakeComplete() && msg instanceof FullHttpResponse) {
            handShaker.finishHandshake(ch, (FullHttpResponse) msg);
            BotClient botClient = new WSBotClient(wsfNetworkTypeStrategies.getChannel());
            Bot bot = new Bot(botClient);
            CompletableFuture.runAsync(() -> {
                bot.flushBotInfo();
                if (ObjUtil.isNull(bot.getUserId()) || StrUtil.isEmpty(bot.getNickname())) {
                    shutdown = true;
                }
                bot.flushFriends();
                bot.flushGroups();
                bot.getGroups().forEach((k, v) -> v.flushGroupMembers());
            }).whenCompleteAsync((v, ex) -> {
                bot.getCountDownLatch().countDown();
                if (ex == null) {
                    BotFactory.getBots().put(bot.getUserId(), bot);
                } else {
                    log.error("bot flush fail: {}", ex.getMessage());
                    shutdown = true;
                    ctx.close();
                }
            });
        }
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                MessageDispatcher.handler(textFrame.text());
            } else if (frame instanceof CloseWebSocketFrame) {
                ch.close();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(new PingWebSocketFrame());
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
