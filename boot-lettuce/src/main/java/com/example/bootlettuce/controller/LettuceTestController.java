package com.example.bootlettuce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuxiangzang
 * @create 2020-11-24-3:06 下午
 * @desc 测试接口
 **/
@RestController
public class LettuceTestController {


  @RequestMapping(value = "/test1", produces = "application/json;charset=UTF-8", method = {
      RequestMethod.GET, RequestMethod.POST})
  public String lettuceTest() {

    return "a";
  }

  @RequestMapping(value = "/test2", produces = "application/json;charset=UTF-8", method = {
      RequestMethod.GET, RequestMethod.POST})
  public String lettuceTest2() {
    return "b";
  }
}
