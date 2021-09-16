package com.example.designpattern.service.proxy;

/**
 * 日志代理
 */
public class CarLogStaticProxy implements Movable{

  Movable movable;

  public CarLogStaticProxy(Movable movable) {
    this.movable = movable;
  }

  @Override
  public void move() {
    System.out.println("car start to move");
    movable.move();
    System.out.println("car stop moving");
  }
}
