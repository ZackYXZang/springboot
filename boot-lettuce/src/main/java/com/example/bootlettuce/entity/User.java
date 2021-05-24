package com.example.bootlettuce.entity;

import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-07-06-5:39 下午
 * @desc 实体类
 **/
@Data
public class User {

  public User() {
//    super();
    this.setWorkids(new int[1]);
  }


  public User(Integer id, String name, Integer age, int[] workids, String tag, Integer priority, Integer score, Integer gender) {
    this.id = String.valueOf(id);
    this.name = name;
    this.age = age;
    this.workids = workids;
    this.tag = tag;
    this.priority = priority;
    this.gender = gender;
  }

  public User(String name, Integer priority, Integer score, Integer gender) {
    this.name = name;
    this.priority = priority;
    this.gender = gender;
  }

  private String id;

  private String name;

  private Integer age;

  private Integer gender;

  private int[] workids;

  private String tag;

  private Integer priority;

  private ThreadLocal<Long> score = ThreadLocal.withInitial(() -> 0L);

}
