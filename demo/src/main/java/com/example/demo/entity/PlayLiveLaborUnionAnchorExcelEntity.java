package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 为官签/公会主播
 */
@Data
public class PlayLiveLaborUnionAnchorExcelEntity {

  /**
   * 主播id
   */
//  @ExcelProperty(value = "userid")
  @ExcelProperty(index = 0)
  Integer anchorId;

  /**
   * 昵称
   */
//  @ExcelProperty(value = "nickname_blob")
  @ExcelProperty(index = 1)
  String nickName;

  /**
   * 封面图质量
   *
   */
//  @ExcelProperty(value = "封面图质量")
  @ExcelProperty(index = 2)
  String coverQuality;

  /**
   * 声音年龄
   *
   */
//  @ExcelProperty(value = "声音年龄")
  @ExcelProperty(index = 3)
  String voiceAge;

  /**
   * 特长
   *
   */
//  @ExcelProperty(value = "特长")
  @ExcelProperty(index = 4)
  String specialty;


//  @ExcelProperty(value = "治愈/陪伴")
  @ExcelProperty(index = 5)
  String cure;
//  @ExcelProperty(value = "甜美/可爱")
  @ExcelProperty(index = 6)
  String sweet;
//  @ExcelProperty(value = "知性/成熟/御姐")
  @ExcelProperty(index = 7)
  String zhixing;
//  @ExcelProperty(value = "性感/纯欲")
  @ExcelProperty(index = 8)
  String sexy;
//  @ExcelProperty(value = "温柔/轻熟/清新")
  @ExcelProperty(index = 9)
  String soft;
//  @ExcelProperty(value = "中性/大大咧咧")
  @ExcelProperty(index = 10)
  String neutral;
//  @ExcelProperty(value = "烟嗓/慵懒")
  @ExcelProperty(index = 11)
  String smoky;
//  @ExcelProperty(value = "少年/活力/酷")
  @ExcelProperty(index = 12)
  String young;
//  @ExcelProperty(value = "成熟大叔/故事性")
  @ExcelProperty(index = 13)
  String mature;
}
