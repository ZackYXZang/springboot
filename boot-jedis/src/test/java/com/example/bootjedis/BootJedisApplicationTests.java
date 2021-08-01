package com.example.bootjedis;

import com.alibaba.fastjson.JSONObject;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.User;
import com.example.bootjedis.utils.LocalDateUtils;
import com.example.bootjedis.utils.TimeUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

@SpringBootTest
class BootJedisApplicationTests {

  @Autowired
  private JedisPool jedisPool;

  @Autowired
  private UserService userService;

  @Autowired
  private RestTemplate restTemplate;

  private String keyPre = "changba:%s";


  public void seekPoint() {
    String sourcePath = "/Users/yuxiangzang/Desktop/Read_File/a.data";
    String target = "1274067353#MXX#179701271#MXX#来跟个风一首（如果爱还在）与各位歌友们共享！希望都不要错过对你最好的人！共勉！❤️❤️❤️#MXX#660fd2c5-33e7-4608-811c-104b94b6acb5#MXX#http://qiniuuwmp3.changba.com/#MXX#1#MXX#如果爱还在";
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(sourcePath, "r");
      Long pos = 0L;
      randomAccessFile.seek(pos);
      long length = randomAccessFile.length();
      int count = 1;
      while (pos < length) {
        String string = new String(randomAccessFile.readLine().getBytes(), "UTF-8");
        pos = randomAccessFile.getFilePointer();
        if (count == 4001) {
          System.out.println(string);
          System.out.println(pos);
        }
        count++;
      }


    } catch (IOException e){
      e.printStackTrace();
    }
  }

  @Test
  void contextLoads() {
//    seekPoint();
    TimeUtil timeUtil = new TimeUtil();
    String sourcePath = "/Users/yuxiangzang/Desktop/Read_File/a.data";

    String destPath =
        sourcePath.substring(sourcePath.lastIndexOf("/"), sourcePath.lastIndexOf(".")) + "_" + "missionName" + "_"
            + "missionDate" + "_index" + sourcePath.substring(sourcePath.lastIndexOf("."));
    System.out.println(destPath);

    String path = System.getProperty("user.dir");
    path = path + "/2020-07-19" + destPath;
    System.out.println(path);
//    String destPath = sourcePath.substring(0, sourcePath.lastIndexOf(".")) + "_" + "missionName" + "_" + "missionDate" + sourcePath.substring(sourcePath.lastIndexOf("."));
//    generateIndexFile(sourcePath, destPath);
//    readLine(sourcePath, 0, Long.MAX_VALUE);
//    System.out.println(timeUtil.getTimeAndReset());
//    readLineByNum(sourcePath, 0L, Long.MAX_VALUE);
//    System.out.println(timeUtil.getTimeAndReset());

//    List<String> positionPointer = findPositionPointer(
//        "/Users/yuxiangzang/Desktop/Read_File/a_A_2021-07-19_index.data", 4001L);
//        System.out.println(timeUtil.getTimeAndReset());
//    System.out.println(positionPointer.get(0));
//    System.out.println(positionPointer.get(1));
//
//    List<String> list = readLinesWithOffsetV2(sourcePath, 10,
//        Long.valueOf(positionPointer.get(1)), 4223, 4001);
//    System.out.println(timeUtil.getTimeAndReset());
//    System.out.println(list.get(0));
//    System.out.println(list.get(list.size() - 1));
//    System.out.println(list.size());


  }

  public static List<String> readLinesWithOffsetV2(String filePath, Integer lines, Long offset, Integer originalStart, Integer realStart){

    lines = lines + originalStart - realStart;
    byte[] result = null;
    try(RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")){
      if(offset != null && offset > 0){
        randomAccessFile.seek(offset);
      }
      FileChannel channel = randomAccessFile.getChannel();
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
      int readLine = 0;
      while (channel.read(byteBuffer) != -1){
        byteBuffer.clear();
        byte[] array = byteBuffer.array();
        readLine += findLineBreakNum(array);
        result = ArrayUtils.addAll(result, array);
        if(readLine >= lines){
          String[] split = new String(result, StandardCharsets.UTF_8).split(System.lineSeparator());
          String[] strs = Arrays.copyOfRange(split, originalStart - realStart, lines);
          return Arrays.asList(strs);
        }
      }
    }catch (IOException e){
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public static int findLineBreakNum(byte[] array){
    int num = 0;
    // \r 十进制ASCII代码是13   \n 十进制ASCII代码是10
    for (int i = array.length - 1; i >= 0; i--) {
      if(array[i]==10)
        num ++;
    }
    return num;
  }

  public static List<String> findPositionPointer(String path, Long start) {

    List<String> result = new ArrayList<String>();
    long pointer = (start / 500) * 500 + 1;
//    long pointer = l * 500 +1;
    //文件总行数
    try {
      FileReader fileReader = new FileReader(path);
      LineNumberReader lnr = new LineNumberReader(fileReader);
      String line = lnr.readLine();
      while (line != null) {
        if (line.contains(String.valueOf(pointer))) {
          String[] split = line.split(",");
          if (split[0].equals(String.valueOf(pointer))) {
            return Arrays.asList(split);
          }
        }
        line = lnr.readLine();
      }
      lnr.close();
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }





  public void generateIndexFile(String sourcePath, String destPath) {
    File file = new File(sourcePath);
    long count = 1;
    List<String> result = new ArrayList<>();
    File file1 = new File(destPath);
    if (file1.exists()) {
      file1.delete();
    }

    try {
      FileReader fileReader = new FileReader(sourcePath);
      LineNumberReader lnr = new LineNumberReader(fileReader);
      String line = lnr.readLine();
      StringBuffer sb = new StringBuffer();
      long byteIndex = 0;
      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        if (count % 500 == 0) {
          Long tempCount = count + 1;
          byteIndex += sb.toString().getBytes().length;
          result.add(tempCount + "," + byteIndex);
          sb.setLength(0);
        }
        count++;
        line = lnr.readLine();
      }
      lnr.close();
      fileReader.close();

      BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
      for (String l : result) {
        writer.write(l + "\r\n");
      }
      writer.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public static List<String> readLineByNum(String path, Long start, Long end) {

    List<String> result = new ArrayList<String>();
    //文件总行数
    try {
      FileReader fileReader = new FileReader(path);
      LineNumberReader lnr = new LineNumberReader(fileReader);
      String line = lnr.readLine();
      while (line != null) {
        if (lnr.getLineNumber() >= start && lnr.getLineNumber() < end) {
//          result.add(line);
        }
        if (lnr.getLineNumber() >= end) {
          break;
        }
        line = lnr.readLine();
      }
      lnr.close();
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
  /**
   * 读取文件正着数N行 输出的时候请顺序输出 todo: 每次将pos缓存起来，同步
   *
   * @param path
   * @param num
   * @return
   */
  public List<String> readLine(String path, long pos, long num) {
    List<String> result = new ArrayList<String>();
    File file = new File(path);
    long count = 0;//定义行数
    if (!file.exists() || file.isDirectory() || !file.canRead()) {
      return null;
    }
    RandomAccessFile fileRead = null;
    try {

      fileRead = new RandomAccessFile(file, "r"); //用读模式
      long length = fileRead.length();//获得文件长度
      if (length == 0L || num <= 0) {//文件内容为空
        return result;
      } else {
        // TODO: 2021/7/15 应该从redis或者内存中获取，要考虑到并发
        if (pos >= length) {
          return result;
        }

        String lineFirst = new String(fileRead.readLine().getBytes(), "UTF-8");
//        result.add(lineFirst);
        count++;
        pos = fileRead.getFilePointer();
        if (count == num) {
          //保存pos到缓存
          return result;
        }
        while (pos < length) {
          //找到第一行有文字的行，以防前面几行都是回车
          pos = count == 1 ? pos - 1 : pos + 1;
          fileRead.seek(pos);
          if (fileRead.readByte() == '\n') {//有换行符，则读取
            String line = new String(fileRead.readLine().getBytes("ISO-8859-1"), "UTF-8");
//            result.add(line);
            count++;
            if (count == num) {//满足指定行数 退出。
              break;
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fileRead != null) {
        try {
          // 关闭资源
          fileRead.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    //保存pos到缓存
    return result;
  }


  /**
   * 读取文件最后N行 输出的时候请逆序输出 如果最后一行是空/回车，不会返回
   *
   * @param path
   * @param numRead
   * @return List<String>
   */
  public static List<String> readLastNLine(String path, long numRead) {
    List<String> result = new ArrayList<String>();
    File file = new File(path);
    long count = 0;//定义行数
    if (!file.exists() || file.isDirectory() || !file.canRead()) {
      return null;
    }
    RandomAccessFile fileRead = null;
    try {
      fileRead = new RandomAccessFile(file, "r"); //用读模式
      long length = fileRead.length();//获得文件长度
      if (length == 0L) {//文件内容为空
        return result;
      } else {
        // 开始位置
        long pos = length - 1;
        while (pos > 0) {
          pos--;
          fileRead.seek(pos); // 开始读取
          if (fileRead.readByte() == '\n') {//有换行符，则读取
            String line = new String(fileRead.readLine().getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(line);
            result.add(line);
            count++;
            System.out.println("pointer = " + fileRead.getFilePointer());
            if (count == numRead) {//满足指定行数 退出。
              System.out.println("pointer = " + fileRead.getFilePointer());
              break;
            }
          }
        }

        if (pos == 0) {
          fileRead.seek(0);
          result.add(new String(fileRead.readLine().getBytes("ISO-8859-1"), "UTF-8"));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fileRead != null) {
        try {
          // 关闭资源
          fileRead.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return result;
  }



  public static void readFileFileChannel(String pathname) {
    File file = new File(pathname);
    FileInputStream fileInputStream = null;
    List<String> list = new ArrayList<>();
    FileChannel fileChannel = null;

    try {
      fileInputStream = new FileInputStream(file);
      fileChannel = fileInputStream.getChannel();

      int capacity = 1*1024*1024;//1M
      ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
      StringBuffer buffer = new StringBuffer();
      while( fileChannel.read(byteBuffer) != -1) {
        //读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
        byteBuffer.clear();
        byte[] bytes = byteBuffer.array();
        String str = new String(bytes);
        //System.out.println(str);
        //处理字符串,并不会将字符串保存真正保存到内存中
        // 这里简单模拟下处理操作.
        list.add(str);
        buffer.append(str.substring(0,1));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      //TODO close处理.
      try {
        if (fileInputStream != null) {
          fileInputStream.close();
        }
        if (fileChannel != null) {
          fileChannel.close();
        }


      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public List<String> readFileByBufferedReader(String pathname, long start, long end) {
    List<String> list = new ArrayList<>();
    File file = new File(pathname);
    BufferedReader reader = null;
    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    try {
      //使用BufferedReader,每次读入1M数据.减少IO.如：
      fileInputStream = new FileInputStream(file);
      inputStreamReader = new InputStreamReader(fileInputStream, Charset.defaultCharset());
      reader = new BufferedReader(inputStreamReader, 1 * 1024 * 1024);
      String tempString = null;
      StringBuffer buffer = new StringBuffer();
      int count = 1;
      while ((tempString = reader.readLine()) != null ) {
        //System.out.println(tempString);
        //处理字符串,并不会将字符串保存真正保存到内存中
        // 这里简单模拟下处理操作.
        if (count >= start && count < end) {
          list.add(tempString);
        }
        count++;
      }
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileInputStream != null) {
          fileInputStream.close();
        }
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
        if (reader != null) {
          reader.close();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }





  public static List<String> readLineByNum(String txtPath, int lineNum, int end)
      throws IOException {
    List<String> result = new ArrayList<>();
    //文件总行数
    long count1 = Files.lines(Paths.get(txtPath)).count();
    LineNumberReader lnr = new LineNumberReader(new FileReader(txtPath));
    String line = lnr.readLine();
    while (line != null) {
      if (lnr.getLineNumber() >= lineNum && lnr.getLineNumber() <= count1) {
        result.add(line);
      }
      line = lnr.readLine();
    }
    return result;
  }


  public static boolean checkObjAllFieldsIsNull(Object object) {
    if (null == object) {
      return true;
    }

    try {
      for (Field f : object.getClass().getDeclaredFields()) {
        if (f.get(object) == null || StringUtils.isEmpty(f.get(object).toString())) {
          return false;
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return true;
  }


  public void getNewColumnFeature(String type, String columnFeature, List<String> result,
      JSONObject jsonObject) {
    if (type.equals("un") || type.equals("list") || type.equals("timediff")) {
      result.add(columnFeature);
    } else if (type.equals("enum") || type.equals("kv")) {
      String[] enumList = jsonObject.getString("list").split(";");
      for (String num : enumList) {
        result.add(columnFeature + "_" + num);
      }
    }
    return;
  }


  public List<String> getRTSelectedDs() {
    try (Jedis jedis = jedisPool.getResource()) {
      List<String> result = new ArrayList<>();
      Set<String> filed = jedis.zrange("aaa", 0, -1);
      result.addAll(filed);
      return result;
    }
  }

  public void test2() {
    DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String startTimeStr1 = "2020-09-07 20:30:00";
    String endTimeStr1 = "2020-09-07 22:00:00";
    Date date = new Date();
    try {
      Date startTime1 = format1.parse(startTimeStr1);
      Date endTime1 = format1.parse(endTimeStr1);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    long nowUnixSecondTime = LocalDateUtils.getUnixSecondTime(LocalDateTime.now().minusDays(3));
    System.out.println(nowUnixSecondTime);

//    String a = "a";
//    String[] split= a.split(",");
//
//
//    List<String> list = Arrays.asList(split);
//
//    for (int i = 1; i < list.size(); i++) {
//
//
//    }
//
//    for (int i = 0; i < 39; i++) {
//      list.add(i + "");
//    }

//
//    List<String> result = new ArrayList<>();
//    int count = 0;
//    String tempResult = "";
//    for (String l : list) {
//      if (count >= 20) {
//        result.add(tempResult);
//        tempResult = l;
//        count = 1;
//        continue;
//      }
//      tempResult = tempResult + l;
//      count++;
//    }
//    if (!StringUtils.isEmpty(tempResult)) {
//      result.add(tempResult);
//    }
//
//    System.out.println(result);

  }


  @Test
  public static void main(String[] args) {
//    /** 初始化集合类*/
//    ArrayList<TestObj> list = new ArrayList<>();
//    for (int i = 0; i < 100; i++) {
//      list.add(new TestObj(i));
//    }
//
//    /** 遍历时删除元素*/
//    for (TestObj obj : list) {
//      if (obj.getValue() < 10) {
//        /** 这里会抛出ConcurrentModificationException*/
//        list.remove(obj);
//      }
//    }
//    Scanner scan = new Scanner(System.in);
    // 从键盘接收数据

    // next方式接收字符串

    int i = 100;
    double j = ((double) i * 2) / 6;
    DecimalFormat df = new DecimalFormat("0.00");
    System.out.println(df.format(j));
  }


  public void test1() {

    String element = "test,1,1";
    element =
        element.substring(0, element.length() - 2) + ",0" + element.substring(element.length() - 2);
    System.out.println(element);
    //0否，1是
    List<Tuple> list = new ArrayList<>();
    String element1 = "a,1,0";
    double score1 = 10.00;
    Tuple tuple1 = new Tuple(element1, score1);

    String element2 = "b,1,1";
    double score2 = 13.00;
    Tuple tuple2 = new Tuple(element2, score2);

    String element3 = "c,1,0";
    double score3 = 12.00;
    Tuple tuple3 = new Tuple(element3, score3);

    String element4 = "d,1,1";
    double score4 = 10.00;
    Tuple tuple4 = new Tuple(element4, score4);

    list.add(tuple2);
    list.add(tuple1);
    list.add(tuple3);
    list.add(tuple4);

    Collections.sort(list, new Comparator<Tuple>() {
      @Override
      public int compare(Tuple o1, Tuple o2) {
        String element1 = o1.getElement();
        String element2 = o2.getElement();
        if (element1.trim().charAt(element1.trim().length() - 1) == element2.trim()
            .charAt(element2.trim().length() - 1)) {
          double score1 = o1.getScore();
          double score2 = o2.getScore();
          if (score1 > score2) {
            return 1;
          } else if (score1 < score2) {
            return -1;
          }
          return 0;
        }

        if ((element1.trim().charAt(element1.trim().length() - 1) == '1') && (
            element2.trim().charAt(element2.trim().length() - 1) == '0')) {
          return -1;
        }

        if ((element1.trim().charAt(element1.trim().length() - 1) == '0') && (
            element2.trim().charAt(element2.trim().length() - 1) == '1')) {
          return 1;
        }

        return 0;
      }
    });

    //bdac, 13,10,10,12
    //dbac, 10,13,10,12
    System.out.println(list.get(0).getElement());
    System.out.println(list.get(0).getScore());
    System.out.println(list.get(1).getElement());
    System.out.println(list.get(1).getScore());
    System.out.println(list.get(2).getElement());
    System.out.println(list.get(2).getScore());
    System.out.println(list.get(3).getElement());
    System.out.println(list.get(3).getScore());

    Collections.sort(list, new Comparator<Tuple>() {
      @Override
      public int compare(Tuple o1, Tuple o2) {
        return 0;
      }
    });

  }


  public void test(User user) {

    List<String> list1 = new ArrayList<>();
    list1.add("a");
    list1.add("b");
    list1.add("c");
    list1.add("d");
    list1.add("e");

    List<String> list2 = new ArrayList<>();
    list2.add("a");
    list2.add("b");
    list2.add("c");
    list2.add("d");
    list2.add("e");
    list2.add("f");

    for (int i = 0; i < list1.size(); i++) {
      String temp1 = list1.get(i);
      for (int j = 0; j < list2.size(); j++) {
        String temp2 = list2.get(j);
        System.out.println(temp1 + temp2);
//        if (temp2.equals("b")) {
//          continue;
//        }
        list1.remove(temp1);
        list2.remove(temp2);
        i--;
        j--;
        break;
      }
    }
    System.out.println(list1);
    System.out.println(list2);

//    try (Jedis jedis = jedisPool.getResource()) {
//    Map<String, String> map = new HashMap<>();
//    map.put("a","1");
//    map.put("b","2");
//    map.put("c","3");
//    map.remove("c");
//    System.out.println(map);

//
//      long nowUnixSecondTime = LocalDateUtils.getUnixSecondTime(LocalDateTime.now());
//      System.out.println(nowUnixSecondTime);
//      String hour = LocalDateUtils.getNowTimeStr(LocalDateUtils.HH);
//      System.out.println(hour);

//      List<String> list = new ArrayList<>();
//      list.add("a1");
//      list.remove(0);
//      System.out.println(list.toString());

//      for (String s : list) {
//        s = s.substring(0, s.length() - 1);
//        System.out.println(s);
//      }
//      System.out.println(list.toString());

//      long nowUnixSecondTime = LocalDateUtils.getUnixSecondTime(LocalDateTime.now());
//      System.out.println("时间long格式 = " + nowUnixSecondTime);
//      System.out.println("时间格式 = " + LocalDateTime.now());
//      jedis.zadd("user:follow:1225210", nowUnixSecondTime,"888");
//      Double zscore = jedis.zscore("user:follow:1225210", "888");
//      final long round = Math.round(zscore);
//      System.out.println("读取值 = " + round);
//      long nowUnixSecondTime1 = LocalDateUtils.getUnixSecondTime(LocalDateTime.now());
//      System.out.println("新时间long格式 = " + nowUnixSecondTime1);
//      System.out.println("新时间格式 = " + LocalDateTime.now());
//
//      long result = nowUnixSecondTime1 - round;
//      System.out.println("result" + result);

//      long nowUnixSecondTime = LocalDateUtils.getUnixSecondTime(LocalDateTime.now());
//      System.out.println("时间long格式 = " + nowUnixSecondTime);
//      long nowUnixSecondTime1 = LocalDateUtils.getUnixSecondTime(LocalDateTime.now().plusSeconds(30));
//      System.out.println("新时间long格式 = " + nowUnixSecondTime1);
//      long result = nowUnixSecondTime1 - nowUnixSecondTime;
//      System.out.println(result);
//      LocalDateTime.now().minusSeconds(30);

//      List<LiveCondition> list = new ArrayList<>();
//      LiveCondition entity = new LiveCondition();
//      entity.setAnchorId("a");
//      entity.setId("1");
//      entity.setUserCode("a");
//      entity.setWeight(2);
//      entity.setPosition(1);
//      entity.setStartTime("07-22 10:00");
//      entity.setEndTime("07-22 12:00");
//      list.add(entity);
//
//      LiveCondition entity1 = new LiveCondition();
//      entity1.setAnchorId("b");
//      entity1.setId("1");
//      entity1.setUserCode("a");
//      entity1.setWeight(2);
//      entity1.setPosition(1);
//      entity1.setStartTime("07-22 10:00");
//      entity1.setEndTime("07-22 12:00");
//      list.add(entity1);
//
//      try {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //HTTP请求
////        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(postParameters,
////            headers);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
//        String url = "http://127.0.0.1:9094/livesystem/live/test?useridPairs=123";
//        ResponseEntity<String> response = restTemplate
//            .exchange(url, HttpMethod.GET,
//                requestEntity, String.class);
//        String s = response.getBody();
//        System.out.println("s");
//        if (Strings.isNullOrEmpty(s)) {
//          System.out.println("错误");
//          return;
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//        System.out.println("错误");
//        return;
//      }

//    }
  }

  public Date double2Date(Double d) {
    Date t;

    Calendar base = Calendar.getInstance();
    SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //Delphi的日期类型从1899-12-30开始

    base.set(1899, 11, 30, 0, 0, 0);
    base.add(Calendar.DATE, d.intValue());
    base.add(Calendar.MILLISECOND, (int) ((d % 1) * 24 * 60 * 60 * 1000));
    t = base.getTime();
    System.out.println(outFormat.format(base.getTime()));

    return t;

  }

  public double changetoDouble(String str) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Double etDay = 0.0;
    Double etTime = 0.0;
    try {
      etDay = Double.parseDouble(df.parse(str).getTime() / (1000 * 60 * 60 * 24) + "");
      etTime = df.parse(str).getHours() / 24.0 +
          df.parse(str).getMinutes() / (24.0 * 60) +
          df.parse(str).getSeconds() / (24.0 * 60 * 60);
    } catch (NumberFormatException e) {
// TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParseException e) {
// TODO Auto-generated catch block
      e.printStackTrace();
    }
    return etDay + etTime;
  }


  static class TestObj {

    int value;

    public TestObj(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

}

interface Fruit {

  public abstract void eat();
}

class Apple implements Fruit {

  public void eat() {
    System.out.println("Apple");
  }
}

class Orange implements Fruit {

  public void eat() {
    System.out.println("Orange");
  }
}

class Factory {

  public static Fruit getInstance(String ClassName) {
    Fruit f = null;
    try {
      f = (Fruit) Class.forName(ClassName).newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }
}

class Client {

  public static void main(String[] a) {
    Fruit f = Factory.getInstance("io.github.dunwu.spring.Apple");
    if (f != null) {
      f.eat();
    }
  }
}
