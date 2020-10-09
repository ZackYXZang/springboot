package com.example.leetcode;

import com.example.leetcode.service.ArrayServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeetcodeApplicationTests {

  @Autowired
  private ArrayServiceImpl arrayService;

  @Test
  void contextLoads() throws Exception {
//    int target = 7;
//    int array[][] = new int[][] {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}, {7,9,12,16}};
//    boolean flag = arrayService.find(target, array);
//    int n = 3;
//    int fibonacci = arrayService.fibonacci(n);
//    //1,2,8,9,12,13,15,16,12,9,7,6,4,2,4,9,10,11,8,7
//    ArrayList<Integer> arrays = arrayService.printMatrix(array);
//    System.out.println(arrays);

    //4,5,1,6,2,7,3,8
//    int[] array = new int[]{4,5,1,6,2,7,3,8};
//    int k = 4;
//    ArrayList<Integer> arrays = arrayService.GetLeastNumbers_Solution(array, k);
//    System.out.println(arrays);

    //1,2,3,4,5,6,7,0
    //7
//    mergeSort(array);
    int[] array1 = new int[]{1, 2, 4, 7, 3, 5, 6, 8};
    int[] array2 = new int[]{4, 7, 2, 1, 5, 3, 8, 6};
//    int k = 6;
//
//    int i = arrayService.GetNumberOfK(array, k);
//    System.out.println(i);

  }

  public List<String> test1(List<String> list, List<String> list2) {
    List<String> res = new ArrayList<>();
    for (String s : list) {
      res.add(s);
      list2.add(s);
    }
    return res;
  }

  public static int getAge(String birthDayStr) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    int age = 0;
    try {
      Date birthDay = format.parse(birthDayStr);
      Calendar cal = Calendar.getInstance();
      int yearNow = cal.get(Calendar.YEAR);  //当前年份
      int monthNow = cal.get(Calendar.MONTH);  //当前月份
      int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
      cal.setTime(birthDay);
      int yearBirth = cal.get(Calendar.YEAR);
      int monthBirth = cal.get(Calendar.MONTH);
      int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
      age = yearNow - yearBirth;   //计算整岁数
      if (monthNow <= monthBirth) {
        if (monthNow == monthBirth) {
          if (dayOfMonthNow < dayOfMonthBirth) {
            age--;//当前日期在生日之前，年龄减一
          }
        } else {
          age--;//当前月份在生日之前，年龄减一
        }
      }
      return age;
    } catch (ParseException e) {
      e.printStackTrace();
      return age;
    }
  }


  public static void mergeSort(int[] array) {
    int count = sort(array, 0, array.length - 1, 0);
    System.out.println("result = " + count);
  }

  public static int sort(int[] array, int start, int end, int count) {
    //如果指针相遇，返回
    if (start >= end) {
      return count;
    }

    //递归
    //找到中间对索引
    int mid = (start + end) / 2;
    //对左边对数组进行递归
    int left = sort(array, start, mid, count);
    //对右边对数组进行递归
    int right = sort(array, mid + 1, end, count);

    //开始计算逆数对
    int i = mid;
    int j = end;

    //临时数组，用来储存有序的结果
    int[] tempArray = new int[array.length];
    //临时数组的开始是左数组的第一个元素的位置
    int tempIndex = end;

    //右数组的第一个元素的索引
    int rightStart = mid + 1;
    //将排序后的临时数组赋值会原数组
    int copyIndex = start;

    while (i >= start && j > mid) {
      if (array[i] > array[j]) {
        count += j - mid;
        tempArray[tempIndex--] = array[i--];
      } else {
        tempArray[tempIndex--] = array[j--];
      }
    }

    //将剩余不分一次放入临时数组（两个while只会执行其中一个）
    while (i >= start) {
      tempArray[tempIndex--] = array[i--];
    }
    while (j > mid) {
      tempArray[tempIndex--] = array[j--];
    }
    //将临时数组中的内容拷贝回原数组中
    while (copyIndex <= end) {
      array[copyIndex] = tempArray[copyIndex++];
    }
    //合并
//    merge (array, start, mid, end);

    return left + right + count;
  }

  public static void merge(int[] array, int left, int mid, int right) {

    //临时数组，用来储存有序的结果
    int[] tempArray = new int[array.length];
    //临时数组的开始是左数组的第一个元素的位置
    int tempIndex = left;

    //右数组的第一个元素的索引
    int rightStart = mid + 1;
    //将排序后的临时数组赋值会原数组
    int copyIndex = left;

    //在两个数组中选择最小的放入临时数组
    while (left <= mid && rightStart <= right) {
      if (array[left] <= array[rightStart]) {
        tempArray[tempIndex++] = array[left++];
      } else {
        tempArray[tempIndex++] = array[rightStart++];
      }
    }

    //将剩余不分一次放入临时数组（两个while只会执行其中一个）
    while (left <= mid) {
      tempArray[tempIndex++] = array[left++];
    }
    while (rightStart <= right) {
      tempArray[tempIndex++] = array[rightStart++];
    }
    //将临时数组中的内容拷贝回原数组中
    while (copyIndex <= right) {
      array[copyIndex] = tempArray[copyIndex++];
    }

  }


}
