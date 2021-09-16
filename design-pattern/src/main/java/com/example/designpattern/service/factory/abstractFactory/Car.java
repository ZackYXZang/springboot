package com.example.designpattern.service.factory.abstractFactory;

public class Car extends Vehicle implements Movable {


  @Override
  public void go() {
    System.out.println("Car go dididi...");
  }

  @Override
  void run() {
    System.out.println("Car go dididi...");
  }
}
