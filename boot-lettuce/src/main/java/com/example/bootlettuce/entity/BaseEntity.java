package com.example.bootlettuce.entity;

import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2020-10-09-10:59 下午
 * @desc
 **/
@Data
public class BaseEntity<T> {

  private T baseEntity;

  public BaseEntity(T baseEntity) {
    this.baseEntity = baseEntity;
  }

  public T getBaseEntity() {
    return baseEntity;
  }

}
