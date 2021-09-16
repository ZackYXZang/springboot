package com.example.designpattern.service.decorator;

/**
 * 装饰的父类
 */
public class Decorator implements Vehicle{

  private Vehicle vehicle;
  public Decorator(Vehicle vehicle) {
    this.vehicle = vehicle;
  }


  @Override
  public void run() {
    vehicle.run();
  }
}
