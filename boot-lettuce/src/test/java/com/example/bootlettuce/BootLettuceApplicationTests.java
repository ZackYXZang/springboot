package com.example.bootlettuce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bootlettuce.entity.BaseEntity;
import com.example.bootlettuce.entity.User;
import com.example.bootlettuce.entity.test;
import com.example.bootlettuce.utils.DateUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.xml.crypto.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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


    String tableConf =
        "\n"
            + "{\n"
            + "    \"name\":\"dmbj.dm_songid_recommend_feature_information_v3\",\n"
            + "    \"partition\":\"dt\",\n"
            + "    \"partition_value\":\"#lastdate#\",\n"
            + "    \"fields_expand_rule\":{\n"
            + "        \"parent_songid\":{\n"
            + "            \"type\":\"un\"\n"
            + "        },\n"
            + "        \"artist_sex\":{\n"
            + "            \"type\":\"enum\",\n"
            + "            \"list\":\"-1;1;2;3\"\n"
            + "        },\n"
            + "        \"tag\":{\n"
            + "            \"type\":\"list\"\n"
            + "        },\n"
            + "        \"song_version\":{\n"
            + "            \"type\":\"enum\",\n"
            + "            \"list\":\"0;1;2;3;4\"\n"
            + "        },\n"
            + "        \"artist_characteristic_songid_uv\":{\n"
            + "            \"type\":\"kv\",\n"
            + "            \"list\":\"1;2;3;4;5;6;7;8;9;10;11;12;13\"\n"
            + "        },\n"
            + "        \"gender_datekey_pv\":{\n"
            + "            \"type\":\"zip\",\n"
            + "            \"first\":\"0;1\",\n"
            + "            \"second\":\"7;30\"\n"
            + "        },\n"
            + "        \"artist_sing_listen_uv\":{\n"
            + "            \"type\":\"zip\",\n"
            + "            \"first\":\"sing_userid_uv;listen_userid_uv;songid_artist_exposure_ctr\",\n"
            + "            \"second\":\"1;3;7;30\",\n"
            + "            \"third\":\"6;8\"\n"
            + "        },\n"
            + "        \"songid_behavior\":{\n"
            + "            \"type\":\"zip\",\n"
            + "            \"first\":\"bei_sing_songid_pv;bei_exposure_pv\",\n"
            + "            \"second\":\"1;3;7;30\",\n"
            + "            \"third\":\"5;9\",\n"
            + "            \"bei_sing_songid_pv\":{\"second\":\"1;30\"}\n"
            + "        }\n"
            + "    }\n"
            + "}";
    JSONObject jsonObject = JSONObject.parseObject(tableConf);
    JSONObject fieldsExpandRule = (JSONObject)jsonObject.get("fields_expand_rule");


    Map<String, Object> mapMap = new HashMap<>();
    for(Map.Entry<String, Object> entry : fieldsExpandRule.entrySet()) {
      String key = entry.getKey();
      JSONObject value = (JSONObject) entry.getValue();
      String type = (String)value.get("type");
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
    jsonObject.put("fields_expand_rule", JSONObject.toJSON(mapMap));
    String result = jsonObject.toString();
    System.out.println(result);

  }

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
