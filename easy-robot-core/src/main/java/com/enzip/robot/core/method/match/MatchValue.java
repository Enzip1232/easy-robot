package com.enzip.robot.core.method.match;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Enzip
 * @since 2023/11/8 13:54
 */
@Getter
public class MatchValue {

    private final String originalRegex;
    private final MatchType matchType;
    private String regex;
    private Pattern pattern;

    public MatchValue(MatchType matchType, String originalRegex) {
        this.matchType = matchType;
        this.originalRegex = originalRegex;
        initRegexPattern(originalRegex);
    }

    private void initRegexPattern(String originalRegex) {
        List<MatchType> matchTypes = Arrays.asList(MatchType.REGEX_MATCHES, MatchType.REGEX_CONTAINS);
        if (matchTypes.contains(this.matchType)) {
            this.regex = originalValueToDynamicParametersRegexValue(originalRegex);
        } else {
            this.regex = Pattern.quote(originalRegex);
        }
        this.pattern = Pattern.compile(this.regex);
    }

    private String originalValueToDynamicParametersRegexValue(String originalValue) {
        StringBuilder builder = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        boolean on = false;
        List<Character> charList = new ArrayList<>();
        for (char c : originalValue.toCharArray()) {
            charList.add(c);
        }
        Iterator<Character> iter = charList.iterator();

        int flag = 0;

        while (iter.hasNext()) {
            char c = iter.next();
            if (!on) {
                if (c == '{') {
                    if (iter.hasNext()) {
                        char next = iter.next();
                        if (next == '{') {
                            on = true;
                        } else {
                            builder.append(c).append(next);
                        }
                    } else {
                        builder.append(c);
                    }
                } else {
                    builder.append(c);
                }
            } else {
                if (c == '{') {
                    flag++;
                    temp.append(c);
                } else if (c == '}') {
                    if (flag == 0 && iter.hasNext()) {
                        char next = iter.next();
                        if (next == '}') {
                            builder.append(dynamicParametersToRegex(temp.toString()));
                            temp = new StringBuilder();
                            on = false;
                        } else {
                            temp.append(c).append(next);
                        }
                    } else {
                        flag--;
                        temp.append(c);
                    }
                } else {
                    temp.append(c);
                }

            }
        }

        if (on) {
            throw new RuntimeException("There is no end flag '}}' for dynamic parameters.");
        }

        return builder.toString();
    }

    private String dynamicParametersToRegex(String parameter) {
        String[] parts = parameter.split(",", 2);
        String name = parts[0];
        String regex = (parts.length > 1) ? parts[1] : ".+";
        return String.format("(?<%s>%s)", name, regex);
    }

    public MatchParameters getParameters(String text) {
        return new MatchParameters(pattern.matcher(text));
    }

    public boolean matches(String text) {
        return pattern.matcher(text).matches();
    }

    public String getParam(String name, String text) {
        return pattern.matcher(text).group(name);
    }
}
