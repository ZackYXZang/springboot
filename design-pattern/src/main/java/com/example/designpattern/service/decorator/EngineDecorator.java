package com.example.designpattern.service.decorator;

/**
 * 引擎的装饰类
 */
public class EngineDecorator extends Decorator{

  public EngineDecorator(Vehicle vehicle) {
    super(vehicle);
  }

  public void run() {
    super.run();
    //增加引擎的装饰
    addEngineDecorator();
  }

  public void addEngineDecorator() {
    System.out.println("增加引擎的装饰");
  }
}
