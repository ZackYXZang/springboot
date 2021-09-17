package com.example.designpattern.service.builder;

import org.springframework.stereotype.Service;

/**
 * 构造模式
 * 分离复杂对象的构建和表示
 * 同样的构建过程可以创建不同的表示
 */
@Service
public class BuilderServiceImpl {

  public void builder() {
    HouseBuilder builder = new HouseBuilderImpl();
    House house = builder.buildDoor().buildFloor().buildWindow().buildHouse();
    System.out.println(house);
  }
}
