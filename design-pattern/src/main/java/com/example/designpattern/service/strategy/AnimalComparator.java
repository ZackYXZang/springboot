package com.example.designpattern.service.strategy;

public interface AnimalComparator<T> {

  int compare(T o1, T o2);
}
