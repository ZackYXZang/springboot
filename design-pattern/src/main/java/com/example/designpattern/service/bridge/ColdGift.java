package com.example.designpattern.service.bridge;

public class ColdGift extends Gift{
  public ColdGift(GiftStyle giftStyle) {
    String style = giftStyle.getStyle();
    this.giftStyle = new GiftStyle(style);
  }
}
