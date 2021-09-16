package com.example.designpattern.service.factory.simpleFactory;

import com.example.designpattern.service.factory.simpleFactory.Movable;

public class Broom implements Movable {


  @Override
  public void go() {
    System.out.println("Broom run shuashua...");
  }
}
