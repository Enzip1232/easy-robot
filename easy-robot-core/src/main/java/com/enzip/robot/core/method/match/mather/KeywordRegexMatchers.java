package com.enzip.robot.core.method.match.mather;

/**
 * @author Enzip
 * @since 2023/10/14 14:17
 */
public enum KeywordRegexMatchers implements KeywordMatcher {
    MATCHES((t, r) -> r.getRegex().matcher(t).matches()),
    CONTAINS((t, r) -> r.getRegex().matcher(t).find());

    private final KeywordMatcher matcher;

    KeywordRegexMatchers(KeywordMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(String target, Keyword rule) {
        return matcher.matches(target, rule);
    }
}
