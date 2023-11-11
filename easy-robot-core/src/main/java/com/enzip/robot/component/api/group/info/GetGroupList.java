package com.enzip.robot.component.api.group.info;

import com.enzip.robot.component.api.BaseApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 获取群列表
 *
 * @author Enzip
 * @since 2023/8/22 9:06
 */
public class GetGroupList extends BaseApi {

    private final GetGroupList.Param param;

    public GetGroupList() {
        this.param = new Param();
    }

    public GetGroupList(Boolean noCache) {
        this.param = new Param();
        this.param.setNoCache(noCache);
    }

    @Override
    public String getAction() {
        return "get_group_list";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Data
    private static class Param {

        /**
         * 是否不使用缓存（使用缓存可能更新不及时, 但响应更快）
         */
        @JsonProperty("no_cache")
        private Boolean noCache;
    }
}
