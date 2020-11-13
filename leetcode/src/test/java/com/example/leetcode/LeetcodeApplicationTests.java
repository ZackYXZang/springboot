package com.example.leetcode;

import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeetcodeApplicationTests {

  @Autowired
  private ArrayServiceImpl arrayService;

  @Autowired
  private ArraySortImpl arraySort;

  @Test
  void contextLoads() throws Exception {

    int[] array = new int[]{1, 5, 4, 3, 2, 6, 8, 7, 9};
    //冒泡
    for (int i = array.length - 1; i >= 0; i--) {
      for (int j = 0; j < i; j++) {
        if(array[j] > array[j + 1]) {
          swap(array, j, j + 1);
        }
      }
    }

    System.out.println(printArray(array));

  }

  public String printArray(int[] array){
    String result = "";
    for (int i = 0; i < array.length; i++) {
      result += array[i];
    }
    return result;
  }

  public void swap(int[] array, int first, int second) {
    int temp = array[first];
    array[first] = array[second];
    array[second] = temp;
  }


}
