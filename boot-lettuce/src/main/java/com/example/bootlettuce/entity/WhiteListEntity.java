package com.example.bootlettuce.entity;

import java.util.List;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2021-05-11-下午5:07
 * @desc 白名单json测试
 **/
@Data
public class WhiteListEntity {

  Integer on;

  List<WhiteListBaseEntity> config;

}
