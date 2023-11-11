package com.enzip.robot.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Enzip
 * @since 2023/8/12 17:32
 */
@Slf4j
public class OMUtil {

    private OMUtil() {
    }

    private static volatile ObjectMapper INSTANCE = null;

    public static ObjectMapper getMapper() {
        if (INSTANCE == null) {
            synchronized (OMUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ObjectMapper();
                    //配置遇到未知属性时不会引发异常
                    INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                }
            }
        }
        return INSTANCE;
    }

    public static String writeValueAsString(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("OMUtil writeValueAsString error.");
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return getMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("OMUtil readValue error.");
        }
    }

//    public static <T> T readValue(JsonNode jsonNode, Class<T> valueType) {
//        return readValue(writeValueAsString(jsonNode), valueType);
//    }

    public static JsonNode readTree(String str) {
        try {
            return getMapper().readTree(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("OMUtil readTree error.");
        }
    }

    public static JsonNode valueToTree(Object object) {
        return getMapper().valueToTree(object);
    }

    public static <T> T convertValue(Object object, Class<T> valueType) {
        return getMapper().convertValue(object, valueType);
    }

    public static String asText(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asText() : null;
    }

    public static Long asLong(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asLong() : null;
    }

    public static Integer asInt(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asInt() : null;
    }

    public static Boolean asBoolean(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asBoolean() : null;
    }
}
