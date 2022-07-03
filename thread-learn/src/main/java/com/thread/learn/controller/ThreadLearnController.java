package com.thread.learn.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadLearnController {


  @RequestMapping(value = "/test", produces = "application/x-www-form-urlencoded;charset=UTF-8", method =
      RequestMethod.POST)
  public String getString() {

    return null;
  }
}
