package com.example.bootjedis.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sxk 创建日期：2020/5/18
 */
@Slf4j
public class JsonUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // 忽略未知字段.
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addDeserializer(LocalDateTime.class, new UnixToLocalDateTimeDeserializer());
    javaTimeModule.addDeserializer(LocalDate.class, new UnixToLocalDateDeserializer());
    javaTimeModule.addSerializer(LocalDate.class, new LocalDateToUnixSerializer());
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeToUnixSerializer());

    mapper.registerModule(javaTimeModule);
  }

  public static String writeValueAsString(Object value) {
    try {
      return mapper.writeValueAsString(value);
    } catch (Exception e) {
      log.error("write value as string error:", e);
      return null;
    }
  }

  public static <T> T readValue(String content, Class<T> clazz) {
    try {
      return mapper.readValue(content, clazz);
    } catch (Exception e) {
      log.error("json string read value error:", e);
      return null;
    }
  }

  public static <T> T readValue(byte[] content, Class<T> clazz) {
    try {
      return mapper.readValue(content, clazz);
    } catch (Exception e) {
      log.error("json string read value error:", e);
      return null;
    }
  }

  public static <T> T readValue(String content, TypeReference<T> typeRef) {
    try {
      return mapper.readValue(content, typeRef);
    } catch (Exception e) {
      log.error("json string read value error:", e);
      return null;
    }
  }

  public static <T> T readValue(byte[] content, TypeReference<T> typeRef) {
    try {
      return mapper.readValue(content, typeRef);
    } catch (Exception e) {
      log.error("json string read value error:", e);
      return null;
    }
  }

}
