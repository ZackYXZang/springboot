package com.example.bootjedis.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.excel.ChannelDataExcel;
import com.example.bootjedis.mapper.ChannelDataMapper;
import com.example.bootjedis.pojo.ChannelData;
import com.example.bootjedis.pojo.ChatInputParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuxiangzang
 * @create 2020-05-29-6:39 下午
 * @desc
 **/
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ChannelDataMapper channelDataMapper;

  @RequestMapping("/test")
  public String getString() {
    String res = userService.getString("a");
    return res;
  }
  @RequestMapping(value = "/test1", produces = "application/json;charset=UTF-8", method =
      RequestMethod.GET)
  public void getString1(){


    ExcelWriter writer = null;
    OutputStream out = null;

    try {
      File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
      String desktopPath = desktopDir.getAbsolutePath();
      System.out.println(desktopPath);
      String fileName = "数据管理表";
      out = new FileOutputStream(desktopPath + "/Desktop/" + fileName + ".xlsx");
      writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
      Sheet sheet = new Sheet(1, 0, ChannelDataExcel.class);
      List<ChannelData> channelData = channelDataMapper
          .selectAll("唱吧", null, null, "admin", 1, 30);


      List<ChannelDataExcel> list = new ArrayList<>();
      channelData.forEach(channelData1 -> {
        ChannelDataExcel channelDataExcel = new ChannelDataExcel();
        channelDataExcel.setProduct(channelData1.getProduct());
        channelDataExcel.setChannel(channelData1.getChannel());
        java.sql.Date popularizeDate = new java.sql.Date(channelData1.getPopularizeDate().getTime());
        channelDataExcel.setPopularizeDate(popularizeDate.toString());
        channelDataExcel.setPopularizeMoney(channelData1.getPopularizeMoney());
        channelDataExcel.setTransferation(channelData1.getTransferation());
        channelDataExcel.setCreator(channelData1.getCreator());
        java.sql.Date createDate = new java.sql.Date(channelData1.getCreateDate().getTime());
        channelDataExcel.setCreateDate(createDate.toString());
        list.add(channelDataExcel);
      });
      //设置自适应宽度
      sheet.setAutoWidth(Boolean.TRUE);
      // 第一个 sheet 名称
      sheet.setSheetName("数据信息");
      writer.write(list, sheet);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        writer.finish();
      }
      if (out != null) {
        try {
          out.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }


}
