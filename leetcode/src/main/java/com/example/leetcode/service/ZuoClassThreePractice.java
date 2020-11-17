package com.example.leetcode.service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author yuxiangzang
 * @create 2020-11-17-11:25 上午
 * @desc 左神第三个pdf练习题
 **/
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
      return array[size  -1];
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

}
