package com.example.designpattern.service.flyweight;

import java.util.ArrayList;
import java.util.List;

public class FlyWeightPool {

  List<FlyWeight> flyWeights = new ArrayList<>();
  {
    for (int i = 0; i < 100; i++) {
      flyWeights.add(new FlyWeight());
    }
  }

  public FlyWeight getFlyWeight() {
    for (int i = 0; i < flyWeights.size(); i++) {
      FlyWeight flyWeight = flyWeights.get(i);
      if (!flyWeight.used) {
        return flyWeight;
      }
    }
    return new FlyWeight();
  }
}
