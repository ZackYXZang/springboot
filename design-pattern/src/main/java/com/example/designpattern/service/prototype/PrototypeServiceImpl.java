package com.example.designpattern.service.prototype;

import org.springframework.stereotype.Service;

/**
 * 原型模式
 * java自带原型模式，一般用于一个对象的属性已经确定，需要产生更多相同对象的时候
 * 需要实现标记型接口Cloneable
 */
@Service
public class PrototypeServiceImpl {

  public void prototype() throws Exception{
    //浅克隆：只能拷贝出新的Car对象，无法拷贝出Car中的Brand(引用)
    System.out.println("浅克隆 start");
    Car car1 = new Car();
    Car car2 = (Car)car1.clone();
    System.out.println(car2.color + "" + car2.price);
    System.out.println(car2.brand);

    System.out.println(car1.brand == car2.brand);
    car1.getBrand().setBrand("Porsche");
    System.out.println(car2.getBrand().getBrand());

    System.out.println("浅克隆 end");


    //深克隆：拷贝出新的Car对象的同时，也拷贝出Car中的Brand(引用)
    System.out.println("深克隆 start");
    NewCar newCar1 = new NewCar();
    NewCar newCar2 = (NewCar)newCar1.clone();
    System.out.println(newCar1.color + "" + newCar2.price);
    System.out.println(newCar2.newBrand);

    System.out.println(newCar1.newBrand == newCar2.newBrand);
    newCar1.getNewBrand().setNewBrand("Porsche");
    System.out.println(newCar2.getNewBrand().getNewBrand());

    System.out.println("深克隆 end");

  }
}
