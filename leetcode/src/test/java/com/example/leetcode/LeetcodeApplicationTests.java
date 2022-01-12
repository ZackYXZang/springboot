package com.example.leetcode;

import com.sun.org.apache.regexp.internal.RE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeetcodeApplicationTests {

  public class TreeLinkNode {

    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
      this.val = val;
    }
  }


  public class ListNode {

    int val;
    ListNode next = null;

    ListNode(int val) {
      this.val = val;
    }
  }

  public class RandomListNode {

    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
      this.label = label;
    }
  }

  public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  @Test
  void contextLoads() throws Exception {
    //树的遍历
    //前序遍历   根左右
    //中序遍历   左根右
    //后序遍历   左右根
    //给定前序遍历和中序遍历，可以确定唯一的二叉树
    //{7,1,12,0,4,11,14,#,#,3,5}

    //[10,2,6]
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
//    System.out.println(KthNode(root, 3));

    //[-1,3,2,1,-2,-2,-3,0,3,2,1,-1]
    //[0,3,2,1]
    //[3,2,1,-2,-2,-3,0,3,2,1]
    int[] array = new int[]{10,9,2,5,3,7,21,18};
//    System.out.println(VerifySquenceOfBST(array));

    //[[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]]
    //[1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10]
    // 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10,
    int[][] matrix = new int[][]{{1, 2, 3, 4}/*,{5,6,7,8},{9,10,11,12},{13,14,15,16}*/};
    ListNode listNode = new ListNode(1);
    listNode.next = new ListNode(2);
    listNode.next.next = new ListNode(3);
    listNode.next.next.next = new ListNode(4);
    listNode.next.next.next.next = new ListNode(5);
//    while (listNode != null) {
//      System.out.println(listNode.val);
//      listNode = listNode.next;
//    }


    int[][] people = new int[][] {{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};

//    System.out.println(lengthOfLIS(new int[]{1,2,-10,-8,-7}));;
//    System.out.println(lengthOfLIS2(new int[]{1,2,-10,-8,-7}));;

    int i = 1 & 31;
    int j = 311 % 32;
    System.out.println("i = " + i);
    System.out.println("j = " + j);

  }


  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode head = dummy;
    int carry = 0;

    while (l1 != null || l2 != null) {
      int l1Val = l1 == null ? 0 : l1.val;
      int l2Val = l2 == null ? 0 : l2.val;
      int sum = l1Val + l2Val + carry;
      carry = sum / 10;
      head.next = new ListNode(sum % 10);
      if (l1 != null) {
        l1 = l1.next;
      }
      if (l2 != null) {
        l2 = l2.next;
      }
      head = head.next;
    }

    if (carry != 0) {
      head.next = new ListNode(carry);
    }
    return dummy.next;
  }


  public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int maxLength = 0;
    int left = 0;

    for (int i = 0; i < s.length(); i++) {
      if (map.containsKey(s.charAt(i))) {
        left = map.get(s.charAt(i)) + 1;
      }
      maxLength = Math.max(maxLength, i - left + 1);
      map.put(s.charAt(i), i);
    }
    return maxLength;
  }

  public int maxArea(int[] height) {
    int leftIndex = 0;
    int rightIndex = height.length - 1;
    int result = 0;

    while (leftIndex < rightIndex) {
      if (height[leftIndex] < height[rightIndex]) {
        result = Math.max(result, (rightIndex - leftIndex) * height[leftIndex]);
        leftIndex++;
      } else {
        result = Math.max(result, (rightIndex - leftIndex) * height[rightIndex]);
        rightIndex--;
      }
    }
    return result;
  }

  public int getWater(int[] height) {
    int leftIndex = 1;
    int rightIndex = height.length - 2;
    int leftMax = height[0];
    int rightMax = height[height.length - 1];
    int result = 0;

    while (leftIndex < rightIndex) {
      if (leftMax < rightMax) {
        result += Math.max(0, leftMax - height[leftIndex]);
        leftMax = Math.max(leftMax, height[leftIndex]);
        leftIndex++;
      } else {
        result += Math.max(0, rightIndex - height[rightIndex]);
        rightMax = Math.max(rightMax, height[rightIndex]);
        rightIndex--;
      }
    }
    return result;

  }

  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);

    for (int first = 0; first < nums.length; first++) {
      if (first > 0 && nums[first] == nums[first - 1]) {
         continue;
      }

      int third = nums.length - 1;
      int target = -nums[first];

      for (int second = first + 1; second < nums.length; second++) {
        if (second > first + 1 && nums[second] == nums[second - 1]) {
          continue;
        }
        while (third > second && nums[second] + nums[third] > target) {
          third--;
        }
        if (second == third) {
          break;
        }
        if (nums[second] + nums[third] == target) {
          List<Integer> list = new ArrayList<>();
          list.add(nums[first]);
          list.add(nums[second]);
          list.add(nums[third]);
          result.add(list);
        }
      }
    }
    return result;
  }

  public List<String> letterCombinations(String digits) {
    String[] phone = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    List<String> result = new ArrayList<>();
    letterCombinationsBackTrack(digits, 0, "", phone, result);
    return result;
  }

  public void letterCombinationsBackTrack(String digits, Integer digitIndex, String temp, String[] phone, List<String> result) {
    if (temp.length() == digits.length()) {
      result.add(temp);
      return;
    }

    int index = digits.charAt(digitIndex) - '0';
    for (int i = 0; i < phone[index].length(); i++) {
      temp += phone[index].charAt(i);
      letterCombinationsBackTrack(digits, digitIndex + 1, temp, phone, result);
      temp = temp.substring(0, temp.length() - 1);
    }
  }

  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;


    for (int i = 0; i < n; i++) {
      if (head == null) {
        return head;
      }
      head = head.next;
    }

    ListNode slow = dummy;
    while (head != null) {
      head = head.next;
      slow = slow.next;
    }

    slow.next = slow.next.next;
    return dummy.next;

  }

  public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    generateParenthesisBackTrack(result, 0, 0, n, "");
    return result;
  }

  public void generateParenthesisBackTrack(List<String> result, int left, int right, int n, String temp) {
    if (temp.length() == n * 2) {
      result.add(temp);
      return;
    }

    if (left < n) {
      generateParenthesisBackTrack(result, left + 1, right, n, temp + "(");

    }
    if (right < n) {
      generateParenthesisBackTrack(result, left, right + 1, n, temp + ")");
    }
  }

  public void nextPermutation(int[] nums) {
    //从后往前，找到第一个下降的数
    //再从后往前，找到第一个比下降的数大的数
    //交换位置
    //然后再把下降的数的位置到最后的数反序

    int left = nums.length - 2;
    while (left >= 0 && nums[left] >= nums[left + 1]) {
      left--;
    }

    int right = nums.length - 1;
    if (left >= 0) {
      while (right > left && nums[right] <= nums[left]) {
        right--;
      }
      swap(nums, left, right);
    }
    reverse(nums, left + 1, nums.length - 1);
  }

  public void swap(int[] nums, int left, int right) {
    int temp = nums[left];
    nums[left] = nums[right];
    nums[right] = temp;
  }

  public void reverse (int[] nums, int left, int right) {
    while (left < right) {
      int temp = nums[left];
      nums[left] = nums[right];
      nums[right] = temp;
      left++;
      right--;
    }
  }

  public int search(int[] nums, int target) {
    //二分查找
    int start = 0;
    int end = nums.length - 1;

    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (nums[mid] == target) {
        return mid;
      }

      if (nums[start] <= nums[mid]) {
        //左侧是升序的
        if (target >= nums[start] && target < nums[mid]) {
          end = mid - 1;
        } else {
          start = mid + 1;
        }
      } else {
        //右侧是升序的
        if (start > nums[mid] && start <= nums[end]) {
          start = mid + 1;
        } else {
          end = mid - 1;
        }
      }
    }
    return -1;
  }

  public int[] searchRange(int[] nums, int target) {
    //二分法

    int left = searchRangeSub(nums, target, true);
    int right = searchRangeSub(nums, target, false) - 1;
    return new int[2];

  }

  public int searchRangeSub(int[] nums, int target, boolean isFirst) {
    int start = 0;
    int end = nums.length - 1;
    int result = -1;

    while (start <= end) {
      int mid = start + (end - start) / 2;
      if ((isFirst && nums[mid] >= target) || nums[mid] > target) {
        end = mid - 1;
        result = mid;
      } else {
        start = mid + 1;
      }
    }
    return result;
  }

  public List<List<Integer>> combinationSum(int[] candidates, int target) {

    List<List<Integer>> result = new ArrayList<>();
    combinationSumBackTrack(candidates, target, result, new ArrayList<>(), 0, 0);
    return result;
  }

  public void combinationSumBackTrack(int[] candidates, int target, List<List<Integer>> result, List<Integer> list, int start, int sum) {
    if (sum > target) {
      return;
    }
    if (sum == target) {
      result.add(new ArrayList<>(list));
      return;
    }

    for (int i = start; i < candidates.length; i++) {
      sum += candidates[i];
      list.add(candidates[i]);
      combinationSumBackTrack(candidates, target, result, list, start, sum);
      sum -= candidates[i];
      list.remove(list.size() - 1);
    }
  }
  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    permuteBackTrack(nums, result, new ArrayList<>(), used);
    return result;
  }

  public void permuteBackTrack(int[] nums, List<List<Integer>> result, List<Integer> list, boolean[] used) {
    if (list.size() == nums.length) {
      result.add(new ArrayList<>(list));
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      if (used[i]) {
        continue;
      }

      used[i] = true;
      list.add(nums[i]);
      permuteBackTrack(nums, result, list, used);
      used[i] = false;
      list.remove(list.size() - 1);
    }
  }

  public boolean canJump(int[] nums) {
    int remoteIndex = 0;

    for (int i = 0; i < nums.length; i++) {
      if (i <= remoteIndex) {
        remoteIndex = Math.max(remoteIndex, i + nums[i]);
        if (remoteIndex >= nums.length) {
          return true;
        }
      }
    }
    return false;
  }

  public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;

    int[][] dp = new int[m][n];
    dp[0][0] = grid[0][0];

    for (int i = 1; i < m; i++) {
      dp[i][0] = dp[i - 1][0] + grid[i][0];
    }
    for (int i = 1; i < n; i++) {
      dp[0][i] = dp[0][i - 1] + grid[0][i];
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
      }
    }
    return dp[m - 1][n - 1];
  }

  public boolean isValidBST(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    int prev = Integer.MIN_VALUE;

    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.push(curr);
        curr = curr.left;
      } else {
        curr = stack.pop();
        if (curr.val < prev) {
          return false;
        }
        prev = curr.val;
        curr = curr.right;
      }
    }
    return true;
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int size = queue.size();
      List<Integer> list = new ArrayList<>();
      for (int i = 0; i < size; i++) {
        TreeNode poll = queue.poll();
        list.add(poll.val);
        if (poll.left != null) {
          queue.offer(poll.left);
        }

        if (poll.right != null) {
          queue.offer(poll.right);
        }
      }
      result.add(list);
    }
    return result;
  }

  public void flatten(TreeNode root) {
    TreeNode curr = root;
    while (curr != null) {
      if (curr.left != null) {
        TreeNode temp = curr.left;
        while (temp != null) {
          temp = temp.right;
        }
        temp.right = curr.right;
        curr.right = curr.left;
        curr.left = null;
      }
      curr = curr.right;
    }
  }

  public int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
      set.add(num);
    }

    int result = 0;
    for (int num : nums) {
      if (!set.contains(num - 1)) {
        int curr = num;
        int currLength = 0;
        while (set.contains(curr)) {
          curr++;
          currLength++;
        }
        result = Math.max(result, currLength);
      }
    }
    return result;
  }

  public boolean wordBreak(String s, List<String> wordDict) {
    Set<String> set = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;

    for (int i = 0; i <= s.length(); i++) {
      for (int j = 0; j <= i; j++) {
        if (dp[j] && set.contains(s.substring(j, i))) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[s.length()];
  }
  public ListNode detectCycle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head.next;

    while (slow != fast) {
      if (fast == null || fast.next == null) {
        return null;
      }
      slow = slow.next;
      fast = fast.next.next;
    }

    while (head != slow.next) {
      head = head.next;
      slow = slow.next;
    }

    return head;
  }

  public ListNode sortList(ListNode head) {
    //二分+归并

    return sortListSub(head);
  }

  public ListNode sortListSub(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode mid = findMid(head);

    ListNode right = sortListSub(mid.next);
    right.next = null;

    ListNode left = sortListSub(head);

    return merge(left, right);
  }

  public ListNode findMid(ListNode head) {
    if (head == null) {
      return head;
    }

    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != null && fast.next != null) {
      fast = fast.next.next;
      slow = slow.next;
    }
    return slow;
  }

  public ListNode merge(ListNode left, ListNode right) {
    if (left == null && right == null) {
      return null;
    }

    ListNode dummy = new ListNode(0);
    ListNode head = dummy;
    while (left != null && right != null) {
      if (left.val <= right.val) {
        head.next = left;
        left = left.next;
      } else {
        head.next = right;
        right = right.next;
      }
      head = head.next;
    }

    if (left != null) {
      head.next = left;
    }

    if (right != null) {
      head.next = right;
    }


    return dummy.next;
  }

  public int maxProduct(int[] nums) {
    ///两个数组，分别记录最大值和最小值，判断当前值的正负来更新

    int[] max = new int[nums.length];
    int[] min = new int[nums.length];
    max[0] = nums[0];
    min[0] = nums[0];

    int result = 0;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] >= 0) {
        max[i] = Math.max(nums[i], max[i - 1] * nums[i]);
        min[i] = Math.min(nums[i], min[i - 1] * nums[i]);
      }else {
        max[i] = Math.max(nums[i], min[i - 1] * nums[i]);
        min[i] = Math.min(nums[i], max[i - 1] * nums[i]);
      }
      result = Math.max(result, max[i]);
    }

    return result;
  }

  public int numIslands(char[][] grid) {
    int count = 0;
    int m = grid.length;
    int n = grid[0].length;

    for (int i = 0; i < m; i ++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == '1') {
          count++;
          numIslandsSub(grid, i, j, m, n);
        }
      }
    }
    return count;
  }

  public void numIslandsSub(char[][] grid, int row, int column, int rowLength, int columnLength) {
    if (row < 0 || row >= rowLength || column < 0 || column >= columnLength || grid[row][column] == '0') {
      return;
    }

    grid[row][column] = '0';
    numIslandsSub(grid, row + 1, column, rowLength, columnLength);
    numIslandsSub(grid, row - 1, column, rowLength, columnLength);
    numIslandsSub(grid, row, column + 1, rowLength, columnLength);
    numIslandsSub(grid, row, column - 1, rowLength, columnLength);
  }

  public int findKthLargest(int[] nums, int k) {
    return findKthLargestSub(nums, 0, nums.length - 1, k);
  }

  public int findKthLargestSub(int[] nums, int start, int end, int k) {
    //返回等于区间的右角标
    int partition = partition(nums, start, end);
    if (partition == k) {
      return nums[partition];
    }

    if (partition > k) {
      return findKthLargestSub(nums, start, partition - 1, k);
    } else {
      return findKthLargestSub(nums, partition + 1, end, k);
    }
  }

  public int partition(int[] nums, int start, int end) {
    int less = start - 1;
    int more = end;

    while (start < more) {
      if (nums[start] < nums[end]) {
        less++;
        swap(nums, less, start);
        start++;
      } else if (nums[start] > nums[end]) {
        swap(nums, start, more);
        more--;
      } else {
        less++;
      }
    }

    swap(nums, more, end);
    return more;
  }

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || p == root || q == root) {
      return root;
    }

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left == null && right == null) {
      return null;
    }

    if (left == null) {
      return right;
    }

    if (right == null) {
      return left;
    }
    return root;
  }


  public int lengthOfLIS(int[] nums) {
    //动态规划+二分法
    int[] dp = new int[nums.length + 1];
    dp[1] = nums[0];
    int length = 1;

    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > dp[length]) {
        dp[length++] = nums[i];
      } else {
        //二分查找，找到合适的位置，保证dp是递增的
        int left = 1;
        int right = nums.length;
        while (left <= right) {
          int mid = left + (right - left) / 2;
          if (nums[mid] > nums[i]) {
            left = mid + 1;
          } else {
            right = mid - 1;
          }
          dp[left] = nums[i];
        }
      }
    }
    return length;
  }





































}










