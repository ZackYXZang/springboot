package com.example.designpattern.service.bridge;

public class Flower extends GiftStyle{

  public Flower(String style) {
    super("这是一束" + style + "花");
  }
}
