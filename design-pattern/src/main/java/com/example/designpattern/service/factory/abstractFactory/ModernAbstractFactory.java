package com.example.designpattern.service.factory.abstractFactory;

import org.springframework.stereotype.Service;

public class ModernAbstractFactory extends AbstractFactory{

  @Override
  public Food createFood() {
    return new Bread();
  }

  @Override
  public Vehicle createVehicle() {
    return new Car();
  }

  @Override
  public Weapon createWeapon() {
    return new AK47();
  }
}
