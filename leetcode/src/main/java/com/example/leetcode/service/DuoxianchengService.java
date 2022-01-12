package com.example.leetcode.service;

import java.util.concurrent.Semaphore;
import org.springframework.stereotype.Service;

@Service
public class DuoxianchengService {

  public void printABC() {
    final Semaphore semaphore1 = new Semaphore(1);
    final Semaphore semaphore2 = new Semaphore(0);
    final Semaphore semaphore3 = new Semaphore(0);
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10; i++) {
          try {
            semaphore1.acquire();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("a");
          semaphore2.release();
        }
      }
    });
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10; i++) {
          try {
            semaphore2.acquire();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("b");
          semaphore3.release();
        }
      }
    });
    Thread t3 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10; i++) {
          try {
            semaphore3.acquire();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("c");
          semaphore1.release();
        }
      }
    });
    t1.start();
    t2.start();
    t3.start();
  }


  //生产者消费者
  private static Integer count = 0;
  private final Integer FULL = 5;
  private static String lock = "lock";

  public void test()
  {
    DuoxianchengService t = new DuoxianchengService();
    new Thread(t.new Producer()).start();
    new Thread(t.new Consumer()).start();
    new Thread(t.new Producer()).start();
    new Thread(t.new Consumer()).start();
  }
  class Producer implements Runnable
  {
    @Override
    public void run()
    {
      for (int i = 0; i < 5; i++)
      {
//        try {
//          Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//          // TODO Auto-generated catch block
//          e1.printStackTrace();
//        }
        synchronized (lock)
        {
          while (count == FULL)
          {
            try {
              lock.wait();
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          count++;
          System.out.println(Thread.currentThread().getName() + "produce:: " + count);
          lock.notifyAll();
        }
      }
    }
  }
  class Consumer implements Runnable
  {
    @Override
    public void run()
    {
      for (int i = 0; i < 5; i++)
      {
//        try {
//          Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//          // TODO Auto-generated catch block
//          e1.printStackTrace();
//        }
        synchronized (lock)
        {
          while (count == 0)
          {
            try {
              lock.wait();
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          count--;
          System.out.println(Thread.currentThread().getName()+ "consume:: " + count);
          lock.notifyAll();
        }
      }
    }
  }

}
