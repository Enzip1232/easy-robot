package com.enzip.robot.core.bot.network;

import com.enzip.robot.component.api.ApiResult;
import com.enzip.robot.component.api.BaseApi;

/**
 * 机器人客户端
 *
 * @author Enzip
 * @since 2023/8/9 8:48
 */
public interface BotClient {

    ApiResult invokeApi(BaseApi baseApi);
}
