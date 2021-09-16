package com.example.designpattern.service.factory.simpleFactory;

import com.example.designpattern.service.factory.simpleFactory.Movable;

public class Plane implements Movable {


  @Override
  public void go() {
    System.out.println("Plane run wuwuwu...");
  }
}
