package com.example.designpattern.service.factory.abstractFactory;

public abstract class AbstractFactory {

  abstract Food createFood();

  abstract Vehicle createVehicle();

  abstract Weapon createWeapon();
}
