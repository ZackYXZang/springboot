package com.example.leetcode.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yuxiangzang
 * @create 2021-04-06-下午4:47
 * @desc 动态规划
 **/
@Service
public class ZuoClassDPPractice {

  /**
   * 求n!
   *
   * @param n
   * @return
   */
  public int getFactorial(int n) {
    //f(n) = n * f(n - 1)
    if (n == 0 || n == 1 || n == 2) {
      return n;
    }
    int result = 1;
    for (int i = 2; i <= n; i++) {
      result = result * n;
    }
    return result;
  }

  /**
   * 汉诺塔问题 打印n层汉诺塔从最左边移动到最右边的全部过程
   */
  public void hanoi(int n) {
    if (n > 0) {
      func(n, n, "left", "mid", "right");
    }
  }

  public void func(int rest, int down, String from, String mid, String to) {
    if (rest == 1) {
      System.out.println("move " + down + " from " + from + " to " + to);
    } else {
      func(rest - 1, down - 1, from, to, mid);
      func(1, down, from, mid, to);
      func(rest - 1, down - 1, mid, from, to);
    }
  }

  /**
   * 打印一个字符串的全部子序列，包括空字符串
   */
  public void printAllSubsquence(String str) {
    if (StringUtils.isEmpty(str)) {
      return;
    }

    process(str.toCharArray(), 0, "");

  }

  public void process(char[] chars, int i, String result) {
    if (i == chars.length) {
      System.out.println(result);
      return;
    }

    process(chars, i + 1, result);
    process(chars, i + 1, result + chars[i]);
  }


  /**
   * 打印一个字符串的全部排列
   *
   * @param nums
   * @return
   */
  public List<List<Integer>> permute(int[] nums) {
    if (nums == null || nums.length == 0) {
      return null;
    }
    //需要一个用来记录之前走没走过的辅助数组
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, result, new ArrayList<Integer>());
    return result;
  }

  public void backtrack(int[] nums, List<List<Integer>> result, List<Integer> helper) {
    if (helper.size() == nums.length) {
      result.add(helper);
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      if (helper.contains(nums[i])) {
        continue;
      }
      helper.add(nums[i]);
      backtrack(nums, result, helper);
      //如果不删除最后一位，那么递归时，最初是的for循环只能执行第一个元素
      helper.remove(helper.size() - 1);
    }
  }


  /**
   * 打印一个字符串的全部排列，要求不要出现重复的排列
   *
   * @param nums
   * @return
   */
  public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
    return list;
  }

  private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums,
      boolean[] used) {
    if (tempList.size() == nums.length) {
      list.add(new ArrayList<>(tempList));
    } else {
      for (int i = 0; i < nums.length; i++) {
        if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
          //举例[1, 1, 2]
          //第一次循环递归完，得到[1, 1, 2][1, 2, 1]
          //第二次递归是从i = 1开始，此时use[0] = false，满足i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
          //此时我们要跳过，如果不跳过，就会再次得到[1, 2, 1]
          continue;
        }
        used[i] = true;
        tempList.add(nums[i]);
        backtrack(list, tempList, nums, used);
        used[i] = false;
        tempList.remove(tempList.size() - 1);
      }
    }
  }

  /**
   * 母牛每年生一只母牛，新出生的母牛成长三年后也能每年生一只 母牛，假设不会死。求N年后，母牛的数量。
   */
  public Integer cowNumber(int n) {
    //f(n) = f(n - 1) + f(n - 3)
    if (n < 1) {
      return 0;
    }
    if (n == 1 || n == 2 || n == 3) {
      return n;
    }

    //解法1：递归
//    return cowNumber(n - 1) + cowNumber(n - 3);

    //解法2，循环
    int res = 3;
    int pre = 2;
    int prepre = 1;
    int tmp1 = 0;
    int tmp2 = 0;
    for (int i = 4; i <= n; i++) {
      tmp1 = res;
      tmp2 = pre;
      res = res + prepre;
      pre = tmp1;
      prepre = tmp2;
    }
    return res;
  }

  /**
   * 如果每只母牛只能活10年，求N年后，母牛的数量。
   */
  public Integer cowNumber2(int n) {
    //f(n) = f(n - 1) + f(n - 3) - f(n - 10)

    if (n < 1) {
      return 0;
    }
    if (n == 1 || n == 2 || n == 3) {
      return n;
    }
    return cowNumber(n - 1) + cowNumber(n - 3) - cowNumber(n - 10);
  }

  /**
   * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能 使用递归函数。如何实现？
   */
  public void reverse(Stack<Integer> stack) {
    //解体思路：递归套递归
    if (stack.isEmpty()) {
      return;
    }
    int last = getAndRemoveLastElement(stack);
    reverse(stack);
    stack.push(last);
  }

  public Integer getAndRemoveLastElement(Stack<Integer> stack) {
    int result = stack.pop();
    if (stack.isEmpty()) {
      return result;
    } else {
      int last = getAndRemoveLastElement(stack);
      stack.push(result);
      return last;
    }
  }

  /**
   * 如何根据暴力递归写出DP：
   * 1、分析可变参数，建立dp表空间，同时判断是否有后效性
   *    后效性：比如在i这个位置的值为5，无论前面怎么选择，只要这个位置是5，同时选择又对后面没有影响，那么就是无后效性
   * 2、确定最终状态
   * 3、根据base case确定初始状态
   * 4、分析一个普遍位置依赖那些位置
   * 5、根绝依赖关系顺序逆序求整个表
   */


  /**
   * 给你一个二维数组，二维数组中的每个数都是正数，要求从左上
   * 角走到右下角，每一步只能向右或者向下。沿途经过的数字要累
   * 加起来。返回最小的路径和。
   */
  public int minPath(int[][] matrix) {
    //f[i][j] = min(f[i-1][j], f[i][j-1]) + f[i][j]
    if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
      return 0;
    }
    int row = matrix.length;
    int col = matrix[0].length;
    int[][] dp = new int[row][col];
    dp[0][0] = matrix[0][0];

    //初始化边界
    for (int i = 1; i < row; i++) {
      dp[i][0] = matrix[i][0] + dp[i - 1][0];
    }
    for (int i = 1; i < col; i++) {
      dp[0][i] = matrix[0][i] + dp[0][i - 1];
    }

    //从起点到当前路径的最短距离 = 上一个和左一个的最小值，加上当前位置的值
    for (int i = 1; i < row; i++) {
      for(int j = 1; j < col; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
      }
    }
    return dp[row - 1][col - 1];
  }

  /**
   * 给你一个数组arr，和一个整数aim。如果可以任意选择arr中的
   * 数字，能不能累加得到aim，返回true或者false
   */
  public boolean money(int[] arr, int aim) {
    if(arr == null || arr.length == 0) {
      return false;
    }

    //初始化dp矩阵，高度为数据长度 + 1，宽度为aim + 1
    //且最后一列都为true
    //最终返回结果为dp[0][0]
    boolean[][] dp = new boolean[arr.length + 1][aim + 1];
    for (int i = 0; i < dp.length; i++) {
      dp[i][aim] = true;
    }

    for(int i = arr.length - 1; i >= 0; i--) {
      for (int j = aim - 1; j >= 0; j--) {
        //dp[i][j]表示：j加上arr[i]的和，是否能最终得到aim
        //dp[i + 1][j]表示：j加上arr[i + 1]的和，是否能最终得到aim
        //如果dp[i + 1][j]为true（能达到），那么哪怕不加上自己arr[i]也能达到
        dp[i][j] = dp[i + 1][j];
        if(j + arr[i] <= aim) {
          //dp[i + 1][j + arr[i]]表示：用j加上arr[i]，是否能得到aim
          dp[i][j] = dp[i][j] || dp[i + 1][j + arr[i]];
        }
      }
    }
    return dp[0][0];
  }

  /**
   * 给定两个数组w和v，两个数组长度相等，w[i]表示第i件商品的重量
   * v[i]表示第i件商品的价值。
   * 再给定一个整数bag，要求你挑选商品的重量加起来一定不能超
   * 过bag，返回满足这个条件下，你能获得的最大价值。
   */
  public int maxValue(int[] weight, int[] value, int bag) {
    if(weight == null || weight.length == 0 || value == null || value.length == 0 || bag == 0) {
      return 0;
    }

    int[][] dp = new int[weight.length + 1][bag + 1];
    for (int i = weight.length - 1; i >= 0; i--) {
      for (int j = bag - 1; j >= 0; j--) {
        dp[i][j] = dp[i + 1][j];
        if (j + weight[i] <= bag) {
          dp[i][j] = Math.max(dp[i][j], dp[i + 1][j + weight[i]]);
        }
      }
    }
    return dp[0][0];
  }



}
