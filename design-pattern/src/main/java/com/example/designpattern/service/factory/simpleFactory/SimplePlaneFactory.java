package com.example.designpattern.service.factory.simpleFactory;

public class SimplePlaneFactory implements SimpleAbstractFactory{

  @Override
  public Movable create() {
    return new Plane();
  }
}
