package com.example.bootjedis.utils;

import com.google.common.base.Strings;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @author sxk
 * @date 2021/1/22 7:19 下午
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

  @Override
  public LocalDate convert(String source) {
    if (Strings.isNullOrEmpty(source) || !StringUtils.isNumeric(source)) {
      return null;
    }
    return LocalDateUtils.timestampToLocalDate(Long.parseLong(source));
  }
}
