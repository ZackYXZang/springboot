package com.example.designpattern.service.observer;

public class Mom implements Observer{

  @Override
  public void reaction() {
    feed();
  }

  public void feed() {
    System.out.println("mom feed ....");
  }
}
