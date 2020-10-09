package com.example.bootjedis.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sxk
 * @date 2020/6/15 2:32 下午
 */
@Slf4j
public class LocalDateUtils {

  public static final DateTimeFormatter PATTERN_YYYY_MM_DD = DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  public static final DateTimeFormatter PATTERN_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

  public static final DateTimeFormatter PATTERN_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");

  public static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();

  public static final DateTimeFormatter HH = DateTimeFormatter.ofPattern("HH");

  /**
   * 格式 yyyyMMdd
   *
   * @return
   */
  public static String getNowStr() {
    return getNowStr(PATTERN_YYYYMMDD);
  }

  public static String getNowStr(DateTimeFormatter dtf) {
    LocalDate now = LocalDate.now();
    return now.format(dtf);
  }

  public static String getNowTimeStr(DateTimeFormatter dtf) {
    LocalDateTime now = LocalDateTime.now();
    return now.format(dtf);
  }

  public static String format(LocalDate date) {
    return format(date, PATTERN_YYYY_MM_DD);
  }

  public static String format(LocalDateTime dateTime) {
    return format(dateTime, PATTERN_YYYY_MM_DD_HH_MM_SS);
  }

  public static String format(LocalDate date, DateTimeFormatter dtf) {
    return date.format(dtf);
  }

  public static String format(LocalDateTime dateTime, DateTimeFormatter dtf) {
    return dateTime.format(dtf);
  }

  public static LocalDate str2LocalDate(String dateStr) {
    return str2LocalDate(dateStr, PATTERN_YYYY_MM_DD);
  }

  public static LocalDate str2LocalDate(String dateStr, DateTimeFormatter dtf) {
    try {
      return LocalDate.parse(dateStr, dtf);
    } catch (Exception e) {
      log.error("local date:{} parse error:", dateStr, e);
      return null;
    }
  }

  public static LocalDateTime str2LocalDateTime(String dateStr) {
    return str2LocalDateTime(dateStr, PATTERN_YYYY_MM_DD_HH_MM_SS);
  }

  public static LocalDateTime str2LocalDateTime(String dateStr, DateTimeFormatter dtf) {
    try {
      return LocalDateTime.parse(dateStr, dtf);
    } catch (Exception e) {
      log.error("local date time:{}, parse error:", dateStr, e);
      return null;
    }
  }

  /**
   * 计算两个日期 相隔多少年
   *
   * @param startDate 2000
   * @param endDate   2005
   * @return endDate-startDate=5
   */
  public static int betweenYears(LocalDate startDate, LocalDate endDate) {
    return (int) ChronoUnit.YEARS.between(startDate, endDate);
  }

  public static int betweenDays(LocalDate startDate, LocalDate endDate) {
    return (int) ChronoUnit.DAYS.between(startDate, endDate);
  }

  public static LocalDateTime toLocalDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * 判断是否为同一天
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static boolean isSameDay(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate == null || endDate == null) {
      return false;
    }
    return startDate.toLocalDate().equals(endDate.toLocalDate());
  }

  public static long getUnixSecondTime(LocalDateTime localDateTime) {
    return localDateTime.atZone(SYSTEM_ZONE_ID).toInstant().getEpochSecond();
  }

  public static long getUnixMilliTime(LocalDateTime localDateTime) {
    return localDateTime.atZone(SYSTEM_ZONE_ID).toInstant().toEpochMilli();
  }


  /**
   * 对非法数据 补充0
   *
   * @param dateStr
   * @return
   */
  public static String fixDateStr(String dateStr) {
    if (dateStr != null && dateStr.length() != 10) {
      StringJoiner sj = new StringJoiner("-");
      String[] split = dateStr.split("-");
      if (split.length == 3) {
        sj.add(split[0]);
        if (split[1].length() != 2) {
          sj.add("0" + split[1]);
        } else {
          sj.add(split[1]);
        }
        if (split[2].length() != 2) {
          sj.add("0" + split[2]);
        } else {
          sj.add(split[2]);
        }
      }
      return sj.toString();
    }
    return dateStr;
  }
}
