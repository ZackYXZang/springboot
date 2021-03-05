package com.example.leetcode.service;

import com.example.leetcode.utils.Node;
import com.example.leetcode.utils.RandomNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2020-11-17-11:25 上午
 * @desc 左神第三个pdf练习题
 **/
@Service("leetCode")
public class ZuoClassThreePractice {

  /**
   * 数组转栈
   */
  public static class ArrayToStack {
    int size;
    Integer[] array;

    public ArrayToStack(int initSize) {
      if (initSize < 0) {
        //initSize cannot smaller than 0
        return;
      }
      array = new Integer[initSize];
      size = 0;
    }

    public Integer peek() {
      if (size == 0) {
        //stack is empty
        return null;
      }
      return array[size - 1];
    }

    public void push(int element) {
      if (size == array.length) {
        //stack is full
        return;
      }
      array[size++] = element;
    }

    public Integer pop() {
      if (size == 0) {
        //stack is empty
        return null;
      }
      return array[--size];
    }
  }


  /**
   * 重点：当不好判断两个变量的关系时，可以增加一个变量，当作关联，化简关联关系
   * 数组转队列
   * 思路：
   * 坐标start标识队列头，end标识队尾，size标识当前队列有多少元素，size和start、end对立相关，
   * 也就是说，在push操作时，size和end同时加，在poll操作时，size减，start加，start和end没有关系
   * 当push元素的时候，只需要校验
   *    1. size是否和array的长度相等
   *    2. 坐标end是否到了队尾
   * 当poll元素当时候，只需要校验
   *    1.size是否为0
   *    2. 坐标start是否到了队尾
   */
  public static class ArrayToQueue {
    Integer[] array;
    int start;
    int end;
    int size;

    public ArrayToQueue(int initSize) {
      if (initSize < 0) {
        return;
      }
      start = 0;
      end = 0;
      size = 0;
      array = new Integer[initSize];
    }

    public void push(int element) {
      if (size == array.length) {
        //满了
        return;
      }
      size++;
      array[end] = element;
      end = end == array.length - 1 ? 0 : end + 1;
    }

    public Integer poll() {
      if (size == 0) {
        //空了
        return null;
      }
      size--;
      Integer temp = array[start];
      start = start == array.length - 1 ? 0 : start + 1;
      return temp;
    }

    public Integer peek() {
      if (size == 0) {
        //空了
        return null;
      }
      return array[start];
    }

  }

  /**
   * 最小栈
   */
  public static class MinStack {
    Stack<Integer> stack1;//正常存数，取数的栈
    Stack<Integer> stack2;//用来记录最小值的栈
    public MinStack() {
      this.stack1 = new Stack<>();
      this.stack2 = new Stack<>();
    }

    //每次push的时候，如果push的元素值比stack2栈顶的元素小/等的时候，stack2加入元素
    public void push(int element) {
      stack1.push(element);
      if (stack2.isEmpty()) {
        stack2.push(element);
      } else {
        if (element <= stack2.peek()) {
          stack2.push(element);
        }
      }
    }

    //每次pop的时候，如果pop的元素值等于stack2栈顶的元素的时候，stack2pop元素
    public Integer pop() {
      if (stack1.isEmpty()) {
        //空了
        return null;
      }
      int value = stack1.pop();
      if (value == stack2.peek()) {
        stack2.pop();
      }
      return value;
    }

    public Integer getMin() {
      if (stack2.isEmpty()) {
        //空了
        return null;
      }
      return stack2.peek();
    }
  }

  /**
   * 用栈实现队列
   */
  public static class StackToQueue {
    Stack<Integer> stack1;
    Stack<Integer> stack2;//用来做poll时候，返回元素的栈
    public StackToQueue() {
      this.stack1 = new Stack<>();
      this.stack2 = new Stack<>();
    }

    public void push(int element) {
      stack1.push(element);
    }

    public Integer poll() {
      if (stack1.isEmpty() && stack2.isEmpty()) {
        //两个都是空的
        return null;
      } else if (stack2.isEmpty()) {
        while (!stack1.isEmpty()) {
          stack2.push(stack1.pop());
        }
      }
      return stack2.pop();
    }

    public Integer peek() {
      if (stack1.isEmpty() && stack2.isEmpty()) {
        //两个都是空的
        return null;
      } else if (stack2.isEmpty()) {
        while (!stack1.isEmpty()) {
          stack2.push(stack1.pop());
        }
      }
      return stack2.peek();
    }

  }

  /**
   * 两个队列实现一个栈
   */
  public static class QueueToStack {
    Queue<Integer> queue1;
    Queue<Integer> queue2;

    public QueueToStack() {
      this.queue1 = new LinkedList<>();
      this.queue2 = new LinkedList<>();
    }

    public void push(int element) {
      queue1.add(element);
    }

    public Integer pop() {
      if (queue1.isEmpty()) {
        //空了
        return null;
      }
      while (queue1.size() > 1) {
        queue2.add(queue1.poll());
      }
      int value = queue1.poll();
      swap();
      return value;
    }

    public Integer peek() {
      if (queue1.isEmpty()) {
        //空了
        return null;
      }
      while (queue1.size() > 1) {
        queue2.add(queue1.poll());
      }
      int value = queue1.poll();
      queue2.add(value);
      swap();
      return value;
    }

    public void swap() {
      Queue<Integer> temp = queue1;
      queue1 = queue2;
      queue2 = temp;
    }

  }


  public int smallSum(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    int res = smallSumMerge(arr, 0, arr.length - 1);
    return res;
  }

  public int smallSumMerge(int[] arr, int start, int end) {
    if (start >= end) {
      return 0;
    }

    int mid = start + (end - start) / 2;
    return smallSumMerge(arr, start, mid) + smallSumMerge(arr, mid + 1, end) + smallSumMergeSub(arr, start, mid, end);
  }



  public int smallSumMergeSub(int[] arr, int start, int mid, int end) {
    int[] help = new int[end - start + 1];
    int helpIndex = 0;
    int l = start;
    int r = mid + 1;
    int res = 0;

    while (l <= mid && r <= end) {
      //这里用end - r + 1，因为用半部分此时已经是递增的了
      res += arr[l] < arr[r] ? (end - r + 1) * arr[l] : 0;
      help[helpIndex++] = arr[l] < arr[r] ? arr[l++] : arr[r++];
    }

    while (l <= mid) {
      help[helpIndex++] = arr[l++];
    }

    while (r <= end) {
      help[helpIndex++] = arr[r++];
    }

    for (int i = 0; i < help.length; i++) {
      arr[start + i] = help[i];
    }

    return res;
  }




  public static int merge(int[] arr, int start, int mid, int end) {
    int[] help = new int[end - start + 1];
    int i = 0;
    int p1 = start;
    int p2 = mid + 1;
    int res = 0;
    while (p1 <= mid && p2 <= end) {
      res += arr[p1] < arr[p2] ? (end - p2 + 1) * arr[p1] : 0;
      help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
    }
    while (p1 <= mid) {
      help[i++] = arr[p1++];
    }
    while (p2 <= end) {
      help[i++] = arr[p2++];
    }
    for (i = 0; i < help.length; i++) {
      arr[start + i] = help[i];
    }
    return res;
  }

  public int MaxGap(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < arr.length; i++) {
      max = Math.max(max, arr[i]);
      min = Math.min(min, arr[i]);
    }
    System.out.println("max :" + max);
    System.out.println("min :" + min);

    if (max == min) {
      return 0;
    }

    int[] maxArr = new int[arr.length + 1];
    int[] minArr = new int[arr.length + 1];
    boolean[] hasValue = new boolean[arr.length + 1];
    System.out.println("maxArr original: " + printArray(maxArr));
    System.out.println("minArr original: " + printArray(minArr));

    int bucketIndex = 0;
    for (int i = 0; i < arr.length; i++) {
      bucketIndex = getBucket(arr[i], arr.length, max, min);
      maxArr[bucketIndex] = hasValue[bucketIndex] ? Math.max(maxArr[bucketIndex], arr[i]) : arr[i];
      minArr[bucketIndex] = hasValue[bucketIndex] ? Math.min(minArr[bucketIndex], arr[i]) : arr[i];
      hasValue[bucketIndex] = true;
    }
    System.out.println("maxArr : " + printArray(maxArr));
    System.out.println("minArr : " + printArray(minArr));


    int res = 0;
    int lastMax = maxArr[0];
    for (int i = 1; i < maxArr.length; i++) {
      if (hasValue[i]) {
        res = Math.max(res, minArr[i] - lastMax);
        lastMax = maxArr[i];
      }
    }
    return res;
  }

  private int getBucket(int value, int length, int max, int min) {
    return (value - min) * length / (max - min);
  }


  /**
   * 转圈（顺时针）打印矩阵
   * @param matrix
   */
  public void printMatrix(int[][] matrix) {
    int rowStart = 0;
    int rowEnd = matrix.length - 1;
    int colStart = 0;
    int colEnd = matrix[0].length - 1;
    while (rowStart <= rowEnd && colStart <= colEnd) {
      print(matrix, rowStart++, rowEnd--, colStart++, colEnd--);
    }
  }

  public void print(int[][] matrix, int rowStart, int rowEnd, int colStart, int colEnd) {
    if(rowStart == rowEnd) {
      for (int i = colStart; i <= colEnd; i++) {
        System.out.println(matrix[rowStart][i] + "");
      }
    } else if (colStart == colEnd) {
      for (int i = rowStart; i <= rowEnd; i++) {
        System.out.println(matrix[i][colStart] + "");
      }
    } else {
      int currRow = rowStart;
      int currCol = colStart;

      while (currCol != colEnd) {
        System.out.println(matrix[rowStart][currCol++] + "");
      }
      while (currRow != rowEnd) {
        System.out.println(matrix[currRow++][colEnd] + "");
      }
      while (currCol != colStart) {
        System.out.println(matrix[rowEnd][currCol--] + "");
      }
      while (currRow != rowStart) {
        System.out.println(matrix[currRow--][colStart] + "");
      }
    }
  }

  /**
   * 顺时针旋转正方形矩阵
   * @param matrix
   */
  public void rotateMatrix(int[][] matrix) {
    int rowStart = 0;
    int rowEnd = matrix.length - 1;
    int colStart = 0;
    int colEnd = matrix[0].length - 1;
    while (rowStart < rowEnd ) {
      int length = rowEnd - rowStart;
      for (int i = 0; i < length; i++) {
        int temp = matrix[rowStart][colStart + i];
        matrix[rowStart][colStart + i] = matrix[rowEnd - i][colStart];
        matrix[rowEnd - i][colStart] = matrix[rowEnd][colEnd - i];
        matrix[rowEnd][colEnd - i] = matrix[rowStart + i][colEnd];
        matrix[rowStart + i][colEnd] = temp;
      }
      rowStart++;
      rowEnd--;
      colStart++;
      colEnd--;
    }
  }

  /**
   * 反转单向列表
   * @param head
   * @return
   */
  public Node reverseNodeList(Node head) {
    Node pre = null;
    Node next = null;

    while (head != null) {
      next = head.next;
      head.next = pre;
      pre = head;
      head = next;
    }
    return pre;
  }

  /**
   * 反转双向链表
   * @param head
   * @return
   */
  public DoubleNode reverseDoubleNodeList(DoubleNode head) {
    DoubleNode pre = null;
    DoubleNode next = null;

    while (head != null) {
      next = head.next;
      head.next = pre;
      head.last = next;
      pre = head;
      head = next;
    }
    return pre;
  }


  public void printZigMatrix(int[][] matrix) {
    int rowA = 0;
    int colA = 0;
    int rowB = 0;
    int colB = 0;
    int rowLength = matrix.length - 1;
    int colLength = matrix[0].length - 1;
    boolean print = false;
    List<Integer> result = new ArrayList<>();

    while (rowA <= rowLength) {
      printZigMatrixSub(matrix, rowA, colA, rowB, colB, print, result);
      rowA = colA == colLength ? rowA + 1 : rowA;
      colA = colA == colLength ? colA : colA + 1;
      colB = rowB == rowLength ? colB + 1 : colB;
      rowB = rowB == rowLength ? rowB : rowB + 1;
      print = !print;
    }

  }

  /**
   * 之字形打印矩阵
   * @param matrix
   * @param rowA
   * @param colA
   * @param rowB
   * @param colB
   * @param print
   * @param result
   */
  public void printZigMatrixSub(int[][] matrix, int rowA, int colA, int rowB, int colB, boolean print, List<Integer> result) {
    if (print) {
      while (rowA <= rowB) {
        result.add(matrix[rowA][colA]);
        System.out.print(matrix[rowA++][colA--] + ",");
      }
    } else {
      while (rowB >= rowA) {
        result.add(matrix[rowB][colB]);
        System.out.print(matrix[rowB--][colB++] + ",");
      }
    }
  }



  //链表题，可优化的地方是额外空间
  public Node getMid(Node head) {

    //method1
//    Node right = head.next;
//    Node curr = head;
//
//    while (curr.next != null && curr.next.next != null) {
//      right = right.next;
//      curr = curr.next.next;
//    }
//    return right;

    //method2
    //好处是，可以确定中点前一个位置的节点
    Node n1 = head;
    Node n2 = head;
    while (n2.next != null && n2.next.next != null) { // find mid node
      n1 = n1.next; // n1 -> mid
      n2 = n2.next.next; // n2 -> end
    }
    n2 = n1.next;
    return n2;
  }

  /**
   * 判断链表是否是回文链表
   * 时间复杂度为O(N), 空间复杂度为O(1)
   * @param head
   * @return
   */
  public boolean isPalindrome(Node head) {
    if (head == null || head.next == null){
      return false;
    }

    //慢指针
    Node node1 = head;
    //快指针
    Node node2 = head;
    while (node2.next != null && node2.next.next != null) {
      node1 = node1.next;
      node2 = node2.next.next;
    }

    //此时快指针改为指向中点，也就是node2的位置是中点的位置
    //奇数的时候，来到中间，偶数的时候，来到中点的前一个位置
    node2 = node1.next;

    //让链表中点前面的节点指向null，然后开始翻转后半部分链表
    node1.next = null;//node1 = pre
    Node node3 = null;//node3 = next

    while (node2 != null) {
      node3 = node2.next;
      node2.next = node1;
      node1 = node2;
      node2 = node3;
    }

    node3 = node1; //node3就是最后一个节点
    node2  = head;

    //check palindrome
    boolean result = true;
    while (node2 != null && node1 != null) {
      if (node1.value != node2.value) {
        result = false;
        break;
      }
      node1 = node1.next;
      node2 = node2.next;
    }

    //恢复链表后半部分
    node1 = null;
    while (node3 != null) {
      node2 = node3.next;
      node3.next = node1;
      node1 = node3;
      node3 = node2;
    }

    return result;
  }


  /**
   * 给定一个数，把链表按照小中大分类，且保持链表的稳定性（也就是按照远顺序排序）
   * @param head
   * @param pivot
   * @return
   */
  public Node listPartition(Node head, int pivot) {
    if (head == null) {
      return null;
    }

    Node lessStart = null;
    Node lessEnd = null;
    Node equalStart = null;
    Node equalEnd = null;
    Node moreStart = null;
    Node moreEnd = null;

    //按照小中大分类
    while (head != null) {
      if (pivot > head.value) {
        if (lessStart == null) {
          lessStart = head;
          lessEnd = head;
        } else {
          lessEnd.next = head;
          lessEnd = lessEnd.next;
        }
      } else if (pivot == head.value) {
        if (equalStart == null) {
          equalStart = head;
          equalEnd = head;
        } else {
          equalEnd.next = head;
          equalEnd = equalEnd.next;
        }
      } else {
        if (moreStart == null) {
          moreStart = head;
          moreEnd = head;
        } else {
          moreEnd.next = head;
          moreEnd = moreEnd.next;
        }
      }
      head = head.next;
    }

    //拼接
    if (lessEnd != null) {
      lessEnd.next = equalStart;
      equalEnd = equalEnd == null ? lessEnd : equalEnd;
    }

    if (equalEnd != null) {
      equalEnd.next = moreStart;
    }

    return lessStart != null ? lessStart : equalStart != null ? equalStart : moreStart;
  }

  /**
   * 复制含有随机指针节点的链表
   * @param head
   */

  public RandomNode copyRandomNodeList(RandomNode head) {
    if (head == null) {
      return null;
    }

    //1->2->3->4 变成：
    //1->1`->2->2`->3->3`->4->4`
    RandomNode node = head;
    while (node != null) {
      RandomNode copy = new RandomNode(node.value);
      RandomNode temp = node.next;
      node.next = copy;
      copy.next = temp;
      node = node.next.next;
    }

    //copy random point
    node = head;
    while (node != null) {
      node.next.rand = node.rand == null ? null : node.rand.next;
      node = node.next.next;
    }

    //split
    RandomNode newHead = head.next;
    node = newHead;
    while (node != null && node.next != null) {
      node.next = node.next.next;
      node = node.next;
    }

    return newHead;

  }


  /**
   * 两个单链表相交问题
   * @param head1
   * @param head2
   * @return
   */
  public Node getIntersectNode(Node head1, Node head2) {
    //1。判断是否有环，loop1，loop2表示两个链表入环的第一个节点或者null
    Node loop1 = getLoopNode(head1);
    Node loop2 = getLoopNode(head2);
    //2。判断两个无环链表相交
    if (loop1 == null && loop2 == null) {
      Node result = noLoopIntersect(head1, head2);
      return result;
    }
    //3。判断两个有环链表相交，包含三种情况
    //3。1 两个有环链表不相交
    //3。2 两个有环链表在环外相交
    //3。3 两个有环链表在环上相交
    if (loop1 != null && loop2 != null) {
      Node result = bothLoopIntersect(head1, head2, loop1, loop2);
      return result;
    }
    //4。一个有环，一个无环的链表不可能相交
    return null;
  }

  public Node bothLoopIntersect(Node head1, Node head2, Node loop1, Node loop2) {
    Node curr1 = null;
    Node curr2 = null;
    if (loop1 == loop2) {
      //3。2 两个有环链表在环外相交
      curr1 = head1;
      curr2 = head2;
      int length = 0;

      while (curr1 != loop1) {
        curr1 = curr1.next;
        length++;
      }
      while (curr2 != loop2) {
        curr2 = curr2.next;
        length--;
      }

      curr1 = length > 0 ? head1 : head2;
      curr2 = curr1 == head1 ? head2 : head1;
      length = Math.abs(length);
      while (length > 0) {
        curr1 = curr1.next;
        length--;
      }

      while (curr1 != curr2) {
        curr1 = curr1.next;
        curr2 = curr2.next;
      }
      return curr1;

    } else {
      //3。3 两个有环链表在环上相交
      curr1 = loop1.next;
      while (curr1 != loop1) {
        if (curr1 == loop2) {
          return loop1;
        }
        curr1 = curr1.next;
      }

      //3。1 两个有环链表不相交
      return null;
    }

  }

  //判断两个无环链表相交
  public Node noLoopIntersect(Node head1, Node head2) {
    if (head1 == null || head2 == null) {
      return null;
    }

    Node curr1 = head1;
    Node curr2 = head2;
    int length = 0;
    while (curr1.next != null) {
      curr1 = curr1.next;
      length++;
    }
    while (curr2.next != null) {
      curr2 = curr2.next;
      length--;
    }
    if (curr1 != curr2) {
      return null;
    }

    curr1 = length > 0 ? head1 : head2;
    curr2 = curr1 == head1 ? head2 : head1;
    length = Math.abs(length);
    while (length > 0) {
      curr1 = curr1.next;
      length--;
    }

    while (curr1 != curr2) {
      curr1 = curr1.next;
      curr2 = curr2.next;
    }
    return curr1;
  }

  public Node getLoopNode(Node head) {
    if (head == null || head.next == null || head.next.next == null) {
      return null;
    }

    Node slow = head.next;
    Node fast = head.next.next;
    while (slow != fast) {
      if (fast.next == null || fast.next.next == null) {
        //无环
        return null;
      }
      slow = slow.next;
      fast = fast.next.next;
    }

    //有环
    //让快指针回到原点
    fast = head;
    while (slow != fast) {
      slow = slow.next;
      fast = fast.next;
    }

    return slow;
  }


  public static Node getIntersectNode1(Node head1, Node head2) {
    if (head1 == null || head2 == null) {
      return null;
    }
    Node loop1 = getLoopNode1(head1);
    Node loop2 = getLoopNode1(head2);
    if (loop1 == null && loop2 == null) {
      return noLoop(head1, head2);
    }
    if (loop1 != null && loop2 != null) {
      return bothLoop(head1, loop1, head2, loop2);
    }
    return null;
  }

  public static Node getLoopNode1(Node head) {
    if (head == null || head.next == null || head.next.next == null) {
      return null;
    }
    Node n1 = head.next; // n1 -> slow
    Node n2 = head.next.next; // n2 -> fast
    while (n1 != n2) {
      if (n2.next == null || n2.next.next == null) {
        return null;
      }
      n2 = n2.next.next;
      n1 = n1.next;
    }
    n2 = head; // n2 -> walk again from head
    while (n1 != n2) {
      n1 = n1.next;
      n2 = n2.next;
    }
    return n1;
  }

  public static Node noLoop(Node head1, Node head2) {
    if (head1 == null || head2 == null) {
      return null;
    }
    Node cur1 = head1;
    Node cur2 = head2;
    int n = 0;
    while (cur1.next != null) {
      n++;
      cur1 = cur1.next;
    }
    while (cur2.next != null) {
      n--;
      cur2 = cur2.next;
    }
    if (cur1 != cur2) {
      return null;
    }
    cur1 = n > 0 ? head1 : head2;
    cur2 = cur1 == head1 ? head2 : head1;
    n = Math.abs(n);
    while (n != 0) {
      n--;
      cur1 = cur1.next;
    }
    while (cur1 != cur2) {
      cur1 = cur1.next;
      cur2 = cur2.next;
    }
    return cur1;
  }

  public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
    Node cur1 = null;
    Node cur2 = null;
    if (loop1 == loop2) {
      cur1 = head1;
      cur2 = head2;
      int n = 0;
      while (cur1 != loop1) {
        n++;
        cur1 = cur1.next;
      }
      while (cur2 != loop2) {
        n--;
        cur2 = cur2.next;
      }
      cur1 = n > 0 ? head1 : head2;
      cur2 = cur1 == head1 ? head2 : head1;
      n = Math.abs(n);
      while (n != 0) {
        n--;
        cur1 = cur1.next;
      }
      while (cur1 != cur2) {
        cur1 = cur1.next;
        cur2 = cur2.next;
      }
      return cur1;
    } else {
      cur1 = loop1.next;
      while (cur1 != loop1) {
        if (cur1 == loop2) {
          return loop1;
        }
        cur1 = cur1.next;
      }
      return null;
    }
  }



  public static class DoubleNode {
    public int value;
    public DoubleNode last;
    public DoubleNode next;

    public DoubleNode(int data) {
      this.value = data;
    }
  }



  public String printArray(int[] array){
    String result = "";
    for (int i = 0; i < array.length; i++) {
      result += array[i] + ",";
    }
    return result;
  }


}


