package com.example.designpattern.service.factory.abstractFactory;

public class MagicAbstractFactory extends AbstractFactory{

  @Override
  Food createFood() {
    return new MushRoom();
  }

  @Override
  Vehicle createVehicle() {
    return new Broom();
  }

  @Override
  Weapon createWeapon() {
    return new MagicStick();
  }
}
