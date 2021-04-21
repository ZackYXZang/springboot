package com.example.leetcode.utils;

import java.util.ArrayList;
import lombok.Data;

/**
 * @author yuxiangzang
 * @create 2021-03-24-下午4:49
 * @desc 图的节点
 **/
@Data
public class GraphNode {

  //该点的值
   public int value;

   //入度，有多少个节点指向我
   public int in;
   //出度，我指向多少个节点
   public int out;

   //该节点能达到的节点
   public ArrayList<GraphNode> nexts;

   //从该节点出发的边
   public ArrayList<GraphEdge> edges;

   public GraphNode(int value) {
     this.value = value;
     in = 0;
     out = 0;
     nexts = new ArrayList<>();
     edges = new ArrayList<>();
   }
}
