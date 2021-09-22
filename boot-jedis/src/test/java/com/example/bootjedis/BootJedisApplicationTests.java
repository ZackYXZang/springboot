package com.example.bootjedis;

import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.utils.StringUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;

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

    Set<String> set = new HashSet<>();
    set.add("zang");
    set.add("yu");
    set.add("xiang");

    List<String> list = new ArrayList<>();

    list.add("zang1");
    list.add("yu");
    list.add("xiang1");

    long start = System.currentTimeMillis();
    boolean b = list.stream().anyMatch(x -> set.contains(x));
    long end = System.currentTimeMillis();
    System.out.println(end - start);


    List<String> list1 = new ArrayList<>();
    list1.add("zang");
    list1.add("yu");
    list1.add("xiang");

    long start1 = System.currentTimeMillis();
    boolean b1 = list.stream().anyMatch(x -> list.stream().anyMatch(x::equals));
    long end1 = System.currentTimeMillis();
    System.out.println(end1 - start1);

  }

}
