package com.example.designpattern.service.decorator;

/**
 * 引擎的装饰类
 */
public class WeaponDecorator extends Decorator{

  public WeaponDecorator(Vehicle vehicle) {
    super(vehicle);
  }

  public void run() {
    super.run();
    //增加武器的装饰
    addWeaponDecorator();
  }

  public void addWeaponDecorator() {
    System.out.println("增加武器的装饰：增加机关枪...");
  }
}
