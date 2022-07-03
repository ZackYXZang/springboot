package com.thread.learn.service.impl;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service("threadLearnService")
public class ThreadLearnServiceImpl {


  private AtomicInteger executeNum = new AtomicInteger(0);

  ThreadLocal<Integer> executeCount = ThreadLocal.withInitial(() -> 0);


  public void threadLearnMethod1(String string) {

//    executeNum = new AtomicInteger(0);
    int times = Integer.parseInt(string);
    recursionExecute(times);

  }

  public void recursionExecute(Integer times) {
    executeNum.getAndIncrement();


    int i = executeCount.get().intValue();
    i++;
    executeCount.set(i);


    if (executeNum.intValue() == times) {
      return;
    }
    recursionExecute(times);
  }

}
