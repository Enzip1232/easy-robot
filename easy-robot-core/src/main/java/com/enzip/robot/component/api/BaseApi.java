package com.enzip.robot.component.api;

import cn.hutool.core.util.IdUtil;
import com.enzip.robot.utils.OMUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Enzip
 * @since 2023/8/12 15:28
 */
public abstract class BaseApi {

    public abstract String getAction();

    public abstract Object getParams();

    private final String echo = IdUtil.getSnowflakeNextIdStr();

    public String getEcho() {
        return echo;
    }

    public String toJsonStr() {
        Map<String, Object> map = new HashMap<>();
        map.put("action", this.getAction());
        map.put("params", this.getParams());
        map.put("echo", this.getEcho());
        return OMUtil.writeValueAsString(map);
    }
}
