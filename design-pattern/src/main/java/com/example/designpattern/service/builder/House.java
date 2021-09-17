package com.example.designpattern.service.builder;

import lombok.Data;

@Data
public class House {

  Door door;
  Floor floor;
  Window window;

  @Override
  public String toString() {
    return "House{" +
        "door=" + door +
        ", floor=" + floor +
        ", window=" + window +
        '}';
  }
}
