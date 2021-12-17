package com.example.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeetcodeApplicationTests {

  public class TreeLinkNode {

    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
      this.val = val;
    }
  }


  public class ListNode {

    int val;
    ListNode next = null;

    ListNode(int val) {
      this.val = val;
    }
  }

  public class RandomListNode {

    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
      this.label = label;
    }
  }

  public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  @Test
  void contextLoads() throws Exception {
    //树的遍历
    //前序遍历   根左右
    //中序遍历   左根右
    //后序遍历   左右根
    //给定前序遍历和中序遍历，可以确定唯一的二叉树
    //{7,1,12,0,4,11,14,#,#,3,5}

    //[10,2,6]
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
//    System.out.println(KthNode(root, 3));

    //[-1,3,2,1,-2,-2,-3,0,3,2,1,-1]
    //[0,3,2,1]
    //[3,2,1,-2,-2,-3,0,3,2,1]
    int[] array = new int[]{10,9,2,5,3,7,21,18};
//    System.out.println(VerifySquenceOfBST(array));

    //[[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]]
    //[1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10]
    // 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10,
    int[][] matrix = new int[][]{{1, 2, 3, 4}/*,{5,6,7,8},{9,10,11,12},{13,14,15,16}*/};
    ListNode listNode = new ListNode(0);
    listNode.next = new ListNode(1);
    listNode.next.next = new ListNode(2);
    listNode.next.next.next = new ListNode(3);
    listNode.next.next.next.next = new ListNode(4);
//    while (listNode != null) {
//      System.out.println(listNode.val);
//      listNode = listNode.next;
//    }


    int[][] people = new int[][] {{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};

//    getNums(new int[]{1,2,3,4,5});
  }


  public int getProfits(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }

    //交易一次
//    int minPrice = Integer.MAX_VALUE;
//    int maxProfit = 0;
//    //只能买卖一次
//    for (int i =0; i < prices.length; i++) {
//      if (minPrice > prices[i]) {
//        minPrice = prices[i];
//      }
//
//      maxProfit = Math.max(maxProfit, prices[i] - minPrice);
//    }
//    return maxProfit;

    //交易多次，动态规划，两个状态：手里有股票，手里没股票
//    int[][] dp = new int[prices.length][2];
//    dp[0][0] = 0; //手里没有股票
//    dp[0][1] = -prices[0]; //手里有股票
//
//    for (int i = 1; i < prices.length; i++) {
//      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
//      dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
//    }
//
//    return dp[prices.length - 1][1];

    //至多交易两笔
//    int buy1 = -prices[0];
//    int sell1 = 0;
//    int buy2 = -prices[0];
//    int sell2 = 0;
//
//    for (int i = 1;i < prices.length; i++) {
//      buy1 = Math.max(buy1, -prices[i]);
//      sell1 = Math.max(sell1, buy1 + prices[i]);
//      buy2 = Math.max(buy2, sell1 - prices[i]);
//      sell2 = Math.max(sell2, buy2 + prices[i]);
//    }
//
//    return sell2;

    //至多交易k笔
//    int k = 10;
//    int[][] buy = new int[prices.length][k + 1];
//    int[][] sell = new int[prices.length][k + 1];
//
//    //初始化
//    buy[0][0] = -prices[0];
//    sell[0][0] = 0;
//    for (int i = 1; i <= k; i++) {
//      buy[0][i] = Integer.MIN_VALUE / 2;
//      sell[0][i] = Integer.MIN_VALUE / 2;
//    }
//
//    for (int i = 1; i < prices.length; i++) {
//      buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
//
//      for (int j = 1; j <= k; j++) {
//        buy[i][j] = Math.max(buy[i - 1][j], sell[i - 1][j] - prices[i]);
//        sell[i][j] = Math.max(sell[i - 1][j], buy[i - 1][j - 1] + prices[i]);
//      }
//    }
//
//    return Arrays.stream(sell[prices.length - 1]).max().getAsInt();

    //交易多次，但是含有冷冻期，冷冻期为1天
    //动态规划：今天过后手里有一个股票；今天过后处于冷冻期，且手里没有股票；今天过后不处于冷冻期，且手里没有股票
//    int[][] dp = new int[prices.length][3];
//
//    dp[0][0] = -prices[0];
//    dp[0][1] = 0;
//    dp[0][2] = 0;
//
//    for (int i = 1; i < prices.length; i++) {
//      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
//      dp[i][1] = dp[i - 1][0] + prices[i]; //因为处理冷冻期，一定是昨天卖了
//      dp[i][2] = Math.max(dp[i - 1][1], dp[i - 1][2]);
//    }
//
//    return Math.max(dp[prices.length - 1][1], dp[prices.length - 1][2]);

    //交易多次，含有手续费
    //动态规划，卖的时候扣除手续费；两个状态，手里没有股票，手里有股票
    int fee = 10;//手续费
    int[][] dp = new int[prices.length][2];
    dp[0][0] = 0;
    dp[0][1] = -prices[0];

    for (int i = 1; i < prices.length; i++) {
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
      dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[0]);
    }

    return dp[prices.length - 1][1];

  }



  public int getWater(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    int leftMax = nums[0];
    int rightMax = nums[nums.length - 1];
    int l = 1;
    int r = nums.length - 2;
    int result = 0;

    while (l <= r) {
      if (leftMax <= rightMax) {
        result += Math.max(0, leftMax - nums[l]);
        leftMax = Math.max(leftMax, nums[l]);
        l++;

      } else {
        result += Math.max(0, rightMax - nums[r]);
        rightMax = Math.max(rightMax, nums[r]);
        r--;
      }
    }

    return result;
  }

  public int getPath(TreeNode root, int target) {
    if (root == null) {
      return 0;
    }

    Map<Long, Integer> map = new HashMap<>();
    map.put(0l, 1);
    return getPathSub(root, target, map, 0l);
  }

  public int getPathSub(TreeNode root, int target, Map<Long, Integer> map, long curr) {
    if (root == null) {
      return 0;
    }

    int result = 0;
    curr += root.val;
    result = map.getOrDefault(curr - target, 0);
    map.put(curr, map.getOrDefault(curr, 0) + 1);
    result += getPathSub(root.left, target, map, curr);
    result += getPathSub(root.right, target, map, curr);
    map.put(curr, map.getOrDefault(curr, 0) - 1);
    return result;
  }
}








