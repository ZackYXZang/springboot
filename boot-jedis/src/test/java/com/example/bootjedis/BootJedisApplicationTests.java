package com.example.bootjedis;

import com.alibaba.fastjson.JSONArray;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.LiveCondition;
import com.example.bootjedis.pojo.User;
import com.example.bootjedis.utils.LocalDateUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import java.util.jar.JarEntry;
import org.apache.poi.ss.formula.functions.T;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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


  @Test
  void contextLoads() {
    test2();
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


    long nowUnixSecondTime = LocalDateUtils.getUnixSecondTime(LocalDateTime.now());
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


  public static void main(String[] args) {
    /** 初始化集合类*/
    ArrayList<TestObj> list = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      list.add(new TestObj(i));
    }

    /** 遍历时删除元素*/
    for (TestObj obj : list) {
      if (obj.getValue() < 10) {
        /** 这里会抛出ConcurrentModificationException*/
        list.remove(obj);
      }
    }
  }


  public void test1() {

    String element = "test,1,1";
    element = element.substring(0, element.length() - 2) + ",0" + element.substring(element.length() - 2);
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
        if (element1.trim().charAt(element1.trim().length() - 1) == element2.trim().charAt(element2.trim().length() - 1)) {
          double score1 = o1.getScore();
          double score2 = o2.getScore();
          if (score1 > score2) {
            return 1;
          } else if (score1 < score2) {
            return -1;
          }
          return 0;
        }

        if ((element1.trim().charAt(element1.trim().length() - 1) == '1') && (element2.trim().charAt(element2.trim().length() - 1) == '0')) {
          return -1;
        }

        if ((element1.trim().charAt(element1.trim().length() - 1) == '0') && (element2.trim().charAt(element2.trim().length() - 1) == '1')) {
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

    for (int i =0; i< list1.size(); i++) {
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
