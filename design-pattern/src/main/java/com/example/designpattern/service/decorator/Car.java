package com.example.designpattern.service.decorator;

public class Car implements Vehicle{

  public Car() {
    System.out.println("this is a car");
  }

  @Override
  public void run() {
    System.out.println("car run.....");
  }
}
