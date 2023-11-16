package com.enzip.robot.core.method.match;

import java.util.regex.Matcher;

/**
 * @author Enzip
 * @since 2023/10/14 14:12
 */
public class MatchParameters {

    private final Matcher matcher;

    public MatchParameters(Matcher matcher) {
        this.matcher = matcher;
    }

    public boolean matches() {
        return matcher.matches();
    }

    public String get(String key) {
        if (matches()) {
            return matcher.group(key);
        }
        return null;
    }
}
