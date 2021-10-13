package com.example.bootjedis;

import com.alibaba.fastjson.JSONObject;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.KtvRoomFeatureEntity;
import com.example.bootjedis.pojo.LiveCondition;
import com.example.bootjedis.utils.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
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

    testJedis();
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
