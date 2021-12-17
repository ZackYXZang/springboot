package com.example.bootjedis;

import com.alibaba.fastjson.JSONObject;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.KtvRoomFeatureEntity;
import com.example.bootjedis.pojo.LiveCondition;
import com.example.bootjedis.pojo.User;
import com.example.bootjedis.pojo.UserChild;
import com.example.bootjedis.utils.MD5Encrypt;
import com.example.bootjedis.utils.StringUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

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

    System.out.println(10 & 9);
    List<User> list = new ArrayList<>();

    User user1 = new User();
    user1.setId(1);
    user1.setAge(7);
    User user2 = new User();
    user2.setId(1);
    user2.setAge(8);
    User user3 = new User();
    user3.setId(2);
    user3.setAge(8);
    User user4 = new User();
    user4.setId(2);
    user4.setAge(6);
    User user5 = new User();
    user5.setId(3);
    user5.setAge(10);
    User user6 = new User();
    user6.setId(3);
    user6.setAge(12);

    list.add(user1);
    list.add(user2);
    list.add(user3);
    list.add(user4);
    list.add(user5);
    list.add(user6);
    System.out.println(JSONObject.toJSONString(list));

    list = list.stream().filter(x -> x.getAge() == 6).collect(Collectors.toList());
    System.out.println(list.size());

  }

  public List<Object> mixData(List<Object> list1,List<Object> list2,int p1,int p2) {
    if(list1 ==  null || list1.size() <= 0) {
      return list2;
    }
    if(list2 == null || list2.size() <= 0) {
      return list1;
    }
    int sumCount = list1.size() + list2.size();
    int p = p1+p2;
    List<Object> list = new ArrayList<>();
    int m = 0;
    int n = 0;

    int count = 0;
    while(count < sumCount) {
      if(n == list2.size()) {
        while(m < list1.size()) {
          count++;
          list.add(list1.get(m));
          m++;
        }
      }
      if(m == list1.size()) {
        while(n < list2.size()) {
          count++;
          list.add(list2.get(n));
          n++;
        }
      }
      if(count%p == 0) {
        while(m < list1.size()) {
          count++;
          list.add(list1.get(m));
          m++;
          if(m%p1 == 0) break;
        }

      } else {
        while(n < list2.size()) {
          count++;
          list.add(list2.get(n));
          n++;
          if(n%p2 == 0) break;
        }
      }
    }

    return list;
  }

  public void testJedis() {

    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    String string = JSONObject.toJSONString(list);
    System.out.println(string);
  }

  public List<Integer> testList(List<Integer> list) {

    return list.subList(0,3);
  }

}
