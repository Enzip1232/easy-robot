package com.enzip.robot.core.method.match.value;

import com.enzip.robot.core.method.match.parameter.MatchParameters;

import java.util.regex.Pattern;

/**
 * @author Enzip
 * @since 2023/10/14 14:10
 */
public interface MatcherValue {
    /**
     * 获取用于匹配的正则
     */
    Pattern getRegex();

    /**
     * 获取原始字符串
     */
    String getOriginal();

    /**
     * 是否匹配. 使用的完全正则匹配: `regex.matches(text)`
     */
    boolean matches(String text);

    /**
     * 根据变量名称获取一个动态参数。
     * 此文本需要符合正则表达式。
     */
    String getParam(String name, String text);

    /**
     * 从一段匹配的文本中提取出需要的参数。
     * 此文本需要符合正则表达式, 否则得到null。
     */
    MatchParameters getParameters(String text);
}
