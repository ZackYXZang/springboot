package com.example.bootjedis.pojo;

import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-08-13-10:48 上午
 * @desc
 **/
@Data
public class ChatInputParamNew {

  // 用户id
  private Integer userid;
  // 返回数据数
  private int num;
  // 分流配置
  private String abgroup;

  /**
   * 性别
   * 0：女
   * 1：男
   * 2：未知
   */
  private int gender;

  private int signal;
}
