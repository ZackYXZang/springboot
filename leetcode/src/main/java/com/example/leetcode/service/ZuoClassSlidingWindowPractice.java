package com.example.leetcode.service;

import java.util.LinkedList;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2021-04-21-下午4:26
 * @desc 滑动窗口
 **/
@Service
public class ZuoClassSlidingWindowPractice {

  /**
   * 给定一个数组代表一个容器， 比如[3,1,2,4]， 代表0位置是一个宽度为1，高度为3的直方图。 代表1位置是一个宽度为1，高度为1的直方图。 代表2位置是一个宽度为1，高度为2的直方图。
   * 代表3位置是一个宽度为1，高度为4的直方图。 所有直方图的底部都在一条水平线上，且紧靠着。 把这个图想象成一个容器，这个容器可以装3格的水。 给定一个没有负数的数组arr，返回能装几格水？
   */
  //时间复杂度O(n^2)
  //每一个位置，遍历左面，得到leftMax；遍历右边，得到rightMax
  //然后计算每一个位置的储水量，累加
  public int getWater1(int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }

    int result = 0;
    for (int i = 1; i < array.length - 1; i++) {
      int leftMax = 0;
      int rightMax = 0;
      for (int l = 0; l < i; l++) {
        leftMax = Math.max(leftMax, array[l]);
      }
      for (int r = array.length - 1; r > i; r--) {
        rightMax = Math.max(rightMax, array[r]);
      }
      result += Math.max(0, Math.min(leftMax, rightMax) - array[i]);
    }
    return result;
  }


  //时间复杂度O(n)
  //空间复杂度O(n)
  //当查询比较麻烦的时候，使用预处理数组
  public int getWater2(int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }

    int result = 0;
    //这里辅助数组的长度，是array.length - 2，因为array的0位置和最后一个位置不会储存水
    int n = array.length - 2;
    int[] leftMax = new int[n];
    leftMax[0] = array[0];
    int[] rightMax = new int[n];
    rightMax[n - 1] = array[n + 1];
    //从开始位置到当前位置最大值，截止到源数组的倒数第三位
    for (int i = 1; i < n; i++) {
      leftMax[i] = Math.max(leftMax[i - 1], array[i]);
    }
    //从当前位置到最后位置最大值，截止到源数组的正数第三位
    for (int i = n - 2; i >= 0; i--) {
      rightMax[i] = Math.max(rightMax[i + 1], array[i + 2]);
    }

    for (int i = 1; i < array.length - 1; i++) {
      result += Math.max(0, Math.min(leftMax[i - 1], rightMax[i - 1]) - array[i]);
    }

    return result;

  }

  //时间复杂度O(n)
  //空间复杂度O(1)
  //接水问题的最优解
  public int getWater3(int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    int result = 0;
    //从l位置到左侧最大值
    int leftMax = array[0];
    //从r位置到右侧最大值
    int rightMax = array[array.length - 1];
    int l = 1;
    int r = array.length - 2;

    while (l <= r) {
      if (leftMax <= rightMax) {
        result += Math.max(0, leftMax - array[l]);
        leftMax = Math.max(leftMax, array[l]);
        l++;
      } else {
        result += Math.max(0, rightMax - array[r]);
        rightMax = Math.max(rightMax, array[r]);
        r--;
      }
    }
    return result;
  }

  /**
   * 给定一个数组arr，返回所有子数组的累加和中，最大的累加和
   */
  public int maxSum(int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    int max = Integer.MIN_VALUE;
    int curr = 0;
    for (int i = 0; i < array.length; i++) {
      curr += array[i];
      max = Math.max(max, curr);
      //max不负责记录cur变成0的时候是否是最大值
      curr = curr >= 0 ? curr : 0;
    }
    return max;
  }

  public int[] getMaxWindow(int[] array, int size) {
    if (array == null || array.length == 0 || array.length < size || size <= 0) {
      return null;
    }

    //窗口最大值/最小值更新结构
    //1. 使用双端链表（双向链表结构），保证从大到小的顺序
    //2. 假设窗口的两端为left，right
    //3. 每当right右移，就要把新的数加入双端链表，从后面加入，如果数进来后，不能保证从大到小的顺序，就把前面的数都弹出，直到保证这个从大到小的结构为止
    //4. 注意：如果新加进去的数和前一个相等，也要弹出前面的数，加入新的数
    //5. 每当left右移，判断双短链表头部的下标是否过期，如果过期，就从头部弹出，如果没有过期，就保持不动
    //6. 这里windowMax放入的是最大数对应数组中的角标
    LinkedList<Integer> windowMax = new LinkedList<>(); //1
    int[] result = new int[array.length - size + 1];
    int index = 0;

    for (int i = 0; i > array.length; i++) {
      //3、 4
      while (!windowMax.isEmpty() && array[i] <= windowMax.peekLast()) {
        windowMax.pollLast();
      }

      //6
      windowMax.addLast(i);
      //5
      if (windowMax.peekFirst() == i - size) {
        windowMax.pollFirst();
      }

      //如果i的位置比窗口的位置大的时候，此时开始得到当前窗口的最大值
      if (i >= size - 1) {
        result[index++] = windowMax.peekFirst();
      }
    }
    return result;
  }

  /**
   * 最大的leftMax与rightMax之差的绝对值
   * 【题目】
   * 给定一个长度为N（N>1）的整型数组arr，可以划分成左右两个
   * 部分，左部分为arr[0..K]，右部分为arr[K+1..N-1]，K可以取
   * 值的范围是[0,N-2]。求这么多划分方案中，左部分中的最大值
   * 减去右部分最大值的绝对值中，最大是多少？
   * 例如：[2,7,3,1,1]，当左部分为[2,7]，右部分为[3,1,1]时，
   * 左部分中的最大值减去右部分最大值的绝对值为4。当左部分为
   * [2,7,3]，右部分为[1,1]时，左部分中的最大值减去右部分最大
   * 值的绝对值为6。还有很多划分方案，但最终返回6。
   * 这个题就是接水问题的变形
   */
  public int maxAbs1(int[] array) {
    //本方法时间复杂度O(n^2)
    if (array == null || array.length == 0) {
      return 0;
    }

    int leftMax = 0;
    int rightMax = 0;
    int result = Integer.MIN_VALUE;
    for(int i = 0; i < array.length - 1; i++) {
      leftMax = Integer.MIN_VALUE;
      for (int l = 0; l <= i; l++){
        leftMax = Math.max(leftMax, array[l]);
      }
      rightMax = Integer.MIN_VALUE;
      for (int r = i + 1; r < array.length; r++) {
        rightMax = Math.max(rightMax, array[r]);
      }
      result = Math.max(result, Math.abs(leftMax - rightMax));
    }
    return result;
  }


  public int maxAbs2(int[] array) {
    //利用辅助数组
    if (array == null || array.length == 0) {
      return 0;
    }
    int[] leftMax = new int[array.length];
    leftMax[0] = array[0];
    int[] rightMax = new int[array.length];
    rightMax[array.length - 1] = array[array.length - 1];

    for (int i = 1; i < array.length; i++) {

    }
    return 0;
  }

}
