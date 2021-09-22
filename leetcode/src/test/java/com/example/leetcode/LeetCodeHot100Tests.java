package com.example.leetcode;


import com.example.leetcode.service.KMPAndManacherAndBFPRTAlgorithm;
import com.example.leetcode.service.LeetCodeHot100;
import com.example.leetcode.service.LeetCodeHot100.ListNode;
import com.example.leetcode.service.StockServiceImpl;
import com.example.leetcode.service.ZuoClassSlidingWindowPractice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yuxiangzang
 * @create 2020-10-23-2:49 下午
 * @desc
 **/
@SpringBootTest
public class LeetCodeHot100Tests {

  @Autowired
  private LeetCodeHot100 leetCodeHot;

  @Autowired
  private KMPAndManacherAndBFPRTAlgorithm kmpAndManacherAndBFPRTAlgorithm;

  @Autowired
  private StockServiceImpl stockService;

  @Test
  void contextLoads() throws Exception {
    test();
  }


  public void test() {
    //[1,3,-1,-3,5,3,6,7]
    //3
    int[] nums = new int[]{1,1,1,1,1};
    //[['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','1','1','1','1']]
    char[][] board = new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'},
        {'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}};
    leetCodeHot.findTargetSumWays(nums,3);


    //j = 1 nums[1] = 3
    //nums[i] > j -> true ->dp[1][1] = dp[0][1]
    //dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
    //dp[1][1] = dp[0][1] ||

    //[[true, true, false, false, false, false, false],
    // [true, true, false, true, true, false, false],
    // [true, true, false, true, true, true, false],
    // [true, true, false, true, true, true, false]]

    //[[true, true, false, false, false, false, false],
    // [true, false, false, true, true, false, false],
    // [true, false, true, false, true, false, false],
    // [true, false, false, false, true, false, true]]

  }



  class Solution {
    //解题思路：把grid的每一个位置变成0，1，2，3，4，5，。。。。；
    //同时查出位置上是1这个点的个数；
    //运用unionfind，把每一个值为1的点与相邻的值为1的点连上同时count--；
    //如果之前已经访问过了，此时她们的father是一样的，就不会再有count--；

    public int numIslands(char[][] grid) {
      if (grid.length == 0 || grid == null || grid[0].length == 0 || grid[0] == null) {
        return 0;
      }
      int m = grid[0].length;
      int n = grid.length;
      int count = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (grid[i][j] == '1') {
            count++;
          }
        }
      }

      System.out.println("totail = " + count);
      UnionFind union_find = new UnionFind(m*n, count);

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (grid[i][j] == '1') {
            if (i > 0 && grid[i - 1][j] == '1') {
              union_find.connect(i * m + j, (i - 1) * m + j);
              //System.out.println("i = " + count);
            }
            if (i < n - 1 && grid[i + 1][j] == '1') {
              union_find.connect(i * m + j, (i + 1) * m + j);
              //System.out.println("i = " + count);
            }
            if (j > 0 && grid[i][j - 1] == '1') {
              union_find.connect(i * m + j, i * m + j - 1);
              //System.out.println("j = " + union_find.query());
            }
            if (j < m - 1 && grid[i][j + 1] == '1') {
              union_find.connect(i * m + j, i * m + j + 1);
              //System.out.println(union_find.query());
            }
          }
        }
      }
      return union_find.query();

    }

  }

  class UnionFind {
    private int[] father;
    private int count;
    public int find (int x) {
      if (father[x] == x) {
        return x;
      }
      father[x] = find(father[x]);
      return father[x];
    }


    public UnionFind(int n, int count) {
      this.count = count;
      father = new int[n];
      for (int i = 0; i < n; i++) {
        father[i] = i;
      }
    }

    public void connect (int a, int b) {
      int root_a = find(a);
      int root_b = find(b);
      if (root_a != root_b) {
        father[root_a] = root_b;
        count--;
      }
    }


    public int query() {

      return count;
    }
  }



}
