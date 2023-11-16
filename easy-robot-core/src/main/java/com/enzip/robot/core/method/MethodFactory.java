package com.enzip.robot.core.method;

import cn.hutool.extra.spring.SpringUtil;
import com.enzip.robot.component.annotation.Listener;
import com.enzip.robot.component.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Enzip
 * @since 2023/9/9 20:24
 */
@Slf4j
public class MethodFactory {

    private static final Map<String, List<MethodInfo>> METHOD_INFO_LIST_MAP = new ConcurrentHashMap<>();

    public static void initMethod() {
        Map<String, Object> beansOfType = SpringUtil.getApplicationContext().getBeansOfType(Object.class);
        List<MethodInfo> methodInfoList = filterSortedMethodInfoList(beansOfType);
        Map<String, List<MethodInfo>> methodInfoListMap = groupByMethodInfoList(methodInfoList);
        METHOD_INFO_LIST_MAP.putAll(methodInfoListMap);
        log.info("method init success");
    }

    public static Map<String, List<MethodInfo>> getMethodInfoListMap() {
        return METHOD_INFO_LIST_MAP;
    }

    public static List<MethodInfo> getMethodInfoListByEventName(String eventName) {
        return getMethodInfoListMap().get(eventName);
    }

    private static List<MethodInfo> filterSortedMethodInfoList(Map<String, Object> beansOfType) {
        List<MethodInfo> methodInfoList = new ArrayList<>();
        for (Object bean : beansOfType.values()) {
            Class<?> userClass = ClassUtils.getUserClass(bean);
            methodInfoList.addAll(
                    Arrays.stream(userClass.getMethods())
                            .filter(method -> {
                                boolean hasListener = method.isAnnotationPresent(Listener.class);
                                long baseEventCount = Arrays.stream(method.getParameterTypes())
                                        .filter(BaseEvent.class::isAssignableFrom).count();
                                return hasListener && baseEventCount == 1L;
                            })
                            .sorted(Comparator.comparingInt(method -> method.getAnnotation(Listener.class).priority()))
                            .map(method -> {
                                MethodInfo methodInfo = new MethodInfo();
                                methodInfo.setObject(bean);
                                methodInfo.setMethod(method);
                                return methodInfo;
                            }).collect(Collectors.toList())
            );
        }
        return methodInfoList;
    }

    private static Map<String, List<MethodInfo>> groupByMethodInfoList(List<MethodInfo> methodInfoList) {
        return methodInfoList.stream().collect(Collectors.groupingByConcurrent(methodInfo -> {
            Parameter[] parameters = methodInfo.getMethod().getParameters();
            Class<?> clazz = Arrays.stream(parameters).map(Parameter::getType)
                    .filter(BaseEvent.class::isAssignableFrom)
                    .collect(Collectors.toList())
                    .get(0);
            return clazz.getSimpleName();
        }, Collectors.toCollection(LinkedList::new)));
    }
}
