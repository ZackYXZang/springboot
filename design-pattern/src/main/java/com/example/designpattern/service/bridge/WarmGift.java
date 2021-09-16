package com.example.designpattern.service.bridge;

public class WarmGift extends Gift {
  public WarmGift(GiftStyle giftStyle) {
    String style = giftStyle.getStyle();
    this.giftStyle = new GiftStyle(style);
  }

}
