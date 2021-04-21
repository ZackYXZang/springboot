package com.example.leetcode.service;

import com.example.leetcode.utils.TrieNode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yuxiangzang
 * @create 2021-03-23-下午5:13
 * @desc
 **/
@Service
public class ZuoClassFiveSevenPractice {

  /**
   * 设计一种结构，在该结构中有如下三个功能： insert(key)：将某个key加入到该结构，做到不重复加入。 delete(key)：将原本在结构中的某个key移除。
   * getRandom()： 等概率随机返回结构中的任何一个key。 【要求】 Insert、delete和getRandom方法的时间复杂度都是 O(1)
   */
  class RandomPool {

    private HashMap<Integer, Integer> keyIndexMap;
    private HashMap<Integer, Integer> indexKeyMap;
    private int size = 0;

    public RandomPool() {
      this.keyIndexMap = new HashMap<>();
      this.indexKeyMap = new HashMap<>();
      this.size = 0;
    }

    public void insert(Integer key) {
      if (!keyIndexMap.containsKey(key)) {
        keyIndexMap.put(key, size);
        indexKeyMap.put(size, key);
        size++;
      }
    }

    public void delete(Integer key) {
      //1。先从keyIndexMap获取要删除的key对应的index
      //2。通过size--，找到最后一个位置（lastIndex）
      //3。从indexKeyMap获取最后一个位置的对应的key（lastKey）
      //4。更新keyIndexMap，把最后一个key对应的index改为删除的那个key对应的index
      //5。更新indexKeyMap，把要删除key对应的index位置的（index，key），变成（index，lastKey）
      //6。删除keyIndexMap中的key
      //7。删除indexKeyMap中的最后位置（lastIndex）
      if (this.keyIndexMap.containsKey(key)) {
        Integer deleteIndex = this.keyIndexMap.get(key);
        //因为size是从0开始的
        int lastIndex = --this.size;
        Integer lastKey = this.indexKeyMap.get(lastIndex);
        this.keyIndexMap.put(lastKey, deleteIndex);
        this.indexKeyMap.put(deleteIndex, lastKey);
        this.keyIndexMap.remove(key);
        this.indexKeyMap.remove(lastIndex);
      }
    }

    public Integer getRandom() {
      if (this.size == 0) {
        return null;
      }

      int randomIndex = (int) (Math.random() * this.size);
      return this.keyIndexMap.get(randomIndex);
    }
  }

  /**
   * 岛问题 一个矩阵中只有0和1两种值，每个位置都可以和自己的上、下、左、右 四个位置相连，如果有一片1连在一起，这个部分叫做一个岛，求一个 矩阵中有多少个岛？
   */
  public int countIslands(int[][] matrix) {
    //思路是：
    //1。在碰到1的时候，把所有相连的1变成2，遇到0返回
    //2。继续，如果碰到0、或者边界返回，碰到2继续，碰到1，结果加1，同时重复步骤1
    if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
      return 0;
    }

    int row = matrix.length;
    int col = matrix[0].length;
    int res = 0;

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (matrix[i][j] == 1) {
          res++;
          infect(matrix, i, j, row, col);
        }
      }
    }

    return res;
  }

  public void infect(int[][] matrix, int row, int col, int rowLength, int colLength) {
    if (row < 0 || col < 0 || row >= rowLength || col >= colLength || matrix[row][col] != 1) {
      return;
    }
    matrix[row][col] = 2;
    infect(matrix, row + 1, col, rowLength, colLength);
    infect(matrix, row - 1, col, rowLength, colLength);
    infect(matrix, row, col + 1, rowLength, colLength);
    infect(matrix, row, col - 1, rowLength, colLength);
  }


  //并查集，用来查找a，b是否是一个集合
  //必须要在一开始给定一个集合
  static class UnionFindSet {

    //value是key的父节点
    public HashMap<Object, Object> fatherMap;
    //表示以key为头节点的集合有多大
    public HashMap<Object, Integer> sizeMap;

    public UnionFindSet() {
      this.fatherMap = new HashMap<>();
      this.sizeMap = new HashMap<>();
    }

    //初始化，此时每一个节点到父节点都是自己
    public void makeSets(List<Object> list) {
      fatherMap.clear();
      sizeMap.clear();
      for (Object fanXing : list) {
        fatherMap.put(fanXing, fanXing);
        sizeMap.put(fanXing, 1);
      }
    }

    //找到节点到父节点
    private Object findHead(Object fanXing) {
      Object father = fatherMap.get(fanXing);
      if (father != fanXing) {
        father = findHead(father);
      }
      fatherMap.put(fanXing, father);
      return father;
    }

    //判断两个节点是不是同一个集合
    public boolean isSameSet(Object a, Object b) {
      return findHead(a) == findHead(b);
    }

    //合并两个节点，同时要把以两个节点为头的集合合并了，小的合并到大的里
    public void union(Object a, Object b) {
      if (a == null || b == null) {
        return;
      }

      Object aHead = findHead(a);
      Object bHead = findHead(b);

      if (aHead != bHead) {
        int aSetSize = sizeMap.get(a);
        int bSetSize = sizeMap.get(b);
        if (aSetSize > bSetSize) {
          fatherMap.put(bHead, aHead);
          sizeMap.put(a, aSetSize + bSetSize);
        } else {
          fatherMap.put(aHead, bHead);
          sizeMap.put(b, aSetSize + bSetSize);
        }
      }
    }
  }

  //前缀树，字典树
  class Trie {

    private TrieNode root;

    public Trie() {
      root = new TrieNode();
    }

    //插入
    public void insert(String word) {
      if (StringUtils.isEmpty(word)) {
        return;
      }
      char[] chars = word.toCharArray();
      TrieNode node = root;
      for (int i = 0; i < chars.length; i++) {
        int index = chars[i] - 'a';
        if (node.nexts[index] == null) {
          node.nexts[index] = new TrieNode();
        }
        node = node.nexts[index];
        node.path++;
      }
      node.end++;
    }

    //删除
    public void delete(String word) {
      if (search(word) != 0) {
        char[] chars = word.toCharArray();
        TrieNode node = root;
        for (int i = 0; i < chars.length; i++) {
          int index = chars[i] - 'a';
          if (--node.nexts[index].path == 0) {
            node.nexts[index] = null;
            return;
          }
          node = node.nexts[index];
        }
        node.end--;
      }
    }

    //查询输入单词存在的个数
    public int search(String word) {
      if (StringUtils.isEmpty(word)) {
        return 0;
      }
      char[] chars = word.toCharArray();
      TrieNode node = root;
      for (int i = 0; i < chars.length; i++) {
        int index = chars[i] - 'a';
        if (node.nexts[index] == null) {
          return 0;
        }
        node = node.nexts[index];
      }
      return node.end;
    }

    //查询以输入为前缀的单词出现过多少次
    public int prefixNumber(String pre) {
      if (StringUtils.isEmpty(pre)) {
        return 0;
      }
      char[] chars = pre.toCharArray();
      TrieNode node = root;
      for (int i = 0; i < chars.length; i++) {
        int index = chars[i] - 'a';
        if (node.nexts[index] == null) {
          return 0;
        }
        node = node.nexts[index];
      }
      return node.path;
    }

  }

  /**
   * 一块金条切成两半，是需要花费和长度数值一样的铜板的。比如 长度为20的 金条，不管切成长度多大的两半，都要花费20个铜 板。一群人想整分整块金 条，怎么分最省铜板？
   * 例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为 10+20+30=60. 金条要分成10,20,30三个部分。 如果， 先把长 度60的金条分成10和50，花费60
   * 再把长度50的金条分成20和30， 花费50 一共花费110铜板。 但是如果， 先把长度60的金条分成30和30，花费60 再把长度30 金条分成10和20，花费30 一共花费90铜板。
   * 输入一个数组，返回分割的最小代价。
   */
  //经典的哈夫曼编码问题
  public int lessMoney(int[] arr) {
    //1。首先构建一个小根堆
    //2.取出两个最小的数值相加，之后再放回堆中
    //3。重复步骤2，直到堆中只剩下一个数值，这个数值就是和
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    for (int i = 0; i < arr.length; i++) {
      queue.add(arr[i]);
    }
    int sum = 0;
    while (queue.size() > 1) {
      int temp = queue.poll() + queue.poll();
      sum += temp;
      queue.add(temp);
    }
    return sum;
  }

  /**
   * 给定两个数组，一个代表着成本，一个代表着收益 给定初始资金W，求在可以买k次的情况下，如何让收益最大化
   */
  class IPO {

    public class MinCostComparator implements Comparator<Project> {

      @Override
      public int compare(Project o1, Project o2) {
        return o1.cost - o2.cost;
      }

    }

    public class MaxProfitComparator implements Comparator<Project> {

      @Override
      public int compare(Project o1, Project o2) {
        return o2.profit - o1.profit;
      }

    }

    class Project {

      public int cost;
      public int profit;

      public Project(int cost, int profit) {
        this.cost = cost;
        this.profit = profit;
      }
    }

    public int findMaximizedCapital(int W, int k, int[] profits, int[] costs) {
      //0。将每一个cost和profit对应的生成一个项目
      //1。构建一个小根堆，标准是cost
      //2。0。构建一个大根堆，标准是profit
      //2。1。首先将能通过初始资金买到的项目，全部从小根堆放入大根堆
      //3。在大根堆中选取profit最大的一个项目，计算，更新初始资金
      //4。重复步骤2。1。，如果没有满足条件的，再从大根堆中去一个profit最大的项目
      //5。重复步骤4，直到到达k次，或者大根堆为空
      Project[] projects = new Project[costs.length];
      for (int i = 0; i < costs.length; i++) {
        projects[i] = new Project(costs[i], profits[i]);
      }

      PriorityQueue<Project> minCost = new PriorityQueue<>(new MinCostComparator());
      PriorityQueue<Project> maxProfit = new PriorityQueue<>(new MaxProfitComparator());
      for (int i = 0; i < projects.length; i++) {
        minCost.add(projects[i]);
      }

      for (int i = 0; i < k; i++) {
        while (!minCost.isEmpty() && minCost.peek().cost < W) {
          maxProfit.add(minCost.poll());
        }

        if (maxProfit.isEmpty()) {
          return W;
        }

        W = +maxProfit.poll().profit;
      }
      return W;
    }
  }

  /**
   * 一个数据流中，随时可以取得中位数
   */
  class MedianQuick {
    //思路：
    //构建两个堆，一个大根堆，一个小根堆
    //大根堆放比较小的数，顶点就是比较小的数中的最大值
    //小根堆放比较大的数，顶点就是比较大的数中的最小值
    //先往大根堆放，然后比较大小，来判断之后的元素进入大根堆还是小根堆
    //同时注意，再添加元素的时候，要保证两个堆的大小差距不超过1

    public class MaxHeapComparator implements Comparator<Integer> {

      @Override
      public int compare(Integer o1, Integer o2) {
        return o2 - o1;
      }
    }

    public class MinHeapComparator implements Comparator<Integer> {

      @Override
      public int compare(Integer o1, Integer o2) {
        return o1 - o2;
      }
    }

    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>(new MinHeapComparator());

    public void addNumber(int num) {
      if (maxHeap.isEmpty()) {
        maxHeap.add(num);
        return;
      }

      if (maxHeap.peek() >= num) {
        maxHeap.add(num);
      } else {
        //放入小根堆
        if (minHeap.isEmpty()) {
          minHeap.add(num);
          return;
        }
        if (minHeap.peek() > num) {
          maxHeap.add(num);
        } else {
          minHeap.add(num);
        }
      }

      modifyTwoHeapSize();
    }

    public void modifyTwoHeapSize() {
      if (maxHeap.size() == minHeap.size() + 2) {
        minHeap.add(maxHeap.poll());
      }
      if (maxHeap.size() + 2 == minHeap.size()) {
        maxHeap.add(minHeap.poll());
      }
    }

    public Integer getMedian() {
      int maxHeapSize = maxHeap.size();
      int minHeapSize = minHeap.size();

      if (maxHeapSize + minHeapSize == 0) {
        return null;
      }

      int maxHeapHead = maxHeap.peek();
      int minHeapHead = minHeap.peek();

      if (maxHeapSize == minHeapSize) {
        return (maxHeapHead + minHeapHead) / 2;
      }
      return maxHeapSize > minHeapSize ? maxHeapHead : minHeapHead;
    }
  }

  /**
   * 给定一个字符串类型的数组strs，找到一种拼接方式，使得把所
   * 有字 符串拼起来之后形成的字符串具有最低的字典序。
   */
  public String lowestString(String[] strs) {
    if (strs == null || strs.length ==0) {
      return "";
    }

    Arrays.sort(strs, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return (o1 + o2).compareTo(o2 + o1);
      }
    });
    String result = "";
    for (int i = 0; i < strs.length; i++) {
      result += strs[i];
    }
    return result;
  }

  /**
   * 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目
   * 的宣讲。 给你每一个项目开始的时间和结束的时间(给你一个数
   * 组，里面 是一个个具体的项目)，你来安排宣讲的日程，要求会
   * 议室进行 的宣讲的场次最多。返回这个最多的宣讲场次。
   */
  class BestArrange{
    public class Program{
      public int start;
      public int end;

      public Program(int start, int end) {
        this.start = start;
        this.end = end;
      }
    }

    //贪心算法：
    //比较谁最先结束
    public int bestArrange(Program[] programs, int start) {
      Arrays.sort(programs, new Comparator<Program>() {
        @Override
        public int compare(Program o1, Program o2) {
          return o1.end - o2.end;
        }
      });

      int result = 0;
      for (int i = 0; i < programs.length; i++) {
        if (start <= programs[i].end) {
          result++;
          start = programs[i].end;
        }
      }
      return result;
    }
  }

}
