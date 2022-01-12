package com.example.leetcode.service;

import com.example.leetcode.utils.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
  public int pathSum(TreeNode root, int targetSum) {
    Map<Long, Integer> map = new HashMap<>();
    map.put(0L, 1);
    return dfs(root, map, 0, targetSum);
  }

  public int dfs(TreeNode root, Map<Long, Integer> map, long curr, int targetSum) {
    if (root == null) {
      return 0;
    }

    int ret = 0;
    curr += root.value;

    ret = map.getOrDefault(curr - targetSum, 0);
    map.put(curr, map.getOrDefault(curr, 0) + 1);
    ret += dfs(root.left, map, curr, targetSum);
    ret += dfs(root.right, map, curr, targetSum);
    //这里减一的原因是：已经计算过当前分支节点了，计算其他分支的时候不能被当前分支影响
    map.put(curr, map.getOrDefault(curr, 0) - 1);

    return ret;
  }

  //求根节点到叶节点数字之和
  //给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
  //每条从根节点到叶节点的路径都代表一个数字：
  //例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
  //计算从根节点到叶节点生成的 所有数字之和 。
  //叶节点 是指没有子节点的节点。
  //dfs，时间复杂度O(n)，空间复杂度O(n)
  public int sumNumbers(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return dfs2(root, 0);
  }

  public int dfs2(TreeNode root, int prevSum) {
    if (root == null) {
      return 0;
    }

    int sum = prevSum * 10 + root.value;
    if (root.left == null && root.right == null) {
      return sum;
    } else {
      return dfs2(root.left, sum) + dfs2(root.right, sum);
    }
  }


}
