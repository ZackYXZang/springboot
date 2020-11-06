package com.example.leetcode.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.tree.TreeNode;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2020-09-01-7:51 下午
 * @desc 数组实现方法
 **/
@Service
public class ArrayServiceImpl {

  /**
   * 	1。二维数组中的查找
   * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
   * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
   * 判断数组中是否含有该整数。
   */
  public boolean find(int target, int [][] array) {
    if(array == null || array.length == 0 || array[0] == null || array[0].length == 0) {
      return false;
    }
    int rowStart = 0; //表示有多少行
    int rowEnd = array.length - 1;
    int colStart = 0;//表示每行有多少个数
    int colEnd = array[0].length - 1;

    //从右上角开始
    while (rowStart <= rowEnd && colStart <= colEnd) {
      int temp = array[rowStart][colEnd];
      if (temp == target) {
        return true;
      }

      if (temp < target) {
        rowStart++;
      }

      if (temp > target) {
        colEnd--;
      }
    }
    return false;
  }

  /**
   * 斐波那契数列
   * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0，第1项是1）。
   * n<=39
   */
  public int fibonacci(int n) {
    //0,1,1,2,3,5,8,13
    if (n == 0 ||n == 1) {
      return n;
    }
    int first = 0;
    int second = 1;

    while (n > 1) {
      int temp = second;
      second = second + first;
      first = temp;
      n--;
    }
    return second;
  }

  /**
   *调整数组顺序使奇数位于偶数前面
   *输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
   */
  public void reOrderArray(int [] array) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] % 2 == 1) {
        int temp = array[i];
        int j = 0;
        for(j = i - 1; j >= 0; j--) {
          if (array[j] % 2 == 0) {
            array[j + 1] = array[j];
          } else {
            break;
          }
        }
        array[j + 1] = temp;
      }
    }
  }

  /**
   * 顺时针打印出矩阵
   * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
   * 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
   * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
   */
  public ArrayList<Integer> printMatrix(int [][] matrix) {
    if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
      return new ArrayList<Integer>();
    }
    int rowStart = 0; //表示有多少行
    int rowEnd = matrix.length;
    int colStart = 0;//表示每行有多少个数
    int colEnd = matrix[0].length;

    ArrayList<Integer> result = new ArrayList<>();
    while (rowStart < rowEnd && colStart < colEnd) {

      if ((rowStart + 1 == rowEnd) && (colStart + 1 == colEnd)) {
        result.add(matrix[rowStart][colStart]);
        return result;
      }
      //一行
      if (rowStart + 1 == rowEnd) {
        for (int i = colStart; i < colEnd; i++) {
          result.add(matrix[rowStart][i]);
        }
        return result;
      }
      //一列
      if (colStart + 1 == colEnd) {
        for (int j = rowStart; j < rowEnd; j++) {
          result.add(matrix[j][colEnd - 1]);
        }
        return result;
      }

      for (int i = colStart; i < colEnd; i++) {
        result.add(matrix[rowStart][i]);
      }

      for (int j = rowStart + 1; j < rowEnd; j++) {
        result.add(matrix[j][colEnd - 1]);
      }

      for (int i = colEnd - 2; i >= colStart; i--) {
        result.add(matrix[rowEnd - 1][i]);
      }

      for (int j = rowEnd - 2; j > rowStart; j--) {
        result.add(matrix[j][colStart]);
      }

      rowStart++;
      rowEnd--;
      colStart++;
      colEnd--;
    }
    return result;
  }

  /**
   * 最小K个数
   * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
   */
  public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
    if (input == null || input.length < k || k == 0) {
      return new ArrayList<>();
    }

    //方法一：时间nk
    ArrayList<Integer> result = new ArrayList<>();
    int count = 0;
    while (count < k) {
      int min = Integer.MAX_VALUE;
      for (int i = 0; i < input.length; i++) {
        min = Math.min(min, input[i]);
      }
      result.add(min);
      count++;
    }

    //借助最大堆
    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        //通过比较方式定义最大堆
        return o2.compareTo(o1);
      }
    });

    for (int i = 0; i < input.length; i++) {
      if (maxHeap.size() < k) {
        maxHeap.offer(input[i]);
      } else if (maxHeap.peek() > input[i]) {
        maxHeap.poll();
        maxHeap.offer(input[i]);
      }
    }

    for (Integer value : maxHeap) {
      result.add(value);
    }

    return result;
  }

  /**
   * 把数组排成最小的数
   * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
   */
  public String PrintMinNumber(int [] numbers) {
    if (numbers == null || numbers.length == 0) {
      return "";
    }
    //确定数字的长度
    String str = "";
    String[] numbersStr = new String[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
      numbersStr[i] = numbers[i] + "";
      str = str + numbersStr[i];
    }
    int length = str.length();
    StringBuffer sb = new StringBuffer();
    List<String> list = new ArrayList<>();
    boolean[] used = new boolean[numbers.length];
    getNumberList(list, sb, numbersStr, length, used);
    String min = list.get(0);

    for (String s : list) {
      if (min.compareTo(s) > 0) {
        min = s;
      }
    }

    return min;

  }

  private void getNumberList (List<String> list, StringBuffer sb, String[] numbers, int length, boolean[] used) {
    if (sb.length() == length) {
      list.add(sb.toString());
      return;
    }

    for (int i = 0; i < numbers.length; i++) {
      if (!used[i]) {
        sb.append(numbers[i]);
        used[i] = true;
        getNumberList(list, sb, numbers, length, used);
        used[i] = false;
        sb.delete(sb.length() - numbers[i].length(), sb.length());
      }
    }
  }

  /**
   * 数组中的逆序对
   * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
   * 输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007
   */
  public int InversePairs(int[] array) {
    if (array == null || array.length < 2) {
      return 0;
    }
    return MergeSort(array, 0, array.length - 1);
  }


  public int MergeSort(int[] array, int l, int r) {
    if (l == r) {
      return 0;
    }
    int mid = l + ((r - l) >> 1);
    int left = MergeSort(array, l, mid);
    int right = MergeSort(array, mid + 1, r);
    int merge = merge(array, l, mid, r);
    int result = (left + right + merge) >  1000000007 ? (left + right + merge) % 1000000007 : left + right + merge;
//    System.out.println("resul1 = " + result);
    return result;
  }

  public int merge(int[] array, int l, int mid, int r) {
    int[] help = new int[r - l + 1];
    int i = 0;
    int leftIndex = l;
    int rightIndex = mid + 1;
    int count = 0;

    while (leftIndex <= mid && rightIndex <= r) {
      if (array[leftIndex] > array[rightIndex]) {
        count += mid - leftIndex + 1;
        if (count > 1000000007) {
          count = count % 1000000007;
        }
        help[i++] = array[rightIndex++];
      } else {
        help[i++] = array[leftIndex++];
      }
    }

    while (leftIndex <= mid) {
      help[i++] = array[leftIndex++];
    }

    while (rightIndex <= r) {
      help[i++] = array[rightIndex++];
    }

    for (i = 0; i < help.length; i++) {
      array[l + i] = help[i];
    }
    return count;
  }

  public int InversePairs2(int [] array) {
    if(array == null) {
      return 0;
    }
    int[] tmp = new int[array.length];
    return mergeSort(array, tmp, 0, array.length-1);
  }

  //归并排序，递归
  private int mergeSort(int[] array, int[] tmp, int low, int high) {
    if(low >= high) {
      return 0;
    }
    int res = 0, mid = low + (high - low) / 2;
    res += mergeSort(array, tmp, low, mid);
    res %= 1000000007;
    res += mergeSort(array, tmp, mid + 1, high);
    res %= 1000000007;
    res += merge(array, tmp, low, mid, high);
    res %= 1000000007;
//    System.out.println("result2 = " + res);
    return res;
  }

  //归并排序，合并
  private int merge(int[] array, int[] tmp, int low, int mid, int high) {
    int i = low, i1 = low, i2 = mid + 1;
    int res = 0;
    while(i1 <= mid && i2 <= high) {
      if(array[i1] > array[i2]) {
        res += mid - i1 + 1;
        res %= 1000000007;
        tmp[i++] = array[i2++];
      } else {
        tmp[i++] = array[i1++];
      }
    }
    while(i1 <= mid) {
      tmp[i++] = array[i1++];
    }
    while(i2 <= high) {
      tmp[i++] = array[i2++];
    }
    for (i = low; i <= high; i++) {
      array[i] = tmp[i];
    }
    return res;
  }

  /**
   * 	数字在排序数组出现的次数
   * 题目描述
   * 统计一个数字在升序数组中出现的次数。
   */
  public int GetNumberOfK(int [] array , int k) {
    if(array == null || array.length == 0) {
      return 0;
    }
    //方法一：暴力循环O(n)
//    int start = 0;
//    int end = array.length - 1;
//
//    if (k < array[start] || k > array[end]) {
//      return 0;
//    }
//
//    while (array[start] < k) {
//      start++;
//    }
//
//    while (array[end] > k) {
//      end--;
//    }
//
//    if (start <= end) {
//      return end - start + 1;
//    }
//    return 0;

    //方法二：二分法
    //寻找上下边界
    int start = 0;
    int end = array.length;
    if (k < array[start] || k > array[end - 1]) {
      return 0;
    }
    int leftBound = 0;
    int rightBound = 0;
    //寻找上边界
    while (start < end) {
      int mid = (start + end) /2;
      if(array[mid] <= k) {
        start = mid + 1;
      } else {
        end = mid;
      }
    }
    rightBound = start;
    System.out.println(rightBound);
    start = 0;
    end = array.length - 1;
    //下边界
    while (start < end) {
      int mid = (start + end) /2;
      if (array[mid] < k) {
        start = mid + 1;
      }else {
        end = mid;
      }
    }
    leftBound = start;
    System.out.println(leftBound);
    return rightBound - leftBound;
  }

  /**
   * 题目描述
   * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
   * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
   * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
   * [1,2,3,4,5,6,7],[3,2,4,1,6,5,7]
   */
  public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
    if (pre == null || pre.length == 0 || in == null || in.length == 0) {
      return null;
    }

    TreeNode root = reConstructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
    return root;
  }

  public TreeNode reConstructBinaryTree(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
    if (startPre > endPre ||startIn > endIn) {
      return null;
    }
    TreeNode root = new TreeNode(pre[startPre]);

    for(int i = startIn; i <= endIn; i++) {
      if(in[i] == root.val) {
        root.left = reConstructBinaryTree(pre, startPre + 1, startPre + (i - startIn), in, startIn, i - 1);
        root.right = reConstructBinaryTree(pre, startPre + (i - startIn) + 1, endPre, in, i + 1, endIn);
      }
    }
    return root;
  }
    class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
  }

  /**
   * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
   * @param array
   * @param sum
   * @return
   */
//  public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
//
//  }

  /**
   * 给定一个数组，求如果排序之后，相邻两数的最大差值，要求时
   * 间复杂度O(N)，且要求不能用非基于比较的排序。
   * @param arr
   * @return
   */
  public int MaxGap(int[] arr) {
    if(arr == null || arr.length < 2) {
      return 0;
    }

    //遍历数组，找到最大值和最小值，如果最大和最小值相等，那么证明数组的所有值都是相等的
    int length = arr.length;
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for(int i = 0; i < length; i++) {
      max = Math.max(max, arr[i]);
      min = Math.min(min, arr[i]);
    }
    if (max == min) {
      return 0;
    }

    //arr的长度是length，桶的长度是length + 1，那么桶一定有最少一个位置是空
    //所以差的最大值一定不会是在桶的同一个板子里
    //把arr等分，看成桶，记录每一个位置的最大值和最小值
    int[] maxs = new int[length + 1];
    int[] mins = new int[length + 1];
    //记录桶的每一个位置是否有位置
    boolean[] hasNumber = new boolean[length + 1];

    //把arr的数对应到桶上，找到桶上每个位置的最大值和最小值
    int bucketIndex = 0;
    for(int i = 0; i < length; i++) {
      bucketIndex = bucket(arr[i], length, min, max);
      mins[bucketIndex] = hasNumber[bucketIndex] ? Math.min(mins[bucketIndex], arr[i]) : arr[i];
      maxs[mins[bucketIndex]] = hasNumber[bucketIndex] ? Math.max(maxs[bucketIndex], arr[i]) : arr[i];
      hasNumber[bucketIndex] = true;
    }

    //遍历桶，用下一个板子的最小值，减去上一个板子的最大值，来算最大差值
    int result = 0;
    int lastMax = maxs[0];
    for (int i = 1; i < length; i++) {
      if (hasNumber[i]) {
        result = Math.max(result, mins[i] - lastMax);
        lastMax = maxs[i];
      }
    }
    return result;
  }

  //确定当前数属于桶上的哪个板子
  public int bucket(long num, long len, long min, long max) {
    return (int) ((num - min) * len/(max - min));
  }
}
