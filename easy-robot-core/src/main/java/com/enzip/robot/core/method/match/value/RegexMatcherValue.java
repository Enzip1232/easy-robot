package com.enzip.robot.core.method.match.value;


import com.enzip.robot.core.method.match.parameter.EmptyFilterParameters;
import com.enzip.robot.core.method.match.parameter.MatchParameters;
import com.enzip.robot.core.method.match.parameter.MatcherParameters;
import com.enzip.robot.core.method.match.value.MatcherValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Enzip
 * @since 2023/10/14 14:19
 */
public class RegexMatcherValue implements MatcherValue {
    private final String originalValue;
    private final boolean isPlainText;
    private final java.util.regex.Pattern regex;

    public RegexMatcherValue(String originalValue, boolean isPlainText) {
        this.originalValue = originalValue;
        this.isPlainText = isPlainText;
        this.regex = createRegex();
    }

    @Override
    public java.util.regex.Pattern getRegex() {
        return regex;
    }

    @Override
    public String getOriginal() {
        return originalValue;
    }

    @Override
    public boolean matches(String text) {
        java.util.regex.Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }

    @Override
    public String getParam(String name, String text) {
        java.util.regex.Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            return matcher.group(name);
        }
        return null;
    }

    @Override
    public MatchParameters getParameters(String text) {
        java.util.regex.Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            return new MatcherParameters(matcher);
        } else {
            return new EmptyFilterParameters();
        }
    }

    private java.util.regex.Pattern createRegex() {
        if (isPlainText) {
            return java.util.regex.Pattern.compile(java.util.regex.Pattern.quote(originalValue));
        } else {
            String regexValue = originalValueToDynamicParametersRegexValue(originalValue);
            return java.util.regex.Pattern.compile(regexValue);
        }
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
}
