package com.enzip.robot.core.method.match.mather;

/**
 * @author Enzip
 * @since 2023/10/14 14:09
 */
@FunctionalInterface
public interface Matcher<T, R> {
    boolean matches(T target, R rule);
}
