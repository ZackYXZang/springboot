package com.example.bootjedis.Service;

import com.example.bootjedis.pojo.User;

/**
 * @author yuxiangzang
 * @create 2020-07-03-7:11 下午
 * @desc 测试jedis接口
 **/
public interface UserService {

  /**
   * Redis String 类型
   * 需求：用户输入一个key，
   * 先判断redis中是否存在该数据，
   * 如果存在，在redis中查询，
   * 不存在，在mysql中查询
   */
  public String getString(String key);

  /**
   * Redis Hash 类型
   * 需求：
   *  用户在前段传入一个ID编号，
   *  先到Redis中查询，如果Redis中存在，直接返回给用户结果
   *  如果Redis中不存在，查询Mysql，并查询到结果，赋值给Redis，并返回
   */
  User selectById(String id);

}
