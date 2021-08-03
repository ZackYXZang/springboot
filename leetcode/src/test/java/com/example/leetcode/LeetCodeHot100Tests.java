package com.example.leetcode;


import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImpl;
import com.example.leetcode.service.LeetCodeHot100;
import com.example.leetcode.service.ZuoClassSlidingWindowPractice;
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
    int[] nums = new int[]{1, 2, 3};
    char[][] board = new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
    System.out.println(leetCodeHot.exist(board, "ABCCEE"));
  }


}
