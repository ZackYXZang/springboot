package com.thread.learn.entity;

import lombok.Data;

@Data
public class ThreadEntity {

  String name;


  Integer age;


  ThreadLocal<Integer> height = ThreadLocal.withInitial(() -> 0);

}
