package com.enzip.robot.component.api.friend.info;

import com.enzip.robot.component.api.BaseApi;

/**
 * 获取好友列表
 *
 * @author Enzip
 * @since 2023/8/21 15:12
 */
public class GetFriendList extends BaseApi {

    @Override
    public String getAction() {
        return "get_friend_list";
    }

    @Override
    public Object getParams() {
        return null;
    }
}
