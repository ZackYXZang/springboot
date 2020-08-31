package com.example.bootjedis.pojo;

import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-08-18-6:32 下午
 * @desc 伴奏返回值实体
 **/
@Data
public class AccompanyRecReturnEntity {

  /**
   * 歌曲id
   */
  private String songId;

  /**
   * 推荐理由
   */
  private String recReason;

  /**
   * 池子标识
   */
  private int poolNum;

  /**
   * 版本
   */
  private String version;

}
