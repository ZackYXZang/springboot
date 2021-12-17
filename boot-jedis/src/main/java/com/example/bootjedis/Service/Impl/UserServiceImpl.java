package com.example.bootjedis.Service.Impl;

import com.dianping.cat.Cat;
import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.User;
import com.example.bootjedis.utils.TimeUtil;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author yuxiangzang
 * @create 2020-07-03-7:14 下午
 * @desc 测试jedis接口实现类
 **/
@Primary
@Service
public class UserServiceImpl {

  private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private JedisPool jedisPool;

  int count = 2;
//  @Scheduled(initialDelay = 0, fixedDelay = 5 * 1000)
  public int scheduledChatMatchData() {
    int num = count;
    num = num + 1;
    count = num;
    System.out.println(num);
    Cat.logMetricForCount("scheduledChatMatchData", count);
    return count;
  }

  public String getString(String key) {
    TimeUtil timer = new TimeUtil();


    System.out.println("timer: " + timer.getTimeAndReset());

    return "value";
  }

  private void test1(Jedis jedis) {
    TimeUtil timer1 = new TimeUtil();
    String value2 = jedis
        .hget("map:info:v1:1", "song_pay_gift_7day_earned_goldcoin");
    System.out.println(value2);
    System.out.println("timer1: " + timer1.getTimeAndReset());
  }

  public User selectById(String id) {
    User user = new User();
//    try (Jedis jedis = jedisPool.getResource()) {
//      if (jedis.exists(id)) {
//        logger.info("从redis中取值");
//        Map<String, String> map = jedis.hgetAll(id);
//        user.setId(map.get("id"));
//        user.setName(map.get("name"));
//        user.setAge(Integer.parseInt(map.get("age")));
//      } else {
//        user.setAge(18);
//        user.setName("jedis学习");
//        user.setId(id);
//        logger.info("--->查询数据库，并把值放入redis");
//        Map<String, String> map = new HashMap<>();
//        map.put("id", user.getId());
//        map.put("name", user.getName());
//        map.put("age", user.getAge() + "");
//        jedis.hmset(id, map);
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

    return user;
  }

  public void test() {
    System.out.println("test");
  }
}
