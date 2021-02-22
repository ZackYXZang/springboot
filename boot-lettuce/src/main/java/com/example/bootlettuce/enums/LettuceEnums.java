package com.example.bootlettuce.enums;

import com.example.bootlettuce.entity.BaseEntity;
import com.example.bootlettuce.entity.User;
import com.example.bootlettuce.entity.test;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxiangzang
 * @create 2020-11-17-4:59 下午
 * @desc
 **/
public class LettuceEnums {

  Map<String, BaseEntity> map = new HashMap<String, BaseEntity>() {
    {
      put("test1", new test());
      put("test2", new User());
    }
  };

}
