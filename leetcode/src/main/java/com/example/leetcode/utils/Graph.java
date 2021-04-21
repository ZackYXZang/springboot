package com.example.leetcode.utils;

import java.util.HashMap;
import java.util.HashSet;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2021-03-24-下午4:56
 * @desc 图
 **/
@Data
public class Graph {

  //点的集合，key是编号，也就是节点的值，value是实际的node
  public HashMap<Integer, GraphNode> nodes;

  //边的集合
  public HashSet<GraphEdge> edges;

  public Graph() {
    this.nodes = new HashMap<>();
    this.edges = new HashSet<>();
  }

}
