package com.example.springaoplearn.proxy.jdk;

public class MyCalculator implements Calculator{

  @Override
  public int add(int i, int j) {
    return i + j;
  }

  @Override
  public int sub(int i, int j) {
    return i - j;
  }

  @Override
  public int mult(int i, int j) {
    return i * j;
  }

  @Override
  public int div(int i, int j) {
    return i / j;
  }
}
