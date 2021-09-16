package com.example.designpattern.service.templateMethod;

import org.springframework.stereotype.Service;

/**
 * 模版方法/钩子函数
 */
@Service
public class TemplateMethodServiceImpl {

  public void templateMethod() {
    System.out.println("模版模式 start");
    Father father = new Son();
    father.father();
    System.out.println("模版模式 end");
  }
}
