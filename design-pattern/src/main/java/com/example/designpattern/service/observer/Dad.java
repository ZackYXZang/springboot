package com.example.designpattern.service.observer;

public class Dad implements Observer{

  @Override
  public void reaction() {
    hug();
  }

  public void hug() {
    System.out.println("dad hub ....");
  }
}
