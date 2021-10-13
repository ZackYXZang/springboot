package com.example.leetcode;

import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImpl;
import com.example.leetcode.service.ArraySortImplTwo;
import com.example.leetcode.service.KMPAndManacherAndBFPRTAlgorithm;
import com.example.leetcode.service.LeetCodeHot100;
import com.example.leetcode.service.TreeServiceImpl;
import com.example.leetcode.service.ZuoClassDPPractice;
import com.example.leetcode.service.ZuoClassGraphPractice;
import com.example.leetcode.service.ZuoClassSlidingWindowPractice;
import com.example.leetcode.service.ZuoClassThreePractice;
import com.example.leetcode.service.ZuoClassThreePractice.DoubleNode;
import com.example.leetcode.utils.Graph;
import com.example.leetcode.utils.GraphGenerator;
import com.example.leetcode.utils.GraphNode;
import com.example.leetcode.utils.Node;
import com.example.leetcode.utils.ParentNode;
import com.example.leetcode.utils.RandomNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeetcodeApplicationTests {

  @Test
  void contextLoads() throws Exception {

  }

  /**
   * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
   * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
   */
  public boolean Find(int target, int [][] array) {
    if (array == null || array.length == 0 || array[0] == null || array[0].length == 0) {
      return false;
    }

    //选取左下或者右上角
    int rowStart = 0;
    int colStart = array[0].length - 1;

    while (rowStart < array.length && colStart >= 0) {
      if (array[rowStart][colStart] == target) {
        return true;
      }
      if (array[rowStart][colStart] < target) {
        rowStart++;
        continue;
      }
      if (array[rowStart][colStart] == target) {
        colStart--;
        continue;
      }
    }
    return false;
  }

  /**
   * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
   */

  public String replaceSpace(StringBuffer str) {
    if (str == null || str.length() == 0) {
      return "";
    }

    String string = str.toString();
    String result = string.replaceAll("", "20%");
    return result;

  }
}
