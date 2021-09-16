package com.example.designpattern.service.factory.abstractFactory;

public abstract class AbstractFactory {

  public abstract Food createFood();

  public abstract Vehicle createVehicle();

  public abstract Weapon createWeapon();
}
