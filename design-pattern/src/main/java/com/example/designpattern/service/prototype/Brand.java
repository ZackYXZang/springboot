package com.example.designpattern.service.prototype;

import lombok.Data;

/**
 * 浅克隆
 */
@Data
public class Brand {

  String brand;

  public Brand(String brand) {
    this.brand = brand;
  }

  @Override
  public String toString() {
    return "Brand{" +
        "brand='" + brand + '\'' +
        '}';
  }
}
