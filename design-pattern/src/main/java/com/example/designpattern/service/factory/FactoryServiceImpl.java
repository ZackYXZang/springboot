package com.example.designpattern.service.factory;

import com.example.designpattern.service.factory.abstractFactory.AbstractFactory;
import com.example.designpattern.service.factory.abstractFactory.Bread;
import com.example.designpattern.service.factory.abstractFactory.Food;
import com.example.designpattern.service.factory.abstractFactory.MagicAbstractFactory;
import com.example.designpattern.service.factory.abstractFactory.ModernAbstractFactory;
import com.example.designpattern.service.factory.abstractFactory.Vehicle;
import com.example.designpattern.service.factory.abstractFactory.Weapon;
import com.example.designpattern.service.factory.simpleFactory.Broom;
import com.example.designpattern.service.factory.simpleFactory.Car;
import com.example.designpattern.service.factory.simpleFactory.Plane;
import com.example.designpattern.service.factory.simpleFactory.SimpleBroomFactory;
import com.example.designpattern.service.factory.simpleFactory.SimpleCarFactory;
import com.example.designpattern.service.factory.simpleFactory.SimplePlaneFactory;
import org.springframework.stereotype.Service;

/**
 * 工厂模式
 */
@Service
public class FactoryServiceImpl {


  //简单工厂，扩展性不好
  public void simpleFactory() {
    System.out.println("简单工厂模式：");
    Car car = (Car) new SimpleCarFactory().create();
    car.go();
    Plane plane = (Plane) new SimplePlaneFactory().create();
    plane.go();
    Broom broom = (Broom) new SimpleBroomFactory().create();
    broom.go();
  }

  //抽象工厂，针对的是产品族
  public void abstractFactory() {
    System.out.println("抽象工厂模式：");
    System.out.println("第一个产品族：");
    AbstractFactory modernFactory = new ModernAbstractFactory();
    Weapon modernWeapon = modernFactory.createWeapon();
    modernWeapon.shoot();
    Vehicle modernVehicle = modernFactory.createVehicle();
    modernVehicle.run();
    Food modernFood = modernFactory.createFood();
    modernFood.eat();

    System.out.println("第二个产品族：");
    AbstractFactory magicFactory = new MagicAbstractFactory();
    Weapon magicWeapon = magicFactory.createWeapon();
    magicWeapon.shoot();
    Vehicle magicVehicle = magicFactory.createVehicle();
    magicVehicle.run();
    Food magicFood = magicFactory.createFood();
    magicFood.eat();

  }


}
