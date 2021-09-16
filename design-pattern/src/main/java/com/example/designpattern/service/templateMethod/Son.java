package com.example.designpattern.service.templateMethod;

public class Son extends Father {

  @Override
  void eat() {
    System.out.println("son eat");
  }

  @Override
  void sleep() {
    System.out.println("son sleep");
  }
}
