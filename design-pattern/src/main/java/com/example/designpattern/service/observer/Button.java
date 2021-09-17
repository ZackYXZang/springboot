package com.example.designpattern.service.observer;

import java.util.ArrayList;
import java.util.List;

public class Button {
  List<Observer> observers = new ArrayList<>();
  {
    observers.add(new Mom());
    observers.add(new Dad());
    observers.add(new Dog());
  }


  public void notifyObserver() {
    for (Observer o : observers) {
      o.reaction();
    }
  }
}
