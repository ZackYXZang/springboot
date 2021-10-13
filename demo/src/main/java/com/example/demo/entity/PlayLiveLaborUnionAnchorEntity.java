package com.example.demo.entity;

import java.util.List;
import lombok.Data;

/**
 * 为官签/公会主播
 */
@Data
public class PlayLiveLaborUnionAnchorEntity {

  /**
   * 主播id
   */
   Integer anchorId;

  /**
   * 昵称
   */
  String nickName;

  /**
   * 封面图质量
   *
   */
  Integer coverQuality = 0;

  /**
   * 声音年龄
   *
   */
  Integer voiceAge = 0;

  /**
   * 特长
   *
   */
  Integer specialty = 0;

  /**
   * 声音风格
   *
   */
  List<Integer> voiceStyle;
}
