package com.example.designpattern.service.singleton;

/**
 * Double Check 单例模式
 */
public class SingletonServiceImpl {

  private volatile static SingletonServiceImpl singletonService = null;

  private SingletonServiceImpl() {}

  public static SingletonServiceImpl getInstance() {
    if (singletonService == null) {
      synchronized (SingletonServiceImpl.class) {
        if (singletonService == null) {
          singletonService =  new SingletonServiceImpl();
        }
      }
    }
    return singletonService;
  }
}
