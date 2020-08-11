package com.example.bootjedis.Service.Impl;

import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.User;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author yuxiangzang
 * @create 2020-07-03-7:14 下午
 * @desc 测试jedis接口实现类
 **/
@Service
public class UserServiceImpl implements UserService {

  private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private JedisPool jedisPool;

  @Override
  public String getString(String key) {
    Jedis jedis = jedisPool.getResource();
//    jedis.zscore();
//    jedis.sismember();
    String value = null;
    if (jedis.exists(key)) {
      value = jedis.get(key);
    }

    jedis.close();
    return value;
  }

  @Override
  public User selectById(String id) {
    User user = new User();
    try (Jedis jedis = jedisPool.getResource()){
      if(jedis.exists(id)) {
        logger.info("从redis中取值");
        Map<String, String> map = jedis.hgetAll(id);
        user.setId(map.get("id"));
        user.setName(map.get("name"));
        user.setAge(Integer.parseInt(map.get("age")));
      } else {
        user.setAge(18);
        user.setName("jedis学习");
        user.setId(id);
        logger.info("--->查询数据库，并把值放入redis");
        Map<String, String> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("age", user.getAge() + "");
        jedis.hmset(id, map);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }

    return user;
  }
}
