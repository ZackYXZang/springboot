package com.example.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.demo.entity.ExcelModelEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuxiangzang
 * @create 2020-11-12-4:39 下午
 * @desc 监听类
 **/
public class ExcelListener extends AnalysisEventListener<ExcelModelEntity> {

  private List<ExcelModelEntity> datas = new ArrayList<>();
  @Override
  public void invoke(ExcelModelEntity data, AnalysisContext context) {
    datas.add(data);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
  }

  public List<ExcelModelEntity> getDatas() {
    return datas;
  }
}
