package com.example.bootjedis.config;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.example.bootjedis.utils.AspectUtils;
import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author yuxiangzang
 * @create 2020-08-06-11:55 上午
 * @desc
 **/
@Profile({"dev", "prod"})
@Component
@Aspect
public class CatServiceAspect {

  private static Logger log = LoggerFactory.getLogger(com.example.bootjedis.config.CatServiceAspect.class);

  /**
   * 定义切入点，切入点为com.zhongtai.live.service.impl中的所有函数
   * <p>
   * 通过@Pointcut注解声明频繁使用的切点表达式
   */
  @Pointcut("execution(public * com.example.bootjedis.Service.Impl.UserServiceImpl.*(..)))")
  public void mainPageAspect() {
    System.out.println("zangyuxiang");
  }

  @Around("mainPageAspect()")
  public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("zangyuxiang");
    Cat.logMetricForCount("matching_num");
    return execute(proceedingJoinPoint);
  }

  private static final ThreadLocal<Stopwatch> startLocal = ThreadLocal.withInitial(
      Stopwatch::createUnstarted);

  public static Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    startLocal.get().start();
    String classMethod = AspectUtils.extractClassMethod(joinPoint);
    System.out.println(classMethod);
    Transaction t = Cat.newTransaction("Service", classMethod);

    try {
      Object result = joinPoint.proceed();
      System.out.println(result);
      t.setStatus(Message.SUCCESS);
      return result;
    } catch (Throwable e) {
      t.setStatus(e);
      log.error("mainPageServiceAspect:", e);
      throw e;
    } finally {
      t.complete();
      startLocal.get().stop();
      log.info("{} end use time:{} ms", classMethod,
          startLocal.get().elapsed(TimeUnit.MILLISECONDS));
      startLocal.get().reset();
    }

  }
}
