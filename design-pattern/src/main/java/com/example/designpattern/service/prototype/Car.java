package com.example.designpattern.service.prototype;

import lombok.Data;

/**
 * 浅克隆
 */
@Data
public class Car implements Cloneable{

  String color = "yellow";

  double price = 10000;

  Brand brand = new Brand("BMW");


  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return "Car{" +
        "color='" + color + '\'' +
        ", price=" + price +
        ", brand=" + brand +
        '}';
  }
}
