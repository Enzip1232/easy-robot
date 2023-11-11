package com.enzip.robot.core.method.match.parameter;

/**
 * @author Enzip
 * @since 2023/10/14 14:13
 */
public class EmptyFilterParameters implements MatchParameters {

    @Override
    public String get(String key) {
        return null;
    }
}
