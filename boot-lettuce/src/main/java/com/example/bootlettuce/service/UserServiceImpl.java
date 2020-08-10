package com.example.bootlettuce.service;

import com.example.bootlettuce.entity.User;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2020-08-09-1:32 下午
 * @desc
 **/
@Service
public class UserServiceImpl {

  @Autowired
  private RedisTemplate redisTemplate;

  @Resource(name = "redisTemplate")
  private ValueOperations<String, String> string;  //就是redisTemplate.opsForValue()

  public String getString(String key) {
    String value = null;
    if (redisTemplate.hasKey(key)) {//和jedis的exists方法
      //jedis的get方法
      value = (String) redisTemplate.opsForValue().get(key);
      value = string.get(key);//和上一行代码一个意思
    } else {
      value = "RedisTemplate模版学习lettuce客户端";
      //jedis的set方法
      redisTemplate.opsForValue().set(key, value);
    }
    return value;
  }

  public void expireString(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    redisTemplate.expire(key, 28, TimeUnit.HOURS);
  }

  public User selectById(String id) {
    //因为hash类型在redis的存储格式是：user:id，比如：user:1    user:2
    //所以hashKey的第一个参数是user（表名），第二个参数是key值
    User user = null;
    if (redisTemplate.opsForHash().hasKey("user", id)) {
      user = (User) redisTemplate.opsForHash().get("user", id);
    } else {
      User u = new User();
      u.setId(id);
      u.setAge(20);
      u.setName("zang");
      redisTemplate.opsForHash().put("user", id, u);
    }
    return user;
  }
}
