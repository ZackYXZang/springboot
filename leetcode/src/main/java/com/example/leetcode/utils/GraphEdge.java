package com.example.leetcode.utils;

/**
 * @author yuxiangzang
 * @create 2021-03-24-下午4:51
 * @desc 图的边
 **/
public class GraphEdge {
  //边的权重
  public int weight;

  //出发的接待你
  public GraphNode from;
  //到达的节点
  public GraphNode to;


  public GraphEdge(int weight, GraphNode from, GraphNode to) {
    this.weight = weight;
    this.from = from;
    this.to = to;
  }
}
