package com.example.leetcode.service;

import com.example.leetcode.service.ZuoClassFiveSevenPractice.UnionFindSet;
import com.example.leetcode.utils.Graph;
import com.example.leetcode.utils.GraphEdge;
import com.example.leetcode.utils.GraphNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2021-03-24-下午4:18
 * @desc 图论相关方法
 **/
@Service
public class ZuoClassGraphPractice {

  /**
   * BFS，宽度优先搜索，使用队列 思路： 从开始节点，把节点和节点到to节点不停的放入队列中，利用队列到先入先出特性，不停到poll 同时又一个set，用来记录已经走过哪些节点
   */
  public List<Integer> BFS(GraphNode head) {
    if (head == null) {
      return null;
    }

    //用来遍历
    Queue<GraphNode> queue = new LinkedList<>();
    //用来存储哪些节点已经走过，也就是哪些节点已经放入到队列中过了
    Set<GraphNode> set = new HashSet<>();
    //结果
    List<Integer> result = new ArrayList<>();
    queue.add(head);
    set.add(head);

    while (!queue.isEmpty()) {
      GraphNode curr = queue.poll();
      result.add(curr.value);
      for (GraphNode next : curr.nexts) {
        if (!set.contains(next)) {
          set.add(next);
          queue.add(next);
        }
      }
    }
    return result;
  }

  /**
   * DFS，深度优先搜索，利用栈
   * 从头开始，把from节点，和to节点一起放入栈中
   * pop时会pop出to节点，在把它和它的to节点一起放入栈中
   * @param head
   * @return
   */
  public List<Integer> DFS(GraphNode head) {
    if (head == null) {
      return null;
    }

    //用来遍历
    Stack<GraphNode> stack = new Stack<>();
    Set<GraphNode> set = new HashSet<>();
    List<Integer> result = new ArrayList<>();
    stack.add(head);
    set.add(head);
    result.add(head.value);

    while (!stack.isEmpty()) {
      GraphNode curr = stack.pop();
      for (GraphNode next : curr.nexts) {
        if (!set.contains(curr)) {
          stack.add(curr);
          stack.add(next);
          set.add(next);
          result.add(next.value);
          break;

        }
      }
    }

    return result;
  }

  /**
   * 拓扑排序，这个图必须是有向图，且没有环路，一般用来编译
   * 思路是，把每一个节点和它的入度放入map对应起来
   * 同时找到入度为0的节点，从这些节点出发，沿着next找，同时更新这个next的入度-1
   * 直到遍历完
   * @param graph
   * @return
   */
  public List<Integer> TopologySort(Graph graph) {
    if (graph == null) {
      return null;
    }

    //用来存放节点和它的入度，之后每一更新入度
    Map<GraphNode, Integer> map = new HashMap<>();
    //用来存放入度为0的节点
    Queue<GraphNode> queue = new LinkedList<>();
    for (GraphNode node : graph.nodes.values()) {
      int in = node.in;
      map.put(node, in);
      if (in == 0) {
        queue.add(node);
      }
    }

    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
      GraphNode curr = queue.poll();
      result.add(curr.value);
      for (GraphNode next : curr.nexts) {
        map.put(next, map.get(next) - 1);
        if (map.get(next) == 0) {
          queue.add(next);
        }
      }
    }
    return result;

  }

  /**
   * 最小生成树（无向图）：保证能连通图，且所有边对权重最低
   */

  /**
   * Kruskal算法
   * 从小权重对边考虑，如果没有形成回路，就要这个边
   * 这里如果两个节点判断形没形成回路，需要用到并查集
   */
  public Set<GraphEdge> Kruskal(Graph graph) {
    //构建并查集
    UnionFindSet unionFindSet = new UnionFindSet();
    unionFindSet.makeSets(Arrays.asList(graph.nodes.values()));
    //构建小根堆
    PriorityQueue<GraphEdge> heap = new PriorityQueue<>(new Comparator<GraphEdge>() {
      @Override
      public int compare(GraphEdge o1, GraphEdge o2) {
        return o1.weight - o2.weight;
      }
    });
    for (GraphEdge edge : graph.edges) {
      heap.add(edge);
    }

    Set<GraphEdge> result = new HashSet<>();
    while (!heap.isEmpty()) {
      GraphEdge currEdge = heap.poll();
      //判断是否形成回路，也就是是否属于同一个并查集
      if (!unionFindSet.isSameSet(currEdge.from, currEdge.to)) {
        result.add(currEdge);
        unionFindSet.union(currEdge.from, currEdge.to);
      }
    }
    return result;
  }

  /**
   * prim算法
   * 首先选取一个节点，把这个节点的所有边都加入小根堆
   * 找到权重最小的边所连接的节点，并判断走没走过
   * 如果没走过，把这个节点的边都加入小根堆
   * 重复上两步
   * 如果走过了忽略
   */
  public Set<GraphEdge> Prim(Graph graph) {
    if (graph == null) {
      return null;
    }

    Set<GraphEdge> result =new HashSet<>();
    //判断节点是否走过
    Set<GraphNode> set = new HashSet<>();
    //小根堆，用来选取权重最小的边
    PriorityQueue<GraphEdge> heap = new PriorityQueue<>(new Comparator<GraphEdge>() {
      @Override
      public int compare(GraphEdge o1, GraphEdge o2) {
        return o1.weight - o2.weight;
      }
    });

    //这里用到for循环，是放置有森林，就是很多个小图拼成一个大图
    for (GraphNode node : graph.nodes.values()) {
      if (!set.contains(node)) {
        set.add(node);
        for (GraphEdge edge : node.edges) {
          heap.add(edge);
        }

        while (!heap.isEmpty()) {
          GraphEdge edge = heap.poll();
          GraphNode toNode = edge.to;
          if (!set.contains(toNode)) {
            set.add(toNode);
            result.add(edge);
            for (GraphEdge toEdge : toNode.edges) {
              heap.add(toEdge);
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * dijkstra算法
   * 返回结果是该节点，及该节点到原点的最短距离
   */
//  public HashMap<GraphNode, Integer> dijkstra(Graph graph, int size) {
//
//  }

  class GraphNodeHeap{
    
  }

}
