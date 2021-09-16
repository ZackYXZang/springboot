package com.example.designpattern.service.responsibilityChain;

import org.springframework.stereotype.Service;

/**
 * 责任链模式
 */
@Service
public class ResponsibilityChainServiceImpl {

  public void responsibilityChain() {
    System.out.println("责任链模式 start");
    FilterChain filterChain = new FilterChain();
    filterChain.add(new FirstFilter()).add(new SecondFilter()).add(new ThirdFilter());

    Message message = new Message();
    message.setMessage("responsibility chain pattern");
    filterChain.doFilter(message);
    System.out.println("责任链模式 end");

  }
}
