package com.example.bootjedis.utils;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author yuxiangzang
 * @create 2020-08-06-11:28 上午
 * @desc
 **/
public class AspectUtils {
  public static final String SEPARATOR = "@";

  /**
   * 抽取方法名
   *
   * @param joinPoint
   * @return
   */

  public static String extractClassMethod(ProceedingJoinPoint joinPoint) {
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    Method method = ms.getMethod();
    Class<?> clz = method.getDeclaringClass();
    return clz.getSimpleName() + SEPARATOR + method.getName();
  }
}
