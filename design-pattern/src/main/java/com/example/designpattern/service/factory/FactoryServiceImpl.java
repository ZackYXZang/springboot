package com.example.designpattern.service.factory;

import com.example.designpattern.service.factory.abstractFactory.AbstractFactory;
import com.example.designpattern.service.factory.abstractFactory.Food;
import com.example.designpattern.service.factory.abstractFactory.ModernAbstractFactory;
import com.example.designpattern.service.factory.abstractFactory.Vehicle;
import com.example.designpattern.service.factory.abstractFactory.Weapon;
import com.example.designpattern.service.factory.simpleFactory.Broom;
import com.example.designpattern.service.factory.simpleFactory.Car;
import com.example.designpattern.service.factory.simpleFactory.Plane;
import com.example.designpattern.service.factory.simpleFactory.SimpleBroomFactory;
import com.example.designpattern.service.factory.simpleFactory.SimpleCarFactory;
import com.example.designpattern.service.factory.simpleFactory.SimplePlaneFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工厂模式
 */
@Service
public class FactoryServiceImpl {


  //简单工厂，扩展性不好
  public void simpleFactory() {
    Car car = (Car)new SimpleCarFactory().create();
    car.go();
    Plane plane = (Plane)new SimplePlaneFactory().create();
    plane.go();
    Broom broom = (Broom)new SimpleBroomFactory().create();
    broom.go();
  }

  //抽象工厂，针对的是产品族
  public void abstractFactory() {
    Food food = new ModernAbstractFactory().createFood();
    Vehicle vehicle = new ModernAbstractFactory().createVehicle();
    Weapon weapon = new ModernAbstractFactory().createWeapon();
  }



}
