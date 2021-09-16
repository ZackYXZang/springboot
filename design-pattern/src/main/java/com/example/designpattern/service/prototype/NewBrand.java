package com.example.designpattern.service.prototype;

import lombok.Data;

/**
 * 深克隆
 */
@Data
public class NewBrand implements Cloneable{

  String newBrand;

  public NewBrand(String newBrand) {
    this.newBrand = newBrand;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return "Brand{" +
        "brand='" + newBrand + '\'' +
        '}';
  }
}
