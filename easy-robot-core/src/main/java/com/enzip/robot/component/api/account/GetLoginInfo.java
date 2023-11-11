package com.enzip.robot.component.api.account;

import com.enzip.robot.component.api.BaseApi;

/**
 * 获取登录号信息
 *
 * @author Enzip
 * @since 2023/8/15 15:33
 */
public class GetLoginInfo extends BaseApi {

    @Override
    public String getAction() {
        return "get_login_info";
    }

    @Override
    public Object getParams() {
        return null;
    }
}
