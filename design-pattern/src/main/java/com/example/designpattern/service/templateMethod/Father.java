package com.example.designpattern.service.templateMethod;

public abstract class Father {

  public void father() {
    eat();
    sleep();
  }

  abstract void eat();
  abstract void sleep();
}
