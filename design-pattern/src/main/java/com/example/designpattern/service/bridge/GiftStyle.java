package com.example.designpattern.service.bridge;

import lombok.Data;

@Data
public class GiftStyle {

  String style = "风格";

  public GiftStyle(String style) {
    this.style = style + this.style;
  }
}
