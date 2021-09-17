package com.example.designpattern.service.builder;

public interface HouseBuilder {

  HouseBuilder buildFloor();
  HouseBuilder buildWindow();
  HouseBuilder buildDoor();
  House buildHouse();

}
