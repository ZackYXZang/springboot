package com.example.designpattern.service.factory.abstractFactory;

public class Broom extends Vehicle implements Movable {


  @Override
  public void go() {
    System.out.println("Broom go shuashua...");
  }

  @Override
  void run() {
    System.out.println("Broom run shuashua...");
  }
}
