package com.example.leetcode;

import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImpl;
import com.example.leetcode.service.ArraySortImplTwo;
import com.example.leetcode.service.ZuoClassThreePractice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeetcodeApplicationTests {

  @Autowired
  private ArrayServiceImpl arrayService;

  @Autowired
  private ArraySortImplTwo arraySortTwo;

  @Autowired
  private ZuoClassThreePractice leetCode;

  @Test
  void contextLoads() throws Exception {

    int[] array = new int[]{1, 5, 4, 3, 2, 6, 8, 7, 9};
    //冒泡
//    arraySortTwo.BubbleSort(array);

    //选择排序
//    arraySortTwo.SelectSort(array);

    //插入排序
//    arraySortTwo.InsertSort(array);

    //归并排序
//    arraySortTwo.MergeSort(array);

    //快速排序
//    arraySortTwo.QuickSort(array);

    //堆排序
//    arraySortTwo.HeapSort(array);




//    System.out.println(printArray(array));

    int[] array2 = new int[]{19, 30, 49, 59, 60};
    int maxGap = leetCode.MaxGap(array2);
    int maxGap1 = arrayService.MaxGap(array2);
    System.out.println(maxGap);
    System.out.println(maxGap1);
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
