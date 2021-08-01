package com.example.bootjedis.pojo;

import lombok.Data;

/**
 * 顶部推荐栏KTV返回值
 */
@Data
public class KtvUserResEntity {

  // 用户id
  Integer userid;

  //用户所在房间的mode
  Integer playMode;

  //推荐理由
  String recReason;

  // 房间id
  Integer roomId;

  Integer score;

  Integer priority;

  Integer wieght = 0;

  Integer gender;



}
