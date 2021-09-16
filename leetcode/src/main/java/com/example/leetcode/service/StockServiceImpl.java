package com.example.leetcode.service;

import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service("stockService")
public class StockServiceImpl {

  //总体思路就是动态规划，将买入和卖出分开考虑，写出对应的动态方程，对于指定交易多少次的，需要将buy，sell分别列出动态方程，考虑交易的次数，不限制次数的不需要考虑

  //121. 买卖股票的最佳时机
  //给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
  //你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
  //返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
  public int maxProfit1(int[] prices) {
    //[7,1,5,3,6,4]
    //5
    if (prices == null || prices.length == 0) {
      return 0;
    }

    //[ -6, 4, -2, 3, -2]
    int[] profits = new int[prices.length - 1];
    for (int i = 1; i < prices.length; i++) {
      profits[i - 1] = prices[i] - prices[i - 1];
    }

    int maxProfit = 0;
    int currentProfits = 0;
    for (int i = 0; i < profits.length; i++) {
      currentProfits += profits[i];
      maxProfit = Math.max(maxProfit, currentProfits);
      if (currentProfits < 0) {
        currentProfits = 0;
      }
    }

    return maxProfit;
  }


  //122.买卖股票的最佳时机 II
  //给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
  //设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
  //注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
  public int maxProfit2(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }
    //[7,1,5,3,6,4]  7
    ////[ -6, 4, -2, 3, -2]
//    int maxProfits = 0;
//    int[] profits = new int[prices.length - 1];
//    for (int i = 1; i < prices.length; i++) {
//      profits[i - 1] = prices[i] - prices[i - 1];
//      if (profits[i - 1] > 0) {
//        maxProfits += profits[i - 1];
//      }
//    }
//    return maxProfits;

    int[][] dp = new int[prices.length][2];
    //dp[i][0]表示第i交易后手里没有股票的最大利润
    //dp[i][1]表示第i交易后手里有股票的最大利润
    dp[0][0] = 0; //第一天没有买卖，或者买了又卖了
    dp[0][1] = -prices[0];//第一天买入，没有卖

    for (int i = 1; i < prices.length; i++) {
      //dp[i][0] = Max(dp[i - 1][0], dp[i - 1][1] + prices[i])
      //如果i位置的时候，手里没有股票，那么最大值要么是前一个位置手里没股票，且当前不操作，即取前一个手里没有股票的位置的最大利润值dp[i - 1][0]；
      //要么是前一个时候手中有股票，此时把它卖了，即取前一个手里有股票的位置的最大利润值dp[i - 1][1]，加上当前位置的股票价格prices[i]；
      //dp[i][1] = Max(dp[i - 1][1], dp[i - 1][0] - prices[i])
      //如果i位置的时候，手里有股票，那么最大值要么是前一个位置手里股票，且当前不操作，即取前一个手里有股票的位置的最大利润值dp[i - 1][1]；
      //要么是前一个时候手中没有股票，此时买入当前位置的股票，即取前一个手里没有股票的位置的最大利润值dp[i - 1][0]，减去当前位置的股票价格-prices[i]；
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
      dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
    }
    return dp[prices.length - 1][0];
  }

  //123. 买卖股票的最佳时机 III
  //给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
  //设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
  //注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
  public int maxProfit3(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }
    //将两笔交易分开看，分为buy1, sell1, buy2, sell2
    //在0的位置，buy1，buy2可以买入，也可以买入之后再卖，这样收益是0
    int buy1 = -prices[0];
    int sell1 = 0;
    int buy2 = -prices[0];
    int sell2 = 0;

    //动态方程：
    //buy1[i] = Max(buy1[i - 1], -prices[i])，第一次买入的最大值，要么是前一天的最大值，因为今天不操作，要么就是之前不操作，今天买入-prices[i]
    //sell1[i] = Max(sell1[i - 1], buy1[i - 1] + prices[i])，第一次卖出的最大值，要么就是前一天的最大值，今天不操作，要么就是到昨天为止，买入的最大值，加上今天卖出得到的最大值buy1[i - 1] + prices[i]
    //buy2[i] = Max(buy2[i - 1], sell1[i - 1]-prices[i])，第二次买入的最大值，要么是前一天的最大值，因为今天不操作，要么就是第一次卖出后得到的最大值，今天买入sell1[i - 1]-prices[i]
    //sell2 = Max(sell2[i - 1], buy2[i - 1] + prices[i])，第二次卖出的最大值，要么就是前一天的最大值，今天不操作，要么就是到昨天为止，第二次买入的最大值，加上今天卖出得到的最大值buy2[i - 1] + prices[i]
    for (int i = 1; i < prices.length; i++) {
      buy1 = Math.max(buy1, -prices[i]);
      sell1 = Math.max(sell1, buy1 + prices[i]);
      buy2 = Math.max(buy2, sell1 - prices[i]);
      sell2 = Math.max(sell2, buy2 + prices[i]);
    }
    return sell2;
  }

  //188. 买卖股票的最佳时机 IV
  //给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
  //设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
  //注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
  public int maxProfit4(int k, int[] prices) {

    if (prices == null || prices.length == 0) {
      return 0;
    }

    //因为一次交易至少涉及两天，所以如果k大于总天数的一半，就直接取天数一半即可，多余的交易次数是无意义的
    k = Math.min(prices.length / 2, k);
    int[][] buy = new int[prices.length][k + 1];
    int[][] sell = new int[prices.length][k + 1];

    //初始化，
    // 在buy方程中，除了buy[0][0]表示第一次买入的价格，从1～k次的买入都是不合法的
    // 在sell方程中，除了sell[0][0]表示第一次卖出的价格，从1～k次的买入都是不合法的
    buy[0][0] = -prices[0];
    sell[0][0] = 0;
    for (int i = 1; i <= k; i++) {
      buy[0][i] = Integer.MIN_VALUE / 2;
      sell[0][i] = Integer.MIN_VALUE / 2;
    }

    //动态方程
    //在买的动态方程中，在0位置上，buy[i][0]，要么是当前不动，取前一个位置的值buy[i - 1][0]，要么就是取前一个位置卖股票的最大利润，在本位置买入，sell[i - 1][0] - prices[i]
    //在买的方程中，从1～k位置上，buy[i][j]，要么是当前不动，取前一个位置的值buy[i-1][j]（表示在上一个股票的位置进行了k次交易取得的最大值），要么就是取前一个位置卖股票的最大利润，在本位置买入，sell[i - 1][j] - prices[i]
    //在卖的方程中，从1～k位置上，sell[i][j]，要么就是当前不动，取前一个位置的值sell[i-1][j]（表示在上一个股票的位置进行了k次交易取得的最大值），要么就是取上一个位置的买股票的最大值，在本位置卖出，注意是上一次交易，buy[i - 1][j - 1] + prices[i]
    for (int i = 1; i < prices.length; i++) {
      buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
      //没有意义，因为sell[i][0]表示在i位置进行了0共进行了0次交易，默认是0
//      sell[i][0] = Math.max(sell[i - 1][0], buy[i - 1][0 - 1] + prices[i]);
      for (int j = 1; j <= k; j++) {
        buy[i][j] = Math.max(buy[i - 1][j], sell[i - 1][j] - prices[i]);
        sell[i][j] = Math.max(sell[i - 1][j], buy[i - 1][j - 1] + prices[i]);
      }
    }

    return Arrays.stream(sell[prices.length - 1]).max().getAsInt();
  }

  //309. 最佳买卖股票时机含冷冻期
  //给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
  //设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
  //你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
  //卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
  public int maxProfit5(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }

    //dp[i][0]表示，今天过后手里有一只股票，要么是之前就持有，一直没有卖；要么是今天刚买的，如果是今天刚买的，就说明昨天不是冷冻期，所以是dp[i - 1][2] - prices[i]
    //dp[i][1]表示，今天过后处于冷冻期，且手里不持有股票，那么只有一种情况：今天卖了股票，这就意味着昨天一定要持有股票，即dp[i][0]
    //dp[i][2]表示，今天过后不处于冷冻期，且手里不持有股票；要么是昨天就已经不是冷冻期，今天不进行任何操作，要么是昨天是冷冻期，今天不进行任何操作
    int[][] dp = new int[prices.length][3];
    //dp[0][0]表示，在第一天手里持有一只股票，那么就是-prices[0]
    dp[0][0] = -prices[0];
    //dp[0][1]第一天结束后处于冷冻期，就是第一天卖了又卖，dp[0][1] = 0；
    //dp[0][2]没有意义，因为第一天不会处于冷冻期的情况，就是不进行任何操作，dp[0][2] = 0；

    for (int i = 1; i < prices.length; i++) {
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
      dp[i][1] = dp[i - 1][0] + prices[i];
      dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1]);
    }
    return Math.max(dp[prices.length - 1][1], dp[prices.length - 1][2]);
  }

  //714. 买卖股票的最佳时机含手续费
  //给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
  //你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
  //返回获得利润的最大值。
  //注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
  public int maxProfit6(int[] prices, int fee) {
    if (prices == null || prices.length == 0) {
      return 0;
    }

    int[][] dp = new int[prices.length][2];
    //dp[i][0]表示第i交易后手里没有股票的最大利润
    //dp[i][1]表示第i交易后手里有股票的最大利润
    dp[0][0] = 0; //第一天没有买卖，或者买了又卖了
    dp[0][1] = -prices[0];//第一天买入，没有卖

    for (int i = 1; i < prices.length; i++) {
      //dp[i][0] = Max(dp[i - 1][0], dp[i - 1][1] + prices[i])
      //如果i位置的时候，手里没有股票，那么最大值要么是前一个位置手里没股票，且当前不操作，即取前一个手里没有股票的位置的最大利润值dp[i - 1][0]；
      //要么是前一个时候手中有股票，此时把它卖了，即取前一个手里有股票的位置的最大利润值dp[i - 1][1]，加上当前位置的股票价格prices[i]，再减去交易的手续费；
      //dp[i][1] = Max(dp[i - 1][1], dp[i - 1][0] - prices[i])
      //如果i位置的时候，手里有股票，那么最大值要么是前一个位置手里股票，且当前不操作，即取前一个手里有股票的位置的最大利润值dp[i - 1][1]；
      //要么是前一个时候手中没有股票，此时买入当前位置的股票，即取前一个手里没有股票的位置的最大利润值dp[i - 1][0]，减去当前位置的股票价格-prices[i]；
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
      dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
    }
    return dp[prices.length - 1][0];
  }

}
