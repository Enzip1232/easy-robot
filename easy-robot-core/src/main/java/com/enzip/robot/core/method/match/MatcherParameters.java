package com.enzip.robot.core.method.match;

import java.util.regex.Matcher;

/**
 * @author Enzip
 * @since 2023/10/14 14:12
 */
public class MatcherParameters implements MatchParameters {

    private final Matcher matcher;

    public MatcherParameters(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public String get(String key) {
        return matcher.group(key);
    }
}
