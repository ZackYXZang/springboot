package com.example.designpattern.service.factory.simpleFactory;

import com.example.designpattern.service.factory.simpleFactory.Movable;

public class Car implements Movable {


  @Override
  public void go() {
    System.out.println("Car run dididi...");
  }
}
