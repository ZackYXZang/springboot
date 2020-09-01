package com.example.bootjedis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-08-31-8:21 下午
 * @desc 数据管理excel导出实体
 **/
@Data
public class ChannelDataExcel extends BaseRowModel {

  /**
   * 产品
   */
  @ExcelProperty(value = "产品", index = 0)
  private String product;

  /**
   * 渠道
   */
  @ExcelProperty(value = "渠道", index = 1)
  private String channel;

  /**
   * 推广日期
   */
  @ExcelProperty(value = "推广日期", index = 2)
  private String popularizeDate;

  /**
   * 推广金额
   */
  @ExcelProperty(value = "推广金额", index = 3)
  private BigDecimal popularizeMoney;

  /**
   * 转化量
   */
  @ExcelProperty(value = "转化量", index = 4)
  private Long transferation;

  /**
   * 创建人
   */
  @ExcelProperty(value = "录入账号ID", index = 5)
  private String creator;

  /**
   * 录入日期
   */
  @ExcelProperty(value = "录入日期", index = 6)
  private String createDate;

}
