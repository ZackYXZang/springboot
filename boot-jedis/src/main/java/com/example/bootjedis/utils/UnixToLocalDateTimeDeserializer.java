package com.example.bootjedis.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author sxk
 * @date 2020/12/20 10:55 上午
 */
public class UnixToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
    return LocalDateUtils.timestampToLocalDateTime(p.getLongValue());
  }
}
