package com.example.designpattern.service.builder;

public class Floor {

  int height;
  int length;
  int width;

  public Floor(int height, int length, int width) {
    this.height = height;
    this.length = length;
    this.width = width;
  }

  @Override
  public String toString() {
    return "Door{" +
        "height=" + height +
        ", length=" + length +
        ", width=" + width +
        '}';
  }
}
