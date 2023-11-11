package com.enzip.robot.core.bot.network.ws.forward;

import com.enzip.robot.core.bot.BotProperties;
import com.enzip.robot.core.bot.network.NetworkTypeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * websocket正向网络类型策略
 *
 * @author Enzip
 * @since 2023/8/8 17:23
 */
@Slf4j
@Data
public class WSFNetworkTypeHandler implements NetworkTypeHandler {

    private final Bootstrap clientBootstrap = new Bootstrap();
    private BotProperties botProperties;
    private Channel channel;

    @Override
    public void init(BotProperties botProperties) {
        WSFNetworkTypeHandler wsfNetworkTypeStrategies = this;
        this.botProperties = botProperties;
        this.clientBootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(1024 * 1024 * 100))
                                .addLast(new WebSocketFrameAggregator(1024 * 1024 * 100))
                                .addLast(new WSFNetworkTypeStrategiesHandler(wsfNetworkTypeStrategies));
                    }
                });
        this.connection();
    }

    public void connection() {
        if (channel != null && channel.isActive()) {
            return;
        }
        URI wsUri;
        try {
            wsUri = new URI(this.botProperties.getUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException("websocket url format error.");
        }
        ChannelFuture channelFuture = clientBootstrap.connect(wsUri.getHost(), wsUri.getPort());
        channelFuture.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {
                channel = futureListener.channel();
            } else {
                log.error("Failed to connect to go-cqhttp used ws, try connect after 10s");
                futureListener.channel().eventLoop().schedule(this::connection, 10, TimeUnit.SECONDS);
            }
        });
    }

}
