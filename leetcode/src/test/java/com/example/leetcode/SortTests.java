package com.example.leetcode;

import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImpl;
import com.example.leetcode.service.DuoxianchengService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yuxiangzang
 * @create 2020-10-23-2:49 下午
 * @desc
 **/
@SpringBootTest
public class SortTests {

  @Autowired
  private ArrayServiceImpl arrayService;

  @Autowired
  private ArraySortImpl arraySort;

  @Autowired
  private DuoxianchengService duoxianchengService;

  @Test
  void contextLoads() throws Exception {


    duoxianchengService.test();
  }

  public void test() {
    int testTime = 500000;
    int maxSize = 10;
    int maxValue = 100;
    boolean succeed = true;
    for (int i = 0; i < testTime; i++) {
      int[] arr1 = generateRandomArray(maxSize, maxValue);
      int[] arr2 = copyArray(arr1);
      arrayService.reOrderArray1(arr1);
      arrayService.reOrderArray2(arr2);
      String result1 = "";
      String result2 = "";
      for (int m = 0; m < arr1.length; m++) {
        result1 += arr1[m];
      }
      for (int n = 0; n < arr1.length; n++) {
        result2 += arr2[n];
      }
      if (!result1.equals(result2)) {
        System.out.println(result1);
        System.out.println(result2);
      }
//      int result1 = arrayService.InversePairs(arr1);
//      int result2 = arrayService.InversePairs2(arr2);
//
//      if  (result1 != result2) {
//        succeed = false;
//        printArray(arr1);
//        System.out.println(result1);
//        System.out.println(result2);
//        break;
//      }
    }
//    System.out.println(succeed ? "Nice!" : "Fucking fucked!");
  }

  // for test
  public static int[] generateRandomArray(int maxSize, int maxValue) {
    int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (int) ((maxValue + 1) * Math.random());
    }
    return arr;
  }

  // for test
  public static int[] copyArray(int[] arr) {
    if (arr == null) {
      return null;
    }
    int[] res = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      res[i] = arr[i];
    }
    return res;
  }

  // for test
  public static boolean isEqual(int[] arr1, int[] arr2) {
    if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
      return false;
    }
    if (arr1 == null && arr2 == null) {
      return true;
    }
    if (arr1.length != arr2.length) {
      return false;
    }
    for (int i = 0; i < arr1.length; i++) {
      if (arr1[i] != arr2[i]) {
        return false;
      }
    }
    return true;
  }
  public static void printArray(int[] arr) {
    if (arr == null) {
      return;
    }
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }
}
