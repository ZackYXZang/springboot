package com.example.designpattern.service.observer;

public class Dog implements Observer{

  @Override
  public void reaction() {
    bark();
  }

  public void bark() {
    System.out.println("dog bark ....");
  }
}
