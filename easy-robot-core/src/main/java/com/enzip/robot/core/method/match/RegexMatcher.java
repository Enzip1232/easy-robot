package com.enzip.robot.core.method.match;

import com.enzip.robot.core.method.match.value.MatcherValue;
import com.enzip.robot.core.method.match.value.RegexMatcherValue;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * @author Enzip
 * @since 2023/10/14 17:40
 */
@Getter
public class RegexMatcher {

    private final MatcherValue matcherValue;
    private final Pattern pattern;

    public RegexMatcher(MatchType matchType, String regex) {
        RegexMatcherValue regexMatcherValue = new RegexMatcherValue(regex, matchType.isPlainText());
        this.matcherValue = regexMatcherValue;
        this.pattern = regexMatcherValue.getRegex();
    }
}
