package com.example.designpattern.service.flyweight;

public class FlyWeight {

  long no = System.currentTimeMillis();

  boolean used = false;

  @Override
  public String toString() {
    return "FlyWeight{" +
        "no=" + no +
        '}';
  }
}
