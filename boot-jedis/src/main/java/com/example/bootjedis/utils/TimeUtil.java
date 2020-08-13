package com.example.bootjedis.utils;

/**
 * @author yuxiangzang
 * @create 2020-08-12-5:44 下午
 * @desc
 **/
public class TimeUtil {
  private long curTime;

  public TimeUtil() {
    this.curTime = System.currentTimeMillis();
  }

  public long getTimeAndReset() {
    return System.currentTimeMillis() - this.curTime;
  }
}
