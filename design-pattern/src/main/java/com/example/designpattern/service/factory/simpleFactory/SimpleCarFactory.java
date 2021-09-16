package com.example.designpattern.service.factory.simpleFactory;

public class SimpleCarFactory implements SimpleAbstractFactory{

  @Override
  public Movable create() {
    return new Car();
  }
}
