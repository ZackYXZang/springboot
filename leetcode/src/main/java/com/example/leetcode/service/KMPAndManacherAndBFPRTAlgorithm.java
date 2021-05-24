package com.example.leetcode.service;

import org.springframework.util.StringUtils;

/**
 * @author yuxiangzang
 * @create 2021-04-27-上午10:27
 * @desc KMP算法
 **/
public class KMPAndManacherAndBFPRTAlgorithm {

  /**
   * 概念
   * 1. 最长前/后缀相等时的长度
   *  比如： A, A, A, A, B
   *  在B的位置，长度就是3，因为不能包含自己本身长度，就是(AAAA)
   */
  //这个next数组，每个位置的值代表：
  // 对应原字符串从位置i往前看最长前缀相等时的长度
  //[a,  a, b, a, b]
  //[-1, 0, 1, 0, 1]
  //规定0位置值为-1，1位置值为0
  public int[] getNextArray(char[] array) {
    if (array == null || array.length == 0) {
      return new int[]{-1};
    }

    int[] next = new int[array.length];
    next[0] = -1;
    next[1] = 0;
    //这个表示：在pos位置，求前面的最长前/后缀的长度值
    //重点是：前面的位置，也就是5位置，记录的是0～4位置中的最长前/后缀
    int pos = 2;
    //这个表示：在pos前面那个字符，得到的最长前/后缀的值，
    //同时这个数值也等于最长前缀长度的下一个角标
    int cn = 0;

    while (pos < array.length) {
      if (array[pos - 1] == array[cn]) {
        next[pos] = ++cn;
        pos++;
      } else if (cn > 0) {
        cn = next[cn];
      } else {
        next[pos] = 0;
        pos++;
      }
    }

    return next;
  }

  /**
   * 判断字符A是否包含字符B
   * 如果是，返回开始包含的第一个位置，如果不是，返回-1
   */
  public int getIndexOf(String a, String b) {
    if (StringUtils.isEmpty(a) || StringUtils.isEmpty(b) || a.length() <  b.length()) {
      return -1;
    }

    char[] aChars = a.toCharArray();
    char[] bChars = b.toCharArray();
    int[] next = getNextArray(bChars);
    int aIndex = 0;
    int bIndex = 0;

    while (aIndex < aChars.length && bIndex < bChars.length) {
      if (aChars[aIndex] == bChars[bIndex]) {
        aIndex++;
        bIndex++;
      } else if (next[bIndex] == -1) {
        //此时bIndex = 0；也就是字符B的开始位置
        //能进入这个判断就证明着aChars[aIndex] != bChars[0]
        //所以aIndex++，就是要从下一个位置开始重新判断
        aIndex++;
      } else {
        bIndex = next[bIndex];
      }
    }
    //bIndex走到最后，就是包含
    //走不到最后，同时也表示a走到了最后，那么就是不不包含
    return bIndex == bChars.length ? aIndex - bIndex : -1;
  }



}
