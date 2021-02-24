package com.example.leetcode.service;

import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2021-02-22-下午6:55
 * @desc
 **/
@Service("arraySortTwo")
public class ArraySortImplTwo {

  //冒泡排序
  public void BubbleSort(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    for (int end = arr.length - 1; end > 0; end--) {
      for (int start = 0; start < end; start++) {
        if (arr[start] > arr[start + 1]) {
          swap(arr, start, start + 1);
        }
      }
    }
  }

  //选择排序
  public void SelectSort(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    for (int index = 0; index < arr.length - 1; index ++) {
      int minIndex = index;
      for (int start = index + 1; start < arr.length; start++) {
        minIndex = arr[minIndex] > arr[start] ?  start : minIndex;
      }
      swap(arr, minIndex, index);
    }
  }


  //插入排序
  public void InsertSort(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    for (int index = 1; index < arr.length; index++) {
      for (int start = index - 1; start > 0; start--) {
        if (arr[start] > arr[start + 1]) {
          swap(arr, start, start + 1);
        }
      }
    }
  }

  //归并排序
  public void MergeSort(int[] arr) {
    if(arr == null || arr.length == 0) {
      return;
    }

    MergeSortSub(arr, 0, arr.length - 1);
  }

  public void MergeSortSub(int[] arr, int start, int end) {
    if (start >= end) {
      return;
    }

    int mid = start + (end - start) / 2;
    MergeSortSub(arr, start, mid);
    MergeSortSub(arr, mid + 1, end);
    merge(arr, start, mid, end);
  }

  public void merge(int[] arr, int start, int mid, int end) {
    int[] help = new int[end - start + 1];
    int helpStart = 0;
    int l = start;
    int r = mid + 1;
    while (l <= mid && r <= end) {
      help[helpStart++] = arr[l] < arr[r] ? arr[l++] : arr[r++];
    }
    while (l <= mid) {
      help[helpStart++] = arr[l++];
    }

    while (r <= end) {
      help[helpStart++] = arr[r++];
    }

    for (int i = 0; i < help.length; i++) {
      arr[start + i] = help[i];
    }
  }

  //快速排序
  public void QuickSort(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    QuickSortSub(arr, 0, arr.length - 1);
  }

  public void QuickSortSub(int[] arr, int start, int end) {
    if (start >= end) {
      return;
    }
    swap(arr, start + (int)(Math.random() * (end - start + 1)), end);
    int[] partition = partition(arr, start, end);
    QuickSortSub(arr, start, partition[0] - 1);
    QuickSortSub(arr, partition[1] + 1, end);
  }

  public int[] partition(int[] arr, int start, int end) {
    int less = start - 1;
    int more = end;

    while (start < more) {
      if (arr[start] < arr[end]) {
        less++;
        swap(arr, start, less);
        start++;
      } else if (arr[start] > arr[end]) {
        more--;
        swap(arr, start, more);
      } else {
        start++;
      }
    }

    swap(arr, more, end);
    //返回等于区间的边界
    return new int[]{less + 1, more};
  }

  public void HeapSort(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    for (int i = 0; i < arr.length; i++) {
      BuildHeap(arr, i);
    }

    int size = arr.length;
    swap(arr, 0, --size);

    while (size > 0) {
      HeapSortSub(arr, 0, size);
      swap(arr, 0, --size);
    }
  }

  public void BuildHeap(int[] arr, int index) {
    while (arr[index] > arr[(index - 1) / 2]) {
      swap(arr, index, (index - 1) / 2);
      index = (index - 1) / 2;
    }
  }

  public void HeapSortSub(int[] arr, int index, int size) {
    int left = index * 2 + 1;
    while (left < size) {
      int largestIndex = left + 1 < size && arr[left] < arr[left + 1] ? left + 1 : left;
      largestIndex = arr[largestIndex] > arr[index] ? largestIndex : index;
      if (largestIndex == index) {
        break;
      }
      swap(arr, index, largestIndex);
      index = largestIndex;
      left = index * 2 + 1;
    }
  }

  public void swap(int[] arr, int start, int next) {
    int temp = arr[start];
    arr[start] = arr[next];
    arr[next] = temp;
  }

}
