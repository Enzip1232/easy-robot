package com.enzip.robot.core.method;

import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Enzip
 * @since 2023/9/22 15:44
 */
@Data
public class MethodInfo {

    private Object object;

    private Method method;
}
