package com.example.bootjedis.utils;

import com.google.common.base.Strings;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @author sxk
 * @date 2021/1/22 7:19 下午
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

  @Override
  public LocalDateTime convert(String source) {
    if (Strings.isNullOrEmpty(source) || !StringUtils.isNumeric(source)) {
      return null;
    }
    return LocalDateUtils.timestampToLocalDateTime(Long.parseLong(source));
  }
}
