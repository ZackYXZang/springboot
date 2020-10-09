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
  public int InversePairs(int [] array) {
    int count = sort(array, 0, array.length - 1, 0);
    if (count > 1000000007) {
      count = count % 1000000007;
    }
    return count;
  }
  public static int sort(int[] array, int start, int end, int count) {
    //如果指针相遇，返回
    if (start >= end) {
      return count;
    }

    //递归
    //找到中间对索引
    int mid = (start + end) / 2;
    //对左边对数组进行递归
    int left = sort(array, start, mid, count);
    //对右边对数组进行递归
    int right = sort(array, mid + 1, end, count);

    //开始计算逆数对
    int i = mid;
    int j = end;

    while (i >= start && j > mid) {
      if (array[i] > array[j]) {
        System.out.println("array[i] = " + array[i]);
        System.out.println("array[j] = " + array[j]);
        count += j - mid;
//        if (count > 1000000007) {
//          count = count % 1000000007;
//        }
        System.out.println("count = " + count);
        i--;
      } else {
        j--;
      }
    }
    //合并
    merge(array, start, mid, end);
    return left + right + count;
  }

  public static void merge(int[] array, int left, int mid, int right) {

    //临时数组，用来储存有序的结果
    int[] tempArray = new int[array.length];
    //临时数组的开始是左数组的第一个元素的位置
    int tempIndex = left;

    //右数组的第一个元素的索引
    int rightStart = mid + 1;
    //将排序后的临时数组赋值会原数组
    int copyIndex = left;

    //在两个数组中选择最小的放入临时数组
    while (left <= mid && rightStart <= right) {
      if (array[left] <= array[rightStart]) {
        tempArray[tempIndex++] = array[left++];
      } else {
        tempArray[tempIndex++] = array[rightStart++];
      }
    }

    //将剩余不分一次放入临时数组（两个while只会执行其中一个）
    while (left <= mid) {
      tempArray[tempIndex++] = array[left++];
    }
    while (rightStart <= right) {
      tempArray[tempIndex++] = array[rightStart++];
    }
    //将临时数组中的内容拷贝回原数组中
    while(copyIndex <= right) {
      array[copyIndex] = tempArray[copyIndex++];
    }

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

}
