package com.example.bootjedis.pojo;

import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-07-21-10:53 上午
 * @desc 首页-直播Tab业务传参实体
 **/
@Data
public class LiveCondition {

  public LiveCondition() {

  }

  /**
   * 用户名
   */
  String userCode;

  /**
   * 主键
   */
  String id;

  /**
   * 曝光位置
   */
  Integer position;

  /**
   * 主播ID
   */
  String anchorId;

  /**
   * 权重
   */
  Integer weight;

  /**
   * 开始时间
   */
  String startTime;

  /**
   * 结束时间
   */
  String endTime;



}
