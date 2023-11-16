package com.enzip.robot.core.method;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import com.enzip.robot.component.annotation.Filter;
import com.enzip.robot.component.annotation.FilterValue;
import com.enzip.robot.component.event.BaseEvent;
import com.enzip.robot.component.message.Message;
import com.enzip.robot.component.message.MessagesContent;
import com.enzip.robot.component.message.element.At;
import com.enzip.robot.core.method.match.MatcherValue;
import com.enzip.robot.core.method.result.EventResult;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author Enzip
 * @since 2023/11/16 14:35
 */
@Slf4j
public class MethodEventHandler {

    public static void handle(BaseEvent baseEvent) {
        List<MethodInfo> methodInfoList = MethodFactory.getMethodInfoListByEventName(baseEvent.getClass().getSimpleName());
        for (MethodInfo methodInfo : methodInfoList) {
            Object object = methodInfo.getObject();
            Method method = methodInfo.getMethod();
            if (methodFilter(methodInfo, baseEvent)) {
                continue;
            }
            Object[] objects = methodParamBind(methodInfo, baseEvent);
            Object invoke;
            try {
                invoke = method.invoke(object, objects);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            if (methodChainInterrupt(invoke)) {
                break;
            }
        }
    }

    private static boolean methodFilter(MethodInfo methodInfo, BaseEvent baseEvent) {
        Method method = methodInfo.getMethod();
        boolean hasFilter = method.isAnnotationPresent(Filter.class);
        if (hasFilter) {
            Filter filter = method.getAnnotation(Filter.class);
            Filter.Targets targets = filter.targets();
            MessagesContent messagesContent = getMessagesContent(baseEvent);
            MatcherValue matcherValue = new MatcherValue(filter.matchType(), filter.value());
            if (targets != null) {
                if (ArrayUtil.isNotEmpty(targets.bots())) {
                    return !ArrayUtil.contains(targets.bots(), String.valueOf(baseEvent.getSelfId()));
                }
                if (ArrayUtil.isNotEmpty(targets.authors())) {
                    try {
                        Long userId = (Long) ReflectUtil.getFieldValue(baseEvent, "user_id");
                        return !ArrayUtil.contains(targets.authors(), String.valueOf(userId));
                    } catch (UtilException e) {
                        log.error("filter error by userId : {}", e.getMessage());
                    }
                }
                if (ArrayUtil.isNotEmpty(targets.groups())) {
                    try {
                        Long groupId = (Long) ReflectUtil.getFieldValue(baseEvent, "group_id");
                        return !ArrayUtil.contains(targets.groups(), String.valueOf(groupId));
                    } catch (UtilException e) {
                        log.error("filter error by groupId : {}", e.getMessage());
                    }
                }
                if (targets.atBot()) {
                    boolean isFilter = true;
                    for (Message message : messagesContent.getMessages()) {
                        if (message instanceof At) {
                            isFilter = !((At) message).getQq().equals((baseEvent.getSelfId()));
                        }
                    }
                    return isFilter;
                }
            }
            String plainText = messagesContent.getPlainText();
            if (plainText != null && plainText.length() != 0) {
                return !matcherValue.matches(plainText);
            } else {
                return !filter.ifNullPass();
            }
        }
        return false;
    }

    private static Object[] methodParamBind(MethodInfo methodInfo, BaseEvent baseEvent) {
        Parameter[] parameters = methodInfo.getMethod().getParameters();
        boolean hasFilter = methodInfo.getMethod().isAnnotationPresent(Filter.class);
        MatcherValue matcherValue = null;
        MessagesContent messagesContent = null;
        if (hasFilter) {
            Filter filter = methodInfo.getMethod().getAnnotation(Filter.class);
            messagesContent = getMessagesContent(baseEvent);
            matcherValue = new MatcherValue(filter.matchType(), filter.value());
        }
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.getType().equals(baseEvent.getClass())) {
                objects[i] = baseEvent;
            } else {
                boolean hasFilterValue = parameter.isAnnotationPresent(FilterValue.class);
                if (hasFilterValue && hasFilter) {
                    FilterValue filterValue = parameter.getAnnotation(FilterValue.class);
                    String value = filterValue.value();
                    boolean required = filterValue.required();
                    if (required && (value == null || value.length() == 0)) {
                        throw new RuntimeException("filterValue bind content is null");
                    }
                    String param = matcherValue.getParam(parameter.getName(), messagesContent.getPlainText());
                    objects[i] = param;
                }
            }
        }
        return objects;
    }

    private static boolean methodChainInterrupt(Object invoke) {
        if (invoke != null) {
            if (invoke instanceof EventResult) {
                EventResult result = (EventResult) invoke;
                return result.isTruncated();
            }
        }
        return false;
    }

    private static MessagesContent getMessagesContent(BaseEvent baseEvent) {
        MessagesContent messagesContent;
        try {
            messagesContent = (MessagesContent) ReflectUtil.getFieldValue(baseEvent, "messagesContent");
        } catch (UtilException ignored) {
            messagesContent = new MessagesContent();
        }
        return messagesContent;
    }
}
