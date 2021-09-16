package com.example.designpattern.service.responsibilityChain;

public class ThirdFilter implements Filter{

  @Override
  public boolean doFilter(Message message) {

    System.out.println("replace third word");
    return true;
  }
}
