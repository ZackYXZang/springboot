package com.example.designpattern.service.proxy;

import java.util.Random;

public class Car implements Movable{

  @Override
  public void move() {
    System.out.println("car move didididi...");
    try {
      Thread.sleep(new Random().nextInt(1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
