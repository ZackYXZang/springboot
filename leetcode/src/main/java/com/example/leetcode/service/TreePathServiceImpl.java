package com.example.leetcode.service;

import com.example.leetcode.utils.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service("treePathService")
public class TreePathServiceImpl {

  //路径总和
  //给你二叉树的根节点root和一个表示目标和的整数targetSum ，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和targetSum 。
  //叶子节点 是指没有子节点的节点。
  public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) {
      return false;
    }

    if (root.left == null && root.right == null) {
      return targetSum == root.value;
    }

    return hasPathSum(root.left, targetSum - root.value) || hasPathSum(root.right, targetSum = root.value);
  }

  //路径总和 II
  //给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
  //叶子节点 是指没有子节点的节点。
  public List<List<Integer>> pathSumII(TreeNode root, int targetSum) {
    if (root == null) {
      return null;
    }
    List<List<Integer>> result = new ArrayList<>();
    pathSumIIBackTrack(root, targetSum, result, new ArrayList<>());
    return result;
  }

  public void pathSumIIBackTrack(TreeNode root, int targetSum, List<List<Integer>> result, List<Integer> list) {
    if (root == null) {
      return;
    }

    targetSum -= root.value;
    list.add(root.value);
    if (root.left == null && root.right == null && targetSum == 0) {
      result.add(new ArrayList<>(list));
    }
    pathSumIIBackTrack(root.left, targetSum, result, list);
    pathSumIIBackTrack(root.right, targetSum, result, list);
    list.remove(list.size() - 1);
  }

  //路径总和 III
  //给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
  //路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
//  public int pathSum(TreeNode root, int targetSum) {
//
//  }


}
