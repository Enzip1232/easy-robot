package com.enzip.robot.component.api.group.info;

import com.enzip.robot.component.api.BaseApi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * get_group_member_list
 *
 * @author Enzip
 * @since 2023/8/23 11:34
 */
public class GetGroupMemberList extends BaseApi {

    private final GetGroupMemberList.Param param;

    public GetGroupMemberList(Long groupId) {
        this.param = new Param();
        this.param.groupId = groupId;
    }

    public GetGroupMemberList(Long groupId, Boolean noCache) {
        this.param = new Param();
        this.param.groupId = groupId;
        this.param.noCache = noCache;
    }

    @Override
    public String getAction() {
        return "get_group_member_list";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Data
    private static class Param {

        /**
         * 群号
         */
        @JsonProperty("group_id")
        private Long groupId;

        /**
         * 是否不使用缓存（使用缓存可能更新不及时, 但响应更快）
         */
        @JsonProperty("no_cache")
        private Boolean noCache;
    }
}
