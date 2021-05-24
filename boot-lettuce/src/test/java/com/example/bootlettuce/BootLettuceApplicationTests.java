package com.example.bootlettuce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bootlettuce.entity.BaseEntity;
import com.example.bootlettuce.entity.User;
import com.example.bootlettuce.entity.WhiteListEntity;
import com.example.bootlettuce.entity.test;
import com.example.bootlettuce.utils.DateUtils;
import com.example.bootlettuce.utils.LocalDateUtils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.crypto.Data;
import org.assertj.core.internal.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class BootLettuceApplicationTests {

  @Test
  void contextLoads() {
    test1();
  }

  public void test1() {
//    List<User> list = new ArrayList<>();
//    list.add(new User("zang", 1,1,1));
//    list.add(new User("zang", 2,2,2));
//    list.add(new User("zang", 3,3,3));
//    list.add(new User("zang", 4,4,4));
//    List<User> collect = list.stream().sorted(Comparator.comparing(User::getPriority).thenComparing(Comparator.comparing(User::getGender).reversed()))
//        .collect(Collectors.toList());
//    System.out.println(collect.get(0));

    String sessionListUrl = "https://apis.changbalive.com/api_3rdparty_changba_server.php?ac=getsessioninfolist&kulvdebug=d043fdf7c0a35f2306fe5c3056280332";
    String s ="";
    try {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
      //  执行HTTP请求
      ResponseEntity<String> response = restTemplate
          .exchange(sessionListUrl, HttpMethod.GET, requestEntity, String.class);
      s = response.getBody();
    } catch (Exception e) {

    }

    List<Integer> userIds = new ArrayList<>();
    JSONArray ja = JSON.parseObject(s).getJSONArray("result");
    for (int i = 0; i < ja.size(); i++) {
      Integer userid =  (Integer) ja.getJSONObject(i).getJSONObject("anchorinfo").get("changbauserid");
      userIds.add(userid);
    }
    String result = "(" + userIds.get(0);

    for (int i = 1; i < userIds.size(); i++) {
      result += "," + userIds.get(i);
    }
    result += ")";
    System.out.println(result);
    int i = testException();
  }

  public int testException() {
    try {
      int i  = 1;
    } catch (Exception e) {
      throw e;
    }
    return 1;
  }


  public void getNum(String str) {
    int numB = 0;
    int length = str.length();
    int index = length - 1;
    int count = 0;
    char[] chars = str.toCharArray();
    while (index >= 0) {
      if (chars[index] == 'b') {
        break;
      }
      index--;
    }


    while (index >= 0) {
      if (chars[index] == 'b') {
        numB++;
      } else {
        count = (count + numB) % 1000000007;
        numB = (2 * numB) % 1000000007;
      }
      index--;
    }
    System.out.println(count);
  }

  /**
   * 和线上的特征比较
   * @param newFeaturesOnline
   * @return
   */
  public void getOnlineSRedisValue( String newFeaturesOnline) {

    JSONObject newFeaturesOnlineJson = JSONObject.parseObject(newFeaturesOnline);
    Map<String, Object> add = new HashMap<>();
    Map<String, Object> edit = new HashMap<>();
    Map<String, Object> result = new HashMap<>();
    for (Map.Entry<String, Object> entry : newFeaturesOnlineJson.entrySet()) {
      String key = entry.getKey();
      Object newValue = entry.getValue();
      result.put(key, newValue);
    }

    System.out.println("666");
    System.out.println(result.toString());
    System.out.println("888");
    System.out.println(((JSONObject)JSONObject.toJSON(result)).toJSONString());
  }

  public int rob(int[] nums) {
    if (nums.length == 0) {
      return 0;
    }
    // 子问题：
    // f(k) = 偷 [0..k) 房间中的最大金额

    // f(0) = 0
    // f(1) = nums[0]
    // f(k) = max{ rob(k-1), nums[k-1] + rob(k-2) }

    int N = nums.length;
    int[] dp = new int[N+1];
    dp[0] = 0;
    dp[1] = nums[0];
    for (int k = 2; k <= N; k++) {
      dp[k] = Math.max(dp[k-1], nums[k-1] + dp[k-2]);
    }
    return dp[N];
  }


  /**
   * 格式转换
   * @param tableConf
   * @return
   */
  public JSONObject converTableConf(String tableConf) {
    if (StringUtils.isEmpty(tableConf)) {
      return null;
    }

    JSONObject jsonObject = JSONObject.parseObject(tableConf);
    JSONObject fieldsExpandRule = (JSONObject)jsonObject.get("fields_expand_rule");

    Map<String, Object> mapMap = new HashMap<>();
    for(Map.Entry<String, Object> entry : fieldsExpandRule.entrySet()) {
      String key = entry.getKey();
      JSONObject value = (JSONObject) entry.getValue();
      String type = (String)value.get("type");
      //转换非zip格式的json
      if (!type.equals("zip")) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        Object list = value.get("list");
        if (list != null) {
          map.put("list", list);
        }
        mapMap.put(key, new JSONObject(map));
        continue;
      }

      //转换zip格式的json
      String first = (String)value.get("first");
      if (StringUtils.isEmpty(first)) {
        continue;
      }
      String[] firstArray = first.split(";");
      for (String firstElement : firstArray) {
        if (value.get(firstElement) != null) {
          JSONObject valueInner = (JSONObject)value.get(firstElement);
          convertZipJson(mapMap, key, firstElement, valueInner);
        } else {
          convertZipJson(mapMap, key, firstElement, value);
        }
      }
    }
    return (JSONObject)JSONObject.toJSON(mapMap);

  }

  /**
   * 转换zip格式的json
   * @param mapMap
   * @param key
   * @param firstElement
   * @param value
   */
  public void convertZipJson(Map<String, Object> mapMap, String key, String firstElement, JSONObject value) {
    String second = (String)value.get("second");
    if(StringUtils.isEmpty(second)) {
      return;
    }
    String third = (String)value.get("third");
    if (StringUtils.isEmpty(third)) {
      Map<String, Object> map = new HashMap<>();
      map.put("type", "kv");
      map.put("list", second);
      String newKey = key + "_" + firstElement;
      mapMap.put(newKey, JSONObject.toJSON(map));
    } else {
      String[] secondSArray = second.split(";");
      for (String secondElement : secondSArray) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "kv");
        map.put("list", third);
        String newKey = key + "_" + firstElement + "_" + secondElement;
        mapMap.put(newKey, JSONObject.toJSON(map));
      }
    }
  }



  public void test3() {
    String s = getsessioninfolist();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    JSONArray ja = JSON.parseObject(s).getJSONArray("result");

    for (int i = 0; i < ja.size(); i++) {
      JSONObject jsonObject = ja.getJSONObject(i);
      String anchorId = (Integer) jsonObject.get("anchorid") + "";
      if (StringUtils.isEmpty(anchorId)) {
        continue;
      }
      Integer sessionId = (Integer) jsonObject.get("sessionid");
      String secondTitle = (String) jsonObject.get("secondTitle");
      Map<String, Object> anchorinfo = (Map<String, Object>) jsonObject.get("anchorinfo");
      Integer gender = (Integer)anchorinfo.get("gender");
      Integer cbuserid = (Integer) anchorinfo.get("cbuserid");
      String headPhoto = (String) anchorinfo.get("headPhoto");
      Map<String, Object> recommendrank = (Map<String, Object>) jsonObject.get("recommendrank");
      String starttime = (String) recommendrank.get("starttime");
      JSONArray tag = (JSONArray) recommendrank.get("tag");
      if(tag !=null && tag.size() != 0) {
        List<String> strings = JSONObject.parseArray(tag.toJSONString(), String.class);
      }
      Date date = new Date(Long.parseLong(starttime));
      String format = dateFormat.format(date);

//      try {
//        dateFormat.parse();
//      } catch (ParseException e) {
//        e.printStackTrace();
//      }
//      Date date = new Date(starttime);
      System.out.println(format);
//      System.out.println(cbuserid);
    }
  }
  private String getsessioninfolist() {

    String sessionInfoListUrl = "http://api.changbalive.com/api_3rdparty_changba_server.php?kulvdebug=d043fdf7c0a35f2306fe5c3056280332&ac=getsessioninfolist";
    try {
      RestTemplate client = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
      //  执行HTTP请求
      ResponseEntity<String> response = client
          .exchange(sessionInfoListUrl, HttpMethod.GET, requestEntity, String.class);
      String s = response.getBody();
//      if (StringUtils.isEmpty(s)) {
//        log.error("https get getLiveResult return null , check it!!!");
//        return new MessageModel(result);
//      }
//      JSONArray ja = JSON.parseObject(s).getJSONArray("result");
//      if (ja == null || ja.size() == 0) {
//        log.error("https get getLiveResult result is null , return is {} ", s);
//        return new MessageModel(result);
//      }
      return s;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private String filterTest(test t) {
    return "a";
  }
  public void test2() {

    User user1 = new User();
    user1.setId("1");
    User user2 = new User();
    user2.setId("2");
    User user3 = new User();
    user3.setId("3");
    List<User> list = new ArrayList<>();
    list.add(user1);
    list.add(user2);
    list.add(user3);

    List<User> list1 = new ArrayList<>();
    list1.add(user1);
    list1.add(user2);
    list1.add(user3);
    List<String> collect = list1.stream().filter(a -> !list.contains(a)).map(User::getId)
        .collect(Collectors.toList());
    System.out.println(list1.get(0).getId());

//
//    List<String> ab = new ArrayList<>();
//    ab.add("ab");
//    List<test> list = new ArrayList<>();
//    test a = new test();
//    a.setName1("a");
//    a.setId1("1");
//    a.setAb(ab);
//    test b = new test();
//    b.setName1("b");
//    b.setId1("2");
//    b.setAb(ab);
//    test c = new test();
//    c.setName1("c");
//    c.setId1("3");
//    c.setAb(ab);
//    list.add(b);
//    list.add(a);
//    list.add(c);

//    List<test> collect = list.stream().filter(x -> {
//      if (x.getName1().equals("c")) {
//        return false;
//      }
//      if (x.getName1().equals("a")) {
//        return false;
//      }
//      return true;
//    }).collect(Collectors.toList());
//
//    List<test> collect = list.stream().filter(x -> !x.getName1().equals("b") && !x.getName1().equals(filterTest(x))).collect(Collectors.toList());
//
//    collect.forEach(System.out::println);


//    list.stream().forEach(x -> x.setName1("zangyuxiang"));
//    list.stream().forEach(x -> x.setName1(StringUtils.isEmpty(x.getId1()) ? x.getName1() : x.getId1()));
//    System.out.println(list.size());
//    final List<Integer> collect = list.stream()
//        .sorted((x, y) -> Integer.valueOf(x.getId1()) - Integer.valueOf(y.getId1()))
//        .map(x -> x.getId1().length()).collect(Collectors.toList());
//    System.out.println(collect);
//
//    List<test> newList = list.stream().filter(x -> x.getName1().equals("a") || x.getName1().equals("b")).collect(Collectors.toList());
//    System.out.println(newList.size());
//    List<Integer> collect1 = list.stream().map(test::getId1).map(Integer:: valueOf).collect(Collectors.toList());
//    System.out.println(collect1);
//
//    List<Integer> list1 = new ArrayList<>();
//    list1.add(1);
//    list1.add(3);
//    list1.add(2);
//    list1.add(4);
//    list1.add(6);
//    list1.add(5);
////    Integer reduce = list1.stream().reduce((x,y) -> {
////      System.out.println("x=" + x + ",y=" + y);
////      return x + y;
////    } ).get();
////    System.out.println(reduce);
//
//    final List<Integer> sorted = list1.stream().sorted(Comparator.comparing(Integer::intValue))
//        .collect(Collectors.toList());
//    Integer integer = list1.stream().filter(x -> x == 7).findFirst().orElse(null);
//    System.out.println(integer);

  }


  }
