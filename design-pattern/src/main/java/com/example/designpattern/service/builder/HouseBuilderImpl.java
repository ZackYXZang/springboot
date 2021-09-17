package com.example.designpattern.service.builder;

public class HouseBuilderImpl implements HouseBuilder{

  House house = new House();


  @Override
  public HouseBuilder buildFloor() {
    house.floor = new Floor(1,1,1);
    return this;
  }

  @Override
  public HouseBuilder buildWindow() {
    house.window = new Window(2,2,2);
    return this;
  }

  @Override
  public HouseBuilder buildDoor() {
    house.door = new Door(3,3,3);
    return this;
  }

  @Override
  public House buildHouse() {
    return house;
  }
}
