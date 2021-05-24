package com.example.bootlettuce.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

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

  public static final DateTimeFormatter HH = DateTimeFormatter.ofPattern("HH");
  public static final DateTimeFormatter MIN = DateTimeFormatter.ofPattern("mm");

  public static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();

  /**
   * 格式 yyyyMMdd
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
    if (Strings.isBlank(dateStr)) {
      return null;
    }
    return str2LocalDate(dateStr, PATTERN_YYYY_MM_DD);
  }

  public static LocalDate str2LocalDate(String dateStr, DateTimeFormatter dtf) {
    try {
      return LocalDate.parse(dateStr, dtf);
    } catch (Exception e) {
      log.error("local date:'{}', parse error:", dateStr, e);
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
   * @param endDate 2005
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
   */
  public static String fixDateStr(String dateStr) {
    if (Strings.isBlank(dateStr) || Objects.equals("null", dateStr)) {
      return null;
    }
    if (dateStr.length() != 10) {
      if (dateStr.split(" ", -1).length > 1) {
        return dateStr.split(" ", -1)[0];
      }
      StringJoiner sj = new StringJoiner("-");
      String[] split = dateStr.split("-");
      if (split.length == 3) {
        sj.add(split[0]);
        String mm = split[1];
        if (mm.length() < 2) {
          sj.add("0" + mm);
        } else if (mm.length() > 2) {
          sj.add(mm.substring(mm.length() - 2));
        } else {
          sj.add(mm);
        }
        String dd = split[2];
        if (dd.length() < 2) {
          sj.add("0" + dd);
        } else if (dd.length() > 2) {
          sj.add(dd.substring(dd.length() - 2));
        } else {
          sj.add(dd);
        }
      }
      return sj.toString();
    }
    return dateStr;
  }

}
