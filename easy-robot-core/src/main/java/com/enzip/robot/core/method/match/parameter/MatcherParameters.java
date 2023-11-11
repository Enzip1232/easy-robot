package com.enzip.robot.core.method.match.parameter;

/**
 * @author Enzip
 * @since 2023/10/14 14:12
 */
public class MatcherParameters implements MatchParameters {

    private final java.util.regex.Matcher matcher;

    public MatcherParameters(java.util.regex.Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public String get(String key) {
        return matcher.group(key);
    }
}
