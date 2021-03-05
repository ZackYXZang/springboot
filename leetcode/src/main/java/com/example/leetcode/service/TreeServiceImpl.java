package com.example.leetcode.service;

import com.example.leetcode.utils.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2020-11-11-3:18 下午
 * @desc 树相关方法，左神PDF4
 **/
@Service("treeService")
public class TreeServiceImpl {
  /**
   *          1
   *     2        3
   *   4   5    6   7
   */

  //前序遍历，遍历顺序，根左右
  //1，2，4，4，4，2，5，5，5，2，1，3，6，6，6，3，7，7，7，3，1
  //递归版：
  //把打印时机放到第一次来到这个节点的时候，就是前序便利
  //1，2，4，5，3，6，7
  //把打印时机放到第二次来到这个节点的时候，就是中序便利
  //4，2，5，1，6，3，7
  //把打印时机放到第三次来到这个节点的时候，就是后序便利
  //4，5，2，6，7，3，1

  /**
   * 前序遍历，递归
   * @param head
   */
  public void preOrderRecur(TreeNode head) {
    if (head == null) {
      return;
    }
    System.out.println(head.value + ",");
    preOrderRecur(head.left);
    preOrderRecur(head.right);
  }

  /**
   * 前序遍历，非递归
   * @param head
   */
  public void preOrderUnRecur(TreeNode head) {
    List<TreeNode> result = new ArrayList<>();
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = head;
    stack.push(curr);

    while (!stack.isEmpty()) {
      curr = stack.pop();
      System.out.println(curr.value + ",");
      result.add(curr);
      if (curr.right != null) {
        stack.push(curr.right);
      }
      if (curr.left != null) {
        stack.push(curr.left);
      }
    }
  }

  /**
   * 中序遍历，递归
   * @param head
   */
  public void inOrderRecur(TreeNode head) {
    if (head == null) {
      return;
    }
    preOrderRecur(head.left);
    System.out.println(head.value + ",");
    preOrderRecur(head.right);
  }

  /**
   * 中序遍历，非递归
   * @param head
   */
  public void inOrderUnRecur(TreeNode head) {
    List<TreeNode> result = new ArrayList<>();
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = head;

    while (!stack.isEmpty() || curr != null) {
      if(curr != null) {
        stack.push(curr);
        curr = curr.left;
      } else {
        curr = stack.pop();
        System.out.println(curr.value + ",");
        result.add(curr);
        curr = curr.right;
      }
    }
  }

    /**
     * 后序遍历，递归
     * @param head
     */
  public void postOrderRecur(TreeNode head) {
    if (head == null) {
      return;
    }
    preOrderRecur(head.left);
    preOrderRecur(head.right);
    System.out.println(head.value + ",");
  }

  public void postOrderUnRecur(TreeNode head) {
    List<TreeNode> result = new ArrayList<>();
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack1 = new Stack<>();
    Stack<TreeNode> stack2 = new Stack<>();
    TreeNode curr = head;
    stack1.push(curr);
    while (!stack1.isEmpty()) {
      curr = stack1.pop();
      stack2.push(curr);
      if (curr.left != null) {
        stack1.push(curr.left);
      }
      if (curr.right != null) {
        stack1.push(curr.right);
      }
    }

    while (!stack2.isEmpty()) {
      curr = stack2.pop();
      result.add(curr);
      System.out.println(curr.value + ",");
    }

  }


}
