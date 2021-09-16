package com.example.designpattern.service.responsibilityChain;

public class SecondFilter implements Filter{

  @Override
  public boolean doFilter(Message message) {

    System.out.println("replace second word");
    return true;
  }
}
