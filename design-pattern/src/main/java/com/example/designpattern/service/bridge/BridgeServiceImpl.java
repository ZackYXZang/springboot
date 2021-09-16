package com.example.designpattern.service.bridge;

import org.springframework.stereotype.Service;

/**
 * 桥接模式
 * 举例：送礼物
 * 礼物分为温暖的礼物，冷酷的礼物
 * 礼物可以是花，也可以是书
 *
 */
@Service
public class BridgeServiceImpl {

  public void bridge() {
    Gift gift1 = new WarmGift(new Flower("花"));
    Gift gift2 = new ColdGift(new Book("书"));
    System.out.println(gift1.giftStyle);
    System.out.println(gift2.giftStyle);
  }
}
