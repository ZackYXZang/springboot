package com.example.designpattern.service.responsibilityChain;

public class FirstFilter implements Filter{

  @Override
  public boolean doFilter(Message message) {
    System.out.println("replace first word");
    return true;
  }
}
