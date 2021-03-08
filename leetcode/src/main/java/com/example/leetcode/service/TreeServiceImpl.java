package com.example.leetcode.service;

import com.example.leetcode.utils.ParentNode;
import com.example.leetcode.utils.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
   * 做二叉树相关的题的套路：
   * 1. 列出所有可能性
   * 2. 整理出返回值类型
   * 3. 递归过程按照同样的结构，得到子树的信息
   * 4. 整合子树信息，然后加工成我的信息
   * 5. 往上返回，注意，要要求结构一致
   */
  /**
   *          1
   *     2        3
   *   4   5    6   7
   */

  //前序遍历，遍历顺序，根左右
  //1，2，4，4，4，2，5，5，5，2，1，3，6，6，6，3，7，7，7，3，1
  //递归版：
  //把打印时机放到第一次来到这个节点的时候，就是前序遍历
  //1，2，4，5，3，6，7
  //把打印时机放到第二次来到这个节点的时候，就是中序遍历
  //4，2，5，1，6，3，7
  //把打印时机放到第三次来到这个节点的时候，就是后序遍历
  //4，5，2，6，7，3，1

  /**
   * 前序遍历，递归
   *
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
   *
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
   *
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
   *
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
      if (curr != null) {
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
   *
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

  /**
   * 后序遍历，非递归
   *
   * @param head
   */
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

  /**
   * 后序遍历，非递归，待研究
   *
   * @param h
   */
  public static void posOrderUnRecur2(TreeNode h) {
    System.out.print("pos-order: ");
    if (h != null) {
      Stack<TreeNode> stack = new Stack<TreeNode>();
      stack.push(h);
      TreeNode c = null;
      while (!stack.isEmpty()) {
        c = stack.peek();
        if (c.left != null && h != c.left && h != c.right) {
          stack.push(c.left);
        } else if (c.right != null && h != c.right) {
          stack.push(c.right);
        } else {
          System.out.print(stack.pop().value + " ");
          h = c;
        }
      }
    }
    System.out.println();
  }

  /**
   * 找到一个节点的后继节点，中序遍历
   *
   * @param node
   * @return
   */
  public ParentNode getSuccessorNode(ParentNode node) {
    if (node == null) {
      return null;
    }

    //如果一个节点有右子树，那么后继节点就是右子树的最左节点
    if (node.right != null) {
      return getLeftMostNode(node.right);
    }

    //如果一个节点没有右子树，找到这个节点的父节点，
    //判断这个节点是不是它父节点的左子树，如果不是，就继续网上找，知道找到为止
    //后继节点就是这个父节点
    ParentNode parent = node.parent;
    ParentNode curr = node;
    while (parent != null && parent.left != curr) {
      curr = parent;
      parent = curr.parent;
    }

    return parent;
  }


  public ParentNode getLeftMostNode(ParentNode node) {
    ParentNode curr = node;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }


  /**
   * 判断是否是平衡二叉树
   *
   * @param head
   * @return
   */
  public boolean isBalanceTree(TreeNode head) {
    if (head == null) {
      return true;
    }

    int balance = isBalanceTreeSub(head);
    return balance == -1 ? false : true;
  }


  public int isBalanceTreeSub(TreeNode head) {
    if (head == null) {
      return 0;
    }

    int left = isBalanceTreeSub(head.left);
    if (left == -1) {
      return -1;
    }
    int right = isBalanceTreeSub(head.right);
    if (right == -1) {
      return -1;
    }

    if (Math.abs(left - right) > 1) {
      return -1;
    }
    return Math.max(left, right) + 1;
  }

  /**
   * 判断是否是搜索二叉树（节点的左子树< 节点 < 节点的右子树）
   *
   * @param head
   * @return
   */
  public boolean isSearchTree(TreeNode head) {
    if (head == null) {
      return true;
    }

    //中序遍历，结果是递增的
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = head;
    int pre = Integer.MIN_VALUE;

    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.push(curr);
        curr = curr.left;
      } else {
        curr = stack.pop();
        if (curr.value < pre) {
          return false;
        }
        pre = curr.value;
        curr = curr.right;

      }
    }
    return true;
  }

  /**
   * 判断是否是完全二叉树
   *
   * @param head
   * @return
   */
  public boolean isFullBinaryTree(TreeNode head) {
    if (head == null) {
      return true;
    }

    //按层遍历
    Queue<TreeNode> queue = new LinkedList<>();
    boolean leaf = false;
    TreeNode left = null;
    TreeNode right = null;
    TreeNode curr = head;
    queue.offer(curr);

    while (!queue.isEmpty()) {
      curr = queue.poll();
      left = curr.left;
      right = curr.right;

      //如果一个节点，没有左子树，但是有右子树，那么就不是完全二叉树
      if (left == null && right != null) {
        return false;
      }

      //leaf表示，前面的节点的右子树为空，或者左，右子树都为空
      //那么此时这个节点的左，右子树都必须为空
      if ((left != null || right != null) && leaf) {
        return false;
      }

      if (left != null) {
        queue.offer(left);
      }

      if (right != null) {
        queue.offer(right);
      }

      if (left == null || right == null) {
        leaf = true;
      }
    }
    return true;
  }

  public int getNodeNum(TreeNode head) {
    if (head == null) {
      return 0;
    }

    return bs(head, 1, getLeftHeight(head, 1));

  }

  public int bs(TreeNode head, int level, int height) {
    if (level == height) {
      //说明是叶节点
      return 1;
    }
    if (getLeftHeight(head.right, level + 1) == height) {
      //如果节点的右子树的最左子数能到达最后（就是树的高度），就说明节点的左子树是完全二叉树
      //此时用左子树的和：2^(height - level) - 1，加上当前节点，加上递归求出的右子树的节点数
      /**
       *          1
       *     2        3
       *   4   5    6
       */
      return (1 << (height - level)) + bs(head.right, level + 1, height);
    }else {
      //如果节点的右子树的最左子数不能到达最后（就是树的高度），就说明节点的右子树是完全二叉树，但是高度要-1
      //此时用右子树的和：2^(height - level - 1) - 1，加上当前节点，加上递归求出的左子树的节点数
      /**
       *          1
       *     2        3
       *   4   5    6   7
       * 8      9
       */
      return (1 << (height - level - 1)) + bs(head.left, level + 1, height);
    }

  }

  public int getLeftHeight(TreeNode head, int level) {
    while (head != null) {
      level++;
      head = head.left;
    }
    return level - 1;
  }

}
