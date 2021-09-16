package com.example.designpattern.service.proxy;

/**
 * 时间代理
 */
public class CarTimeStaticProxy implements Movable{

  Movable movable;

  public CarTimeStaticProxy(Movable movable) {
    this.movable = movable;
  }

  @Override
  public void move() {
    long start = System.currentTimeMillis();
    movable.move();
    long end = System.currentTimeMillis();
    System.out.println(end - start);
  }
}
