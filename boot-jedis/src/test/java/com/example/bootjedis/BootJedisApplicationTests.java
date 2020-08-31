package com.example.bootjedis;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bootjedis.pojo.AccompanyRecReturnEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class BootJedisApplicationTests {

  @Autowired
  private JedisPool jedisPool;

  //testBanned:[249330]


  @Test
  void contextLoads() {

//    java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
//    System.out.println(date);
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    String dt = format.format(new Date());
//    Date date1 = new Date();
//    java.sql.Date date = new java.sql.Date();
//    date.toLocalDate();
//    System.out.println(dt);

//   List<String> list = new ArrayList<>();
//   list.add("249330");
//   String string = JSONObject.toJSONString(list);
//
//    List<String> strings = JSONArray.parseArray(string, String.class);
//    System.out.println(strings);
//
//    if (!strings.contains("249330")) {
//      System.out.println("249330 未被封禁过");
//    }

    boolean zang = isNumeric("*#");
    boolean num = isNumeric("1324");
    System.out.println(zang);
    System.out.println(num);
  }

  public boolean isNumeric(String str){
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if( !isNum.matches() ){
      return false;
    }
    return true;
  }

  private void check1(int count, int num) {
    count += num;
  }

  private int check (int count, int num, Map<String, Integer> map) {
    count = count + num;
    map.put("zang", count);
    return count;
  }

  private List<String> removeDuplicateList (List < String > songList, List < String > songids){

    List<String> result = new ArrayList<>();
    for (String song : songList) {
      if (songids.contains(song)) {
        continue;
      }
      result.add(song);
      songids.add(song);
    }

    return result;
  }


  private List<String> dealSongList(List<String> value, Jedis jedis, String pattern,
      Map<Integer, String> enumMap) {
    List<String> songList = new ArrayList<>(100);
    List<String> TempList = value.size() > 2 ? value.subList(0, 2) : value;
    for (String temp : TempList) {
      String key = enumMap.get(Integer.valueOf(temp));
      String newKey = String.format(pattern, key);
      String values = jedis.get(newKey);
      String[] split = values.split(";");
      List<String> songListTemp = Arrays.asList(split);
      songList.addAll(songListTemp.size() > 50 ? songList.subList(0, 50) : songListTemp);

    }
    return songList;
  }

  private List<String> dealArtistList(List<String> artistList, Jedis jedis, String pattern) {
    List<String> songList = new ArrayList<>(100);
    for (String artist : artistList) {
      String artistNewKey = String.format(pattern, artist);
      String artistSongs = jedis.get(artistNewKey);
      if (StringUtils.isEmpty(artistSongs)) {
        String[] split = artistSongs.split(";");
        List<String> tempList = Arrays.asList(split);
        songList.addAll(tempList.size() > 5 ? tempList.subList(0, 5) : tempList);
      }
    }
    return songList;
  }
}
