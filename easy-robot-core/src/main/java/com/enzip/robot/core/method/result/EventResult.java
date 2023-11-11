package com.enzip.robot.core.method.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Enzip
 * @since 2023/10/15 17:25
 */
@Getter
@AllArgsConstructor
public class EventResult {

    private Object content;
    private boolean truncated;

    public static EventResult truncate() {
        return of(null, true);
    }

    public static EventResult through() {
        return of(null, false);
    }

    public static EventResult of(Object content, boolean truncated) {
        return new EventResult(content, truncated);
    }
}
