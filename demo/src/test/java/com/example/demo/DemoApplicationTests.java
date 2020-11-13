package com.example.demo;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.example.demo.entity.ExcelModelEntity;
import com.example.demo.listener.ExcelListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class DemoApplicationTests {

  @Test
  void contextLoads() {
    read();
  }

  void read() {
    // 读取 excel 表格的路径
    String readPath = "C:\\Users\\oukele\\Desktop\\模拟数据.xlsx";
    String readPath1 = "/Users/yuxiangzang/Downloads/7日分发数据.xlsx";
    try {
      Sheet sheet = new Sheet(1,1, ExcelModelEntity.class);
      ExcelListener listener = new ExcelListener();
      EasyExcelFactory.readBySax(new FileInputStream(readPath1),sheet,listener);
      List<ExcelModelEntity> datas = listener.getDatas();

      //doSomething();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 返回 ExcelReader
   *
   * @param excel 需要解析的 Excel 文件
   * @param excelListener new ExcelListener()
   */
  private static ExcelReader getReader(MultipartFile excel, ExcelListener excelListener) {
    String filename = excel.getOriginalFilename();

    if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
      return null;
    }
    InputStream inputStream;

    try {
      inputStream = new BufferedInputStream(excel.getInputStream());

      return new ExcelReader(inputStream, null, excelListener, false);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

}
