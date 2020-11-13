package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-11-12-4:30 下午
 * @desc excel对应实体
 **/
@Data
public class ExcelModelEntity extends BaseRowModel {

  @ExcelProperty(index = 9)
  private String userid;

  @ExcelProperty(index = 10)
  private String workid;

  @ExcelProperty(index =11)
  private String songid;

  @ExcelProperty(index = 12)
  private String mediaType;

  @ExcelProperty(index = 13)
  private Integer playType;
}
