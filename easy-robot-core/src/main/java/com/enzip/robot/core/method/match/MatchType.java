package com.enzip.robot.core.method.match;

import com.enzip.robot.core.method.match.mather.KeywordMatcher;
import com.enzip.robot.core.method.match.mather.*;

/**
 * @author Enzip
 * @since 2023/10/14 17:09
 */
public enum MatchType {
    TEXT_EQUALS(StringMatchers.EQUALS, true),
    TEXT_EQUALS_IGNORE_CASE(StringMatchers.EQUALS_IGNORE_CASE, true),
    TEXT_STARTS_WITH(StringMatchers.STARTS_WITH, true),
    TEXT_ENDS_WITH(StringMatchers.ENDS_WITH, true),
    TEXT_CONTAINS(StringMatchers.CONTAINS, true),
    REGEX_MATCHES(KeywordRegexMatchers.MATCHES, false),
    REGEX_CONTAINS(KeywordRegexMatchers.CONTAINS, false);

    private final Matcher<?,?> matcher;
    private final boolean isPlainText;

    MatchType(Matcher<?,?> matcher, boolean isPlainText) {
        this.matcher = matcher;
        this.isPlainText = isPlainText;
    }

    public boolean isPlainText() {
        return isPlainText;
    }
}
