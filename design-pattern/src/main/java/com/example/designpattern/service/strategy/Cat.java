package com.example.designpattern.service.strategy;

import lombok.Data;

@Data
public class Cat {

  public Cat(int weight, int height) {
    this.weight = weight;
    this.height = height;
  }

  int weight;
  int height;

  @Override
  public String toString() {
    return "Cat{" +
        "weight=" + weight +
        ", height=" + height +
        '}';
  }
}
