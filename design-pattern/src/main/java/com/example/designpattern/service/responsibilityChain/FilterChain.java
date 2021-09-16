package com.example.designpattern.service.responsibilityChain;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter{

  List<Filter> filterChain = new ArrayList<>();
  public FilterChain add(Filter filter) {
    filterChain.add(filter);
    return this;
  }

  @Override
  public boolean doFilter(Message message) {
    for (Filter filter : filterChain) {
      if (!filter.doFilter(message)) {
        return false;
      }
    }
    return true;
  }
}
