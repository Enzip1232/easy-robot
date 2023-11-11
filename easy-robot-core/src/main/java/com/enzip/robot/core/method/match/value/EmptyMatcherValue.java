package com.enzip.robot.core.method.match.value;

import com.enzip.robot.core.method.match.parameter.EmptyFilterParameters;
import com.enzip.robot.core.method.match.parameter.MatchParameters;
import com.enzip.robot.core.method.match.value.MatcherValue;

import java.util.regex.Pattern;

/**
 * @author Enzip
 * @since 2023/10/14 17:51
 */
public class EmptyMatcherValue implements MatcherValue {

    @Override
    public Pattern getRegex() {
        return Pattern.compile("");
    }

    @Override
    public String getOriginal() {
        return "";
    }

    @Override
    public boolean matches(String text) {
        return true;
    }

    @Override
    public String getParam(String name, String text) {
        return null;
    }

    @Override
    public MatchParameters getParameters(String text) {
        return new EmptyFilterParameters();
    }
}
