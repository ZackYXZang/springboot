package com.example.designpattern.service.prototype;

import lombok.Data;

/**
 * 深克隆
 */
@Data
public class NewCar implements Cloneable{

  String color = "yellow";;

  double price = 10000;

  NewBrand newBrand = new NewBrand("BMW");

  @Override
  protected Object clone() throws CloneNotSupportedException {
    NewCar newCar = (NewCar) super.clone();
    newCar.setNewBrand((NewBrand) newBrand.clone());
    return newCar;
  }

  @Override
  public String toString() {
    return "Car{" +
        "color='" + color + '\'' +
        ", price=" + price +
        ", brand=" + newBrand +
        '}';
  }
}
