package com.example.bootlettuce.entity;

import com.alibaba.fastjson.annotation.JSONField;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2021-05-11-下午5:07
 * @desc 白名单json测试sub
 **/
@Data
public class WhiteListBaseEntity {

  @JSONField(name = "anchor_id")
  Integer anchorId;

  @JSONField(name = "start_time")
  Date startTime;

  @JSONField(name = "end_time")
  LocalDateTime endTime;


}
