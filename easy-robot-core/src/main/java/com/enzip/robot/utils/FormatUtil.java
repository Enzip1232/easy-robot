package com.enzip.robot.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Enzip
 * @since 2023/10/8 14:48
 */
@Slf4j
public class FormatUtil {

    private FormatUtil() {
    }

    public static String toMessageJsonStr(String type, String data) {
        return String.format("{\"type\":\"%s\",\"data\":%s}", type, data);
    }

}
