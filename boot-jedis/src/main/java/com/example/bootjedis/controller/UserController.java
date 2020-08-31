package com.example.bootjedis.controller;

import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.mapper.ChannelDataMapper;
import com.example.bootjedis.pojo.ChannelData;
import com.example.bootjedis.pojo.ChatInputParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuxiangzang
 * @create 2020-05-29-6:39 下午
 * @desc
 **/
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ChannelDataMapper channelDataMapper;

  @RequestMapping("/test")
  public String getString() {
    String res = userService.getString("a");
    return res;
  }
  @RequestMapping(value = "/test1", produces = "application/json;charset=UTF-8", method =
      RequestMethod.POST)
  public String getString1() {

    List<ChannelData> channelData = channelDataMapper
        .selectAll("唱吧", null, null, "admin", 1, 30);

    return null;
  }


}
