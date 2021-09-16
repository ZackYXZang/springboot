package com.example.designpattern.service.factory.simpleFactory;

public class SimpleBroomFactory implements SimpleAbstractFactory{

  @Override
  public Movable create() {
    return new Broom();
  }
}
