package com.example.designpattern.service.responsibilityChain;

/**
 * 责任链filter
 */
public interface Filter {

  public boolean doFilter(Message message);
}
