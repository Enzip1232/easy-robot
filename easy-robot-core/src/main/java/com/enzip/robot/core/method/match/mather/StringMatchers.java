package com.enzip.robot.core.method.match.mather;

/**
 * @author Enzip
 * @since 2023/10/14 17:04
 */
public enum StringMatchers implements StringMatcher {
    EQUALS(String::equals),
    EQUALS_IGNORE_CASE(String::equalsIgnoreCase),
    STARTS_WITH(String::startsWith),
    ENDS_WITH(String::endsWith),
    CONTAINS(String::contains);

    private final StringMatcher matcher;

    StringMatchers(StringMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(String target, String rule) {
        return matcher.matches(target, rule);
    }

}
