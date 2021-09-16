package com.example.designpattern.service.strategy;

public class CatWeightComparator implements AnimalComparator<Cat>{

  @Override
  public int compare(Cat o1, Cat o2) {
    if (o1.getWeight() < o2.getHeight()) {
      return -1;
    } else if (o1.getWeight() > o2.getHeight()) {
      return 1;
    } else {
      return 0;
    }
  }
}
