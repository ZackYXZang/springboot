package com.example.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.example.demo.entity.ExcelModelEntity;
import com.example.demo.entity.PlayLiveLaborUnionAnchorExcelEntity;
import com.example.demo.listener.ExcelListener;
import java.io.BufferedInputStream;
import java.io.File;
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
    File file = new File("/Users/yuxiangzang/Downloads/音频主播标签标准10.9.xls");
    try {
      EasyExcel.read(new FileInputStream(file), PlayLiveLaborUnionAnchorExcelEntity.class, new ExcelListener()).sheet()
          .doRead();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


}
