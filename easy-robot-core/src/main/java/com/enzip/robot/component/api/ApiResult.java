package com.enzip.robot.component.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * api结果
 *
 * @author Enzip
 * @since 2023/8/9 17:20
 */
@Data
public class ApiResult {

    /**
     * 状态, 表示 API 是否调用成功, 如果成功, 则是 OK, 其他的在下面会说明
     */
    private String status;

    /**
     * 重新编码
     */
    @JsonProperty("retcode")
    private int retCode;

    /**
     * 错误消息, 仅在 API 调用失败时有该字段
     */
    private String message;

    /**
     * 对错误的详细解释(中文), 仅在 API 调用失败时有该字段
     */
    private String wording;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * '回声', 如果请求时指定了 echo, 那么响应也会包含 echo
     */
    private String echo;
}
