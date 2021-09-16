package com.example.designpattern.service.decorator;

import org.springframework.stereotype.Service;

/**
 * 装饰模式
 */
@Service
public class DecoratorServiceImpl {

  public void decorator() {
    //新建一个实体：车
    Vehicle car = new Car();
    car.run();

    //增加引擎、武器的装饰，把车作为参数传入
    Vehicle engineDecorator = new EngineDecorator(car);
    Vehicle weaponDecorator = new WeaponDecorator(car);
    engineDecorator.run();
    weaponDecorator.run();

  }

}
