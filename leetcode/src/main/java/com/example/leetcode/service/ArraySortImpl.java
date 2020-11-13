package com.example.leetcode.service;

import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2020-10-21-3:19 下午
 * @desc 排序的各种方法
 **/
@Service
public class ArraySortImpl {

  /**
   * 稳定性：
   * 具有稳定性的算法：
   *      冒泡排序：可以实现成稳定的，当大的值向后移动时，碰到相等的值一起往后移动，就可以保证稳定性
   *      插入排序：可以实现成稳定的，当小的值向前移动时，碰到相等的值就不动了，就可以保证稳定性
   *      归并排序：当左边和右边相等时，先拷贝左边的
   * 不具有稳定性的算法：
   *      选择排序（找小的数，从头开始排序）
   *      快速排序（选一个值，划分出大于，等于、小于它的区间，递归）
   *      堆排序
   *
   */

  /**
   * 冒泡排序 O(N^2)
   * [3, 2, 4, 0, 7]
   * 第一次：位置1和位置2比较，大的放后面，类推：位置2和位置3比较，直到位置N-1和位置N，此时位置N的数最大
   * 第二次：位置1和位置2比较，大的放后面，类推：位置2和位置3比较，直到位置N-2和位置N - 1，此时位置N-1的数第二大
   * 以此类推，排序完成
   * @param arr
   * @return
   */
  public void BubbleSort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }

    for (int end = arr.length - 1; end > 0; end--) {
      for (int i = 0; i < end; i++) {
        if (arr[i] > arr[i + 1]) {
          //调换位置
          swap(arr, i, i + 1);
        }
      }
    }
  }

  /**
   * 选择排序 O(N^2)
   * [3, 2, 4, 0, 7]
   * 第一次在位置0到位置N - 1，找到最小到数字，放到位置0上
   * 第一次在位置1到位置N - 1，找到最小到数字，放到位置1上
   * 类推，得到排序到数f组
   * @param arr
   */
  public void SelectSort(int[] arr) {
    if (arr == null || arr.length < 2 ) {
      return;
    }

    for (int i = 0; i < arr.length - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < arr.length; j++) {
        minIndex = arr[minIndex] > arr[j] ? j : minIndex;
      }
      swap(arr, i, minIndex);
    }
  }

  /**
   * 插入排序 最好O(N), 最坏O(N^2)
   * [3, 2, 4, 0, 7]
   * 第一步，位置1开始，比较位置0和位置1对大小，保持位置0，1有序；[2, 3, 4, 0, 7]
   * 第二步，看位置2，依次和位置0，1上对数值比较大小，保持位置0，1，2位置对有序；[2, 3, 4, 0, 7]
   * 第三步，看位置3，依次和位置0，1，2上对数值比较大小，保持位置0，1，2，3位置对有序；[0, 2, 3, 4, 7]
   * 类推，得到有序数组
   * @param arr
   */
  public void InsertSort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }
    for (int i = 1; i < arr.length; i++) {
      for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
        swap(arr, j, j + 1);
      }
    }
  }

  public void swap (int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;

  }

  /**
   * 归并排序 时间复杂度O(N*logN) 空间复杂度O(N)
   * 使用递归；
   * 首先把数组划分成最小的部分，一个一个的，然后两两比较排序，然后合并
   * @param arr
   */
  public void MergeSort(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }
    MergeSort(arr, 0, arr.length - 1);
  }

  public void MergeSort(int[] arr, int l, int r) {
    if (l == r) {
      return;
    }
    int mid = l + ((r - l) >> 1); //防止溢出，等价于(l+r)/2因为l+r可能溢出，再除以2，计算的mid就不对
    MergeSort(arr, l, mid);
    MergeSort(arr, mid + 1, r);
    merge(arr, l, mid, r);
  }

  public void merge(int[] arr, int l, int mid, int r) {
    int[] help = new int[r - l + 1];
    int i = 0;
    int leftIndex = l;
    int rightIndex = mid + 1;
    while (leftIndex <= mid && rightIndex <= r) {
      help[i++] = arr[leftIndex] > arr[rightIndex] ? arr[rightIndex++] : arr[leftIndex++];
    }

    while (leftIndex <= mid) {
      help[i++] = arr[leftIndex++];
    }
    while (rightIndex <= r) {
      help[i++] = arr[rightIndex++];
    }

    for (i = 0; i < help.length; i++) {
      arr[l + i] = help[i];
    }
  }

  /**
   * 把数组划分成比目标值大于，小于，等于，三个区域
   * 时间复杂度是O(N)，空间复杂度是O(1)
   * @param array
   * @param l
   * @param r
   * @param target
   * @return
   */
  public int[] partition(int[] array, int l, int r, int target) {
    int less = l - 1;
    int more = r + 1;
    while (l < more) {
      if (array[l] < target) {
        swap(array, ++less, l++);
      } else if (array[l] > target) {
        swap(array, --more, l);
      } else {
        l++;
      }
    }
    //此时返回的值是等于区域的两个边界
    return new int[] {less + 1, more - 1};
  }

  /**
   * 快排
   * 时间复杂度O(N*logN)，因为选择的划分点是随机的，所以时间复杂度是一个长期期望的复杂度
   * 随机快排的空间复杂度O(logN)，就是划分点
   * T(N) = 2 * T(N/2) + O(N) => O(N*logN)
   * 会被经常使用3，优势是常数项小
   * @param arr
   */
  public void QuickSort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }
    quickSort(arr, 0, arr.length - 1);
  }

  public void quickSort(int[] arr, int l, int r) {
    if (l < r) {
      //随机选一个用来比较的数，这里放到了r位置，也就是用r位置的数来做目标
      swap(arr, l + (int)(Math.random() * (r - l + 1)), r);
      //根据选出来的数，分成大于，等于，小于三个区间，返回等于区间的开始、结束角标
      //等于目标数的区间的左右角标
      int[] partition = partition(arr, l, r);
      //小于目标数的区间
      quickSort(arr, l, partition[0] - 1);
      //大于目标数的区间
      quickSort(arr, partition[1] + 1, r);

    }
  }

  public int[] partition(int[] arr, int l, int r) {
    //此时arr[r]就是用来做比较的数（目标数）
    int less = l - 1;
    int more = r;
    while (l < more) {
      if (arr[l] > arr[r]) {
        //如果l位置的数，比目标数要大，
        //那么交换l位置和more位置的数，并且让more的位置向前移动一位，因为more之后（包括more位置）表示比目标数大的数
        swap(arr, l, --more);
      } else if (arr[l] < arr[r]) {
        //如果l位置的数，比目标数要小，
        //那么先交换l位置的数，和less下一位的数，并让less向右移动一位，
        //因为less位置表示小于目标数（包括less），所以交换的是less下一位的数
        //同时l向右移动一 位
        swap(arr, l++, ++less);
      } else {
        //等于区域，l向右移动
        l++;
      }
    }

    //当遍历结束之后，要交换more位置和r位置的数
    //因为r位置的数是目标数，more位置的数一定是比目标数要大的数，
    //交换后确保more之后（包括more位置）表示比目标数大的数
    swap(arr, more, r);
    //less是小于目标数的位置，less+1是等于目标数的数，或者是大于目标数的数（没有等于区间）
    //经过上面的交换，more位置一定是目标数
    return new int[]{less + 1, more};
  }


  /**
   * 堆排序
   * 时间复杂度O(N*logN)，空间复杂度O(1)
   * @param arr
   */
  public void HeapSort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }

    //从位置0开始，到最后到位置，构建出一个大根堆
    //时间复杂度为O(N*logN)，因为heapInsert(arr, i);时间复杂度是O(logN)
    for (int i = 0; i < arr.length; i++) {
      heapInsert(arr, i);
    }

    //首先交换首位，和最后位置的数，触发可以进行heapify的构建
    int size = arr.length;
    swap(arr, 0, --size);

    //时间复杂度为O(N*logN)，heapify(arr, 0, size);时间复杂度是O(logN)
    while(size > 0) {
      //重新构建最大堆
      heapify(arr, 0, size);
      swap(arr, 0, --size);
    }
  }

  public void heapify(int[] arr, int index, int size) {
    int left = index * 2 + 1;
    while (left < size) {
      //确定index节点的左右子节点的大小
      int largestIndex = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
      //确定子节点和父节点的大小
      largestIndex = arr[largestIndex] > arr[index] ? largestIndex : index;
      if (largestIndex == index) {
        //如果父节点和子节点最大的是父节点，就不继续判断了
        break;
      }
      swap(arr, largestIndex, index);
      index = largestIndex;
      left = index * 2 + 1;
    }
  }

  public void heapInsert(int[] arr, int index) {
    //如果新加进来的节点，要比他的父节点大，那么就交换两者的位置
    while(arr[index] > arr[(index - 1) / 2]) {
      swap(arr, index, (index - 1) / 2);
      index = (index - 1) / 2; //因为(0 - 1) / 2 = 0，所以不用怕数组越界的情况
    }
  }


}
