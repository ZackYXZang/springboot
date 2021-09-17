package com.example.designpattern.service.composite;

import java.util.ArrayList;
import java.util.List;

public class BranchNode extends Node{

  List<Node> nodes = new ArrayList<>();

  String name;

  public BranchNode(String name) {
    this.name = name;
  }

  @Override
  public void p() {
    System.out.println(name);
  }

  public void add(Node node) {
    nodes.add(node);
  }
}
