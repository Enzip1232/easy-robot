package com.enzip.robot.core.bot.network;

import com.enzip.robot.core.bot.BotProperties;
import com.enzip.robot.core.bot.network.ws.forward.WSFNetworkTypeHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 网络类型工厂
 *
 * @author Enzip
 * @since 2023/8/3 9:33
 */
@Slf4j
public class NetWorkFactory {

    private static volatile NetWorkFactory INSTANCE = null;

    /**
     * 网络类型策略集合
     */
    private static final Map<String, Supplier<NetworkTypeHandler>> NETWORK_TYPE_HANDLERS = new HashMap<>();

    static {
        NETWORK_TYPE_HANDLERS.put(NetWorkType.WSF, WSFNetworkTypeHandler::new);
    }

    private NetWorkFactory(){}

    public static NetWorkFactory getInstance(){
        if (INSTANCE == null){
            synchronized (NetWorkFactory.class){
                if (INSTANCE == null){
                    INSTANCE = new NetWorkFactory();
                }
            }
        }
        return INSTANCE;
    }

    public void initBotNetwork(List<BotProperties> botPropertiesList) {
        for (BotProperties botProperties : botPropertiesList) {
            Supplier<NetworkTypeHandler> networkTypeHandlerSupplier = NETWORK_TYPE_HANDLERS.get(botProperties.getType());
            if (networkTypeHandlerSupplier == null) {
                log.warn(String.format("%s is not supported.", botProperties.getType()));
                return;
            }
            NetworkTypeHandler networkTypeHandler = networkTypeHandlerSupplier.get();
            networkTypeHandler.init(botProperties);
        }
    }
}
