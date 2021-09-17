package com.example.designpattern.service.adapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.stereotype.Service;

/**
 * 适配器模式
 */
@Service
public class AdapterServiceImpl {

  public void adapter() {
    /**
     * 在java中，用BufferedReader读取文件的时候，没有办法直接使用FileInputStream
     * 这里调用InputStreamReader作为适配器BufferedReader
     * InputStreamReader 连接着 BufferedReader 和 FileInputStream
     */
    try {
      FileInputStream fileInputStream = new FileInputStream("/test.txt");
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
      BufferedReader bufferedInputStream = new BufferedReader(inputStreamReader);
      String line = bufferedInputStream.readLine();
      while (line != null && !line.equals("")) {
        System.out.println(line);
      }
      bufferedInputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
