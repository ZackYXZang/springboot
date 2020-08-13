package com.example.bootjedis.controller;

import com.example.bootjedis.Service.UserService;
import com.example.bootjedis.pojo.ChatInputParam;
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

  @RequestMapping("/test")
  public String getString() {
    String res = userService.getString("a");
    return res;
  }
  @RequestMapping(value = "/test1", produces = "application/json;charset=UTF-8", method =
      RequestMethod.POST)
  public String getString1(@RequestBody ChatInputParam req) {
    if (req.getUserid() == null || req.getNum() < 1 || (req.getGender() != 0 && req.getGender() != 1)) {
      return "chatRecommend parameters error...";
    }
    String res = userService.getString("a");
    return res;
  }
}
