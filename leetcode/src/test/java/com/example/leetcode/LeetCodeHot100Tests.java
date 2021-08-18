package com.example.leetcode;


import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImpl;
import com.example.leetcode.service.LeetCodeHot100;
import com.example.leetcode.service.ZuoClassSlidingWindowPractice;
import com.example.leetcode.utils.TreeNode;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Handler;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

/**
 * @author yuxiangzang
 * @create 2020-10-23-2:49 下午
 * @desc
 **/
@SpringBootTest
public class LeetCodeHot100Tests {

  @Autowired
  private LeetCodeHot100 leetCodeHot;

  @Autowired
  private ZuoClassSlidingWindowPractice practice;

  @Test
  void contextLoads() throws Exception {
    test();
  }


  public void test() {
    int[] nums = new int[]{7, 1, 5, 3, 6, 4};
    //[['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','1','1','1','1']]
    char[][] board = new char[][] {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'}, {'1','1','1','1','1'}};
//    leetCodeHot.maxProfit(nums);
    getResult();
//
//    //             1
//    //      2               3
//    //  4      5       6       7
//    //8  9  10  11  12  13  14  15
//    //
//    TreeNode head = new TreeNode(1);
//    head.left = new TreeNode(2);
//    head.right = new TreeNode(2);
//    head.left.left = new TreeNode(3);
//    head.left.right = new TreeNode(4);
//    head.right.left = new TreeNode(4);
//    head.right.right = new TreeNode(3);
//
//    System.out.println(leetCodeHot.isSymmetric(head));
//    inOrderUnRecur(head);
  }

  public void getResult () {
    int i = 1;
    getResult1(i);
    System.out.println(i);
  }

  public void getResult1(Integer i) {
    i = i + 1;
  }






}
