package com.example.leetcode.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yuxiangzang
 * @create 2021-04-27-上午10:27
 * @desc KMP算法
 **/
@Service
public class KMPAndManacherAndBFPRTAlgorithm {

  /**
   * KMP概念 1. 最长前/后缀相等时的长度 比如： A, A, A, A, B 在B的位置，长度就是3，因为不能包含自己本身长度，就是(AAAA)
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
   * 判断字符A是否包含字符B 如果是，返回开始包含的第一个位置，如果不是，返回-1
   */
  public int getIndexOf(String a, String b) {
    if (StringUtils.isEmpty(a) || StringUtils.isEmpty(b) || a.length() < b.length()) {
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
        //next[bIndex]位置上的值，正好是字符B从bIndex开始往前，最长的回文传的位置
        bIndex = next[bIndex];
      }
    }
    //bIndex走到最后，就是包含
    //走不到最后，同时也表示a走到了最后，那么就是不不包含
    return bIndex == bChars.length ? aIndex - bIndex : -1;
  }

  /**
   * Manacher算法，判断回文传的长度 时间复杂度O(n)
   *
   * @param str
   * @return
   */
  public static int maxLcpsLength(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }
    char[] charArr = manacherString(str);
    //用来记录每一个位置的回文传半径长度
    int[] pArr = new int[charArr.length];
    //index用来记录中心点 -> 取最右边界的时候的中心，也就是更新最右边界的时候更新中心点
    //对应下面的if (i + pArr[i] > pR)
    int index = -1;
    //pR用来记录有边界 -> 回文半径的最右边界
    int pR = -1;
    int max = Integer.MIN_VALUE;

    String maxStr = "";
    int result = 0;
    for (int i = 0; i != charArr.length; i++) {
      //通过i`得到i位置的回文半径
      //Math.min(pArr[2 * index - i], pR - i)表示：
      //首先，pArr[2 * index - i]表示，我们将pArr[2 * index - i]看成j位置的回文半径，pArr[j]，这里我们求的是i位置的回文半径，pArr[i]，同时j < i
      //假设j和i关于index对称，就会有index - i = j - index -> j = 2 * index - i，也就是pArr[2 * index - i]
      //如果i`位置的回文半径比i到最右边界大，那么i位置的回文半径就是最右边界pR - i的位置(pR - i)，并且此时不用继续往下了，也就是不用进while循环里
      //如果i`位置的回文半径比i到最右边界小，那么i位置的回文半径就是i`的回文半径，并且此时不用继续往下了，也就是不用进while循环里
      //如果i`位置的回文半径和i到最右边界相等，那么进入while循环，继续判断是不是往外扩

      //整体的意思是如果i在最右回文半径里，通过i`取到回文半径，如果不在就是1
      pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1;
      while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
        if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
        //更新i位置的回文半径
        {
          pArr[i]++;
        } else {
          break;
        }
      }

      //结束while后，如果大于右边界，扩展右边界，更新中心点
      // 否则中心点保持不变，i + 1移动
      if (i + pArr[i] > pR) {
        pR = i + pArr[i];
        index = i;
      }

      result += (pArr[i] / 2);
      System.out.println("result = " + result);
      if (max < pArr[i]) {
        System.out.println("i = " + i);
        System.out.println("max = " + max);
        System.out.println("pArr[i] = " + pArr[i]);

        int end = (i + pArr[i]) / 2;
        int start = end >=(i + 1 - pArr[i]) / 2 ? (i + 1 - pArr[i]) / 2 : end;
        maxStr = str.substring(start, end);
        System.out.println(maxStr);
      }
      max = Math.max(max, pArr[i]);

    }
//    for (int j = 0; j < pArr.length; j++) {
//      System.out.println(pArr[j]);
//    }
    return max - 1;
  }

  /**
   * 以防偶数的情况 构建一个str.length * 2 + 1的数组 并且使偶数位置为#，奇数位置为字符串本身
   *
   * @param str
   * @return
   */
  public static char[] manacherString(String str) {
    char[] charArr = str.toCharArray();
    char[] res = new char[str.length() * 2 + 1];
    int index = 0;
    for (int i = 0; i != res.length; i++) {
      res[i] = (i & 1) == 0 ? '#' : charArr[index++];
    }
    return res;
  }

}
