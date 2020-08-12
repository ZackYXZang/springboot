package com.example.bootjedis.controller;

import com.example.bootjedis.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
