package com.enzip.robot.core.method.match.mather;

import com.enzip.robot.core.method.match.value.MatcherValue;

import java.util.regex.Pattern;

/**
 * @author Enzip
 * @since 2023/10/14 14:10
 */
public interface Keyword {
    Pattern getRegex();
    String getText();
    MatcherValue getMatcherValue();
}
