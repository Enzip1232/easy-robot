package com.enzip.robot.core.method.match;

import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * @author Enzip
 * @since 2023/11/8 11:10
 */
public enum MatchType {
    TEXT_EQUALS(String::equals),
    TEXT_EQUALS_IGNORE_CASE(String::equalsIgnoreCase),
    TEXT_STARTS_WITH(String::startsWith),
    TEXT_ENDS_WITH(String::endsWith),
    TEXT_CONTAINS(String::contains),
    REGEX_MATCHES((t, r) -> Pattern.matches(r, t)),
    REGEX_CONTAINS((t, r) -> Pattern.compile(r).matcher(t).find());

    private final BiFunction<String, String, Boolean> matcher;

    // 构造函数
    MatchType(BiFunction<String, String, Boolean> matcher) {
        this.matcher = matcher;
    }

    // 使用枚举实例的方法来执行匹配
    public boolean match(String text, String regex) {
        return matcher.apply(text, regex);
    }
}
