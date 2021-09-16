package com.example.designpattern.service.singleton;

/**
 * Double Check 单例模式
 */
public class SingletonServiceImpl {

  private volatile static SingletonServiceImpl singletonService = null;

  private SingletonServiceImpl() {}

  public static SingletonServiceImpl getInstance() {
    System.out.println("单例模式 start");

    if (singletonService == null) {
      synchronized (SingletonServiceImpl.class) {
        if (singletonService == null) {
          singletonService =  new SingletonServiceImpl();
        }
      }
    }
    System.out.println("单例模式 end");
    return singletonService;

  }
}
