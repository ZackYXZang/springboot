package com.example.designpattern.service.flyweight;

import org.springframework.stereotype.Service;

/**
 * 享元模式
 * 提前放置一些对象在池子中，之后可以重复利用对象
 * java中String用的就是享元模式
 */
@Service
public class FlyWeightServiceImpl {


  public void flyWeight() {
    FlyWeightPool flyWeightPool = new FlyWeightPool();
    for (int i = 0; i < 50; i++) {
      FlyWeight flyWeight = flyWeightPool.getFlyWeight();
      System.out.println(flyWeight);
    }
  }
}
