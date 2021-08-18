package com.example.leetcode.utils;

/**
 * @author yuxiangzang
 * @create 2021-03-05-下午6:03
 * @desc 二叉树节点
 **/
public class TreeNode {

  public int value;
  public TreeNode left;
  public TreeNode right;

  public TreeNode() {}
  public TreeNode(int data) {
    this.value = data;
  }
  public TreeNode(int data, TreeNode left , TreeNode right) {
    this.value = data;
    this.left = left;
    this.right = right;
  }
}
