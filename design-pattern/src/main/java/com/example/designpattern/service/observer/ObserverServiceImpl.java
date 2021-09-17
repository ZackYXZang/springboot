package com.example.designpattern.service.observer;

import org.springframework.stereotype.Service;

/**
 * 观察者模式
 * 一般用于事件处理模型
 */
@Service
public class ObserverServiceImpl {


  public void observer() {

    Button button = new Button();
    button.notifyObserver();
  }
}
