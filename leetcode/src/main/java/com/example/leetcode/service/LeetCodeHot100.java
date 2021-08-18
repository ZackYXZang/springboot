package com.example.leetcode.service;

import com.example.leetcode.utils.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import org.springframework.stereotype.Service;

/**
 * @author yuxiangzang
 * @create 2021-05-24-下午4:39
 * @desc 力扣热门100题
 **/
@Service("leetCodeHot")
public class LeetCodeHot100 {

  //1. 两数之和
  //给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值的那两个整数，并返回它们的数组下标。
  //你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
  //你可以按任意顺序返回答案。
  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(target - nums[i])) {
        return new int[]{map.get(target - nums[i]), i};
      }
      map.put(nums[i], i);
    }
    return new int[0];
  }

  public class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  //2. 两数相加
  //给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
  //请你将两个数相加，并以相同形式返回一个表示和的链表。
  //你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode();
    ListNode head = dummy;

    ListNode firstHead = l1;
    ListNode secondHead = l2;
    int carry = 0;

    while (firstHead != null || secondHead != null) {
      int firstVal = firstHead == null ? 0 : firstHead.val;
      int secondVal = secondHead == null ? 0 : secondHead.val;
      int sum = firstVal + secondVal + carry;
      if (sum >= 10) {
        carry = 1;
      } else {
        carry = 0;
      }
      head.next = new ListNode(sum % 10);
      head = head.next;

      firstHead = firstHead == null ? null : firstHead.next;
      secondHead = secondHead == null ? null : secondHead.next;
    }
    if (carry != 0) {
      head.next = new ListNode(1);
    }
    return dummy.next;
  }

  //3. 无重复字符的最长子串
  //给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
  public int lengthOfLongestSubstring(String s) {

    /*Set<Character> window = new HashSet<>();
    int rightIndex = -1;
    int max = 0;
    int length = s.length();

    for (int i = 0; i < length; i++) {
      //每次循环时把上一个character移除
      if (i != 0) {
        window.remove(s.charAt(i - 1));
      }
      while (rightIndex + 1 < length && !window.contains(s.charAt(rightIndex + 1))) {
        window.add(s.charAt(rightIndex + 1));
        rightIndex++;
      }
      max = Math.max(max, window.size());
    }
    return max;*/

    int left = 0;
    int max = 0;
    //value是角标
    Map<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      if (map.containsKey(s.charAt(i))) {
        //为什么需要max，也就是何时left > map.get(s.charAt(i)) + 1
        //比如"abba"，第二个b的时候，left更新成2；第二次a的时候，left = Math.max(2, 0 + 1);
        left = Math.max(left, map.get(s.charAt(i)) + 1);
        System.out.println(left);
      }
      map.put(s.charAt(i), i);
      max = Math.max(max, i - left + 1);
    }
    return max;
  }

  //4. 寻找两个正序数组的中位数
  //给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
  //这里使用的二分查找的办法，两个驻足分别取中位数的一半记性比较，小的一半就呗过滤
  //时间复杂度：O(log(m+n))O(log(m+n))，空间复杂度：O(1)O(1)
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int length1 = nums1.length;
    int length2 = nums2.length;
    int totalLength = length1 + length2;
    if (totalLength % 2 == 1) {
      //奇数情况下 中位数位于(m + n) / 2 + 1
      int midIndex = totalLength / 2;
      double median = getKthElement(nums1, nums2, midIndex + 1);
      return median;
    } else {
      //偶数情况下，中位数是(m + n) / 2 ，(m + n) / 2 + 1之和除以2
      double median = (getKthElement(nums1, nums2, totalLength / 2) + getKthElement(nums1, nums2,
          totalLength / 2 + 1)) / 2.0;
      return median;
    }
  }

  public int getKthElement(int[] nums1, int[] nums2, int k) {
    /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
     * 这里的 "/" 表示整除
     * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
     * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
     * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
     * 这样 pivot 本身最大也只能是第 k-1 小的元素
     * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
     * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
     * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
     */

    int length1 = nums1.length;
    int length2 = nums2.length;
    //两个数组的起始位置，会随着过滤不满足条件的数组部分而移动
    int index1 = 0;
    int index2 = 0;
    int kthElement = 0;

    while (true) {
      // 边界情况
      if (index1 == length1) {
        return nums2[index2 + k - 1];
      }
      if (index2 == length2) {
        return nums1[index1 + k - 1];
      }
      if (k == 1) {
        return Math.min(nums1[index1], nums2[index2]);
      }

      // 正常情况（这里k是可以代表中位数的位置）
      //这里为什么取k / 2 - 1， 因为(k / 2 - 1) + (k / 2 - 1) = k - 2 <= k，可以直接排除掉k / 2 - 1前的数（包含k / 2 - 1位置上的数）
      //如果取k / 2，那么k / 2 + k / 2  = k / 2，没有办法直接排除掉 k / 2之前的数（包含 k / 2）
      int half = k / 2;
      //分别在两个数组中，找到中位数的一半或者数组长度的前一位
      int newIndex1 = Math.min(index1 + half, length1) - 1;
      int newIndex2 = Math.min(index2 + half, length2) - 1;
      //比较两个的值哪个大
      //如果nums1的值小，过滤掉数组nums1[newIndex1]前面的部分
      //如果nums2的值小，过滤掉数组nums2[newIndex2]前面的部分
      if (nums1[newIndex1] <= nums2[newIndex2]) {
        //因为排除一定在中位数前面数字的长度，所以中位数的位置也要随着改变
        k -= (newIndex1 - index1 + 1);
        index1 = newIndex1 + 1;
      } else {
        k -= (newIndex2 - index2 + 1);
        index2 = newIndex2 + 1;
      }
    }
  }


  //最长回文子传
  //给你一个字符串 s，找到 s 中最长的回文子串。
  public class Palindrome {

    /**
     * 思路一： 动态规划的思路： P(i,j) = P(i+1, j-1) && (Si == Sj) 也就是如果字符串区间在[i，j]是回文传，必须满足在[i+1，j-1]位置是回文传，并且字符串在i和j位置的字符要想等
     * 边界条件：字符传长度为1的时候，是回文传，字符长度为2的时候，两个字符传相等是回文传
     */


    public String longestPalindrome1(String s) {
      int len = s.length();
      if (len < 2) {
        return s;
      }

      int maxLen = 1;
      int begin = 0;
      // dp[i][j] 表示 s[i..j] 是否是回文串
      boolean[][] dp = new boolean[len][len];
      // 初始化：所有长度为 1 的子串都是回文串
      for (int i = 0; i < len; i++) {
        dp[i][i] = true;
      }

      char[] charArray = s.toCharArray();
      // 递推开始
      // 先枚举子串长度
      for (int L = 2; L <= len; L++) {
        // 枚举左边界，左边界的上限设置可以宽松一些
        for (int i = 0; i < len; i++) {
          // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
          int j = L + i - 1;
          // 如果右边界越界，就可以退出当前循环
          if (j >= len) {
            break;
          }

          if (charArray[i] != charArray[j]) {
            dp[i][j] = false;
          } else {
            if (j - i < 3) {
              //j - i = 0, 1, 2，且charArray[i] == charArray[j]
              //这个时候一般是最小回文传了，直接赋值true
              dp[i][j] = true;
            } else {
              //dp方程
              dp[i][j] = dp[i + 1][j - 1];
            }
          }

          // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
          if (dp[i][j] && j - i + 1 > maxLen) {
            maxLen = j - i + 1;
            begin = i;
          }
        }
      }
      return s.substring(begin, begin + maxLen);
    }

    //思路二：
    //中心扩展
    //如果一个中心点往两边扩散，比较边界的两个字符是否相等，记录最长的情况下的长度
    public String longestPalindrome2(String s) {
      if (s == null || s.length() < 1) {
        return "";
      }
      int start = 0, end = 0;
      for (int i = 0; i < s.length(); i++) {
        int len1 = expandAroundCenter(s, i, i);
        int len2 = expandAroundCenter(s, i, i + 1);
        int len = Math.max(len1, len2);
        if (len > end - start) {
          start = i - (len - 1) / 2;
          end = i + len / 2;
        }
      }
      return s.substring(start, end + 1);
    }

    public int expandAroundCenter(String s, int left, int right) {
      while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        --left;
        ++right;
      }
      return right - left - 1;
    }

    //思路三：
    //借助一个数组表示每个位置的回文半径
    //如果是找i位置的回文半径，就根据以当时的回文中心j为中点对称的i`位置的回文半径(已知)取继续求
    public String longestPalindrome3(String s) {
      if (s == null || s.length() == 0) {
        return "";
      }
      char[] charArr = manacherString(s);
      int[] pArr = new int[charArr.length];

      int index = -1;
      int pR = -1;
      int max = Integer.MIN_VALUE;
      String maxStr = "";

      for (int i = 0; i < charArr.length; i++) {
        pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1;
        while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
          if (charArr[i + pArr[i]] == charArr[i - pArr[i]]) {
            pArr[i]++;
          } else {
            break;
          }
        }

        if (i + pArr[i] > pR) {
          index = i;
          pR = i + pArr[i];
        }

        if (max < pArr[i]) {
          max = pArr[i];
          int end = (i + pArr[i]) / 2;
          int start = end >= (i + 1 - pArr[i]) / 2 ? (i + 1 - pArr[i]) / 2 : end;
          maxStr = s.substring(start, end);

        }
      }

      return maxStr;

    }

    /**
     * 以防偶数的情况 构建一个str.length * 2 + 1的数组 并且使偶数位置为#，奇数位置为字符串本身 之后计算的值 /2就可以，无论原字符串是奇偶长度
     *
     * @param str
     * @return
     */
    public char[] manacherString(String str) {
      char[] charArr = str.toCharArray();
      char[] res = new char[str.length() * 2 + 1];
      int index = 0;
      for (int i = 0; i != res.length; i++) {
        res[i] = (i & 1) == 0 ? '#' : charArr[index++];
      }
      return res;
    }

  }

  //盛最多水的容器
  //给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
  //双指针的方法，时间复杂度O(N)

  public int maxArea(int[] height) {
    if (height == null || height.length == 0) {
      return 0;
    }
    int result = 0;
    int leftIndex = 0;
    int rightIndex = height.length - 1;

    while (leftIndex < rightIndex) {
      result = Math
          .max(result, (rightIndex - leftIndex) * Math.min(height[leftIndex], height[rightIndex]));
      if (height[leftIndex] < height[rightIndex]) {
        leftIndex++;
      } else {
        rightIndex--;
      }
    }
    return result;
  }

  //三数之和
  //给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
  //注意：答案中不可以包含重复的三元组。
  //时间复杂度O(N)
  //解法：
  //1. 如果nums == null || nums.length == 0，直接返回空
  //2. 对数组进行排序
  //3. 遍历数组
  //3.1 如果nums[i] > 0，那么就不用向后遍历了，因为不可能加和等于0
  //3.2 因为是不重复的，所以需要判断nums[i]是否和nums[i - 1]重复，重复就跳过
  //3.3 L=i+1， R= length - 1，如果加和大于0，R--；如果加和小于0，L++，同时需要过滤左边界和右边界是否和他的下一位重复
  public List<List<Integer>> threeSum(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new ArrayList<>();
    }
    //时间复杂度O(NlogN)
    Arrays.sort(nums);

    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > 0) {
        return result;
      }
      //过滤重复
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      int leftIndex = i + 1;
      int rightIndex = nums.length - 1;
      while (leftIndex < rightIndex) {
        int sum = nums[i] + nums[leftIndex] + nums[rightIndex];
        if (sum == 0) {
          List<Integer> list = new ArrayList<>();
          list.add(nums[i]);
          list.add(nums[leftIndex]);
          list.add(nums[rightIndex]);
          result.add(list);
          //过滤重复：过滤左边界和右边界是否和他的下一位重复
          while (leftIndex < rightIndex && nums[leftIndex] == nums[leftIndex + 1]) {
            leftIndex++;
          }
          while (leftIndex < rightIndex && nums[rightIndex] == nums[rightIndex - 1]) {
            rightIndex--;
          }
          leftIndex++;
          rightIndex--;
        } else if (sum < 0) {
          leftIndex++;
        } else {
          rightIndex--;
        }
      }
    }
    return result;

  }

  //电话号码的字母组合
  //给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
  //给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
  //输入：digits = "23"
  //输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
  //用回朔方法
  public List<String> letterCombinations(String digits) {

    String[] phone = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv",
        "wxyz"};
    List<String> list = new ArrayList<>();
    if (digits == null || digits.length() == 0) {

      return list;
    }
    backTrack(list, phone, digits, "", 0);
    return list;
  }

  private void backTrack(List<String> list, String[] phone, String digits, String tempString,
      int start) {
    if (start == digits.length()) {
      list.add(tempString);
      return;
    }

    int index = digits.charAt(start) - '0';
    for (int i = 0; i < phone[index].length(); i++) {
      backTrack(list, phone, digits, tempString + phone[index].charAt(i), start + 1);
//      tempString = tempString + phone[index].charAt(i);
//      backTrack(list, phone, digits, tempString, start + 1);
//      tempString = tempString.substring(0, tempString.length() - 1);
    }
  }


  //删除链表的倒数第 N 个结点
  //给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
  //进阶：你能尝试使用一趟扫描实现吗？
  public ListNode removeNthFromEnd(ListNode head, int n) {
    if (head == null) {
      return head;
    }

    ListNode dummy = new ListNode(0);
    dummy.next = head;

    ListNode slow = dummy;

    //此时head走到第n + 1个节点的位置
    for (int i = 0; i < n; i++) {
      if (head == null) {
        return head;
      }
      head = head.next;
    }

    //当head走到最后当时候，slow走到链表长度 - (n + 1) 的位置
    while (head != null) {
      head = head.next;
      slow = slow.next;
    }

    //删除第n个节点
    slow.next = slow.next.next;
    return dummy.next;
  }

  //有效的括号
  //给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
  //有效字符串需满足：
  //左括号必须用相同类型的右括号闭合。
  //左括号必须以正确的顺序闭合。
  public boolean isValid(String s) {
    if (s == null || s.length() < 2) {
      return false;
    }

    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
      if (c == '(') {
        stack.push(')');
      } else if (c == '[') {
        stack.push(']');
      } else if (c == '{') {
        stack.push('}');
      } else if (stack.isEmpty() || stack.pop() != c) {
        //stack.isEmpty()，用来校验)]}多的情况
        //stack.pop() != c，用来校验顺序不对的情况
        return false;
      }
    }
    //用来校验([{多的情况
    return stack.isEmpty();
  }

  //合并两个有序链表
  //将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) {
      return l2;
    }
    if (l2 == null) {
      return l1;
    }

    ListNode dummy = new ListNode(0);
    ListNode head = dummy;

    while (l1 != null && l2 != null) {
      if (l1.val > l2.val) {
        head.next = l2;
        l2 = l2.next;
        head = head.next;
      } else {
        head.next = l1;
        l1 = l1.next;
        head = head.next;
      }
    }

    if (l1 != null) {
      head.next = l1;
    }
    if (l2 != null) {
      head.next = l2;
    }
    return dummy.next;
  }


  //括号生成
  //数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
  //方法一、递归，举出所有可能，然后判断是否是有效的；时间复杂度O((2^2n) * n)，空间复杂度O(n)
  //方法二、回朔方法，时间复杂度O((4^n) / (n ^ 1/2))，空间复杂度O(n)
  public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    if (n <= 0) {
      return result;
    }
    backTrackGenerateParenthesis(result, "", 0, 0, n);
    return result;
  }

  private void backTrackGenerateParenthesis(List<String> result, String tempString, int left,
      int right, int n) {
    if (tempString.length() == n * 2) {
      result.add(tempString);
      return;
    }

    //左括号的数量一定小于n个
    if (left < n) {
      backTrackGenerateParenthesis(result, tempString + "(", left + 1, right, n);
    }

    //右括号一定小于等于左括号的数量
    if (right < left) {
      backTrackGenerateParenthesis(result, tempString + ")", left, right + 1, n);
    }
  }

  //合并K个升序链表
  //给你一个链表数组，每个链表都已经按升序排列。
  //请你将所有链表合并到一个升序链表中，返回合并后的链表。
  //两种思路：
  // 1。使用大根堆，时间复杂度O(nlogk)，空间复杂度O(k)
  // 2。分治，时间复杂度O(nlogk)，空间复杂度O(logk)
  public ListNode mergeKLists(ListNode[] lists) {
    //大根堆
    ListNode priorityMethod = mergeKListsPriorityQueue(lists);
    //分治
    ListNode divideAndConquer = mergeKListsDivideAndConquer(lists);
    return priorityMethod;
  }

  //分治
  public ListNode mergeKListsDivideAndConquer(ListNode[] lists) {
    if (lists == null || lists.length == 0) {
      return null;
    }

    return mergeKListsDivideAndConquer(lists, 0, lists.length - 1);

  }

  public ListNode mergeKListsDivideAndConquer(ListNode[] lists, int start, int end) {
    if (start == end) {
      return lists[start];
    }
    if (start > end) {
      return null;
    }

    int mid = start + (end - start) / 2;
    ListNode left = mergeKListsDivideAndConquer(lists, start, mid);
    ListNode right = mergeKListsDivideAndConquer(lists, mid + 1, end);
    return mergeKListsDivideAndConquer(left, right);
  }

  public ListNode mergeKListsDivideAndConquer(ListNode left, ListNode right) {
    if (left == null) {
      return right;
    }
    if (right == null) {
      return left;
    }

    ListNode dummy = new ListNode(0);
    ListNode head = dummy;
    while (left != null && right != null) {
      if (left.val < right.val) {
        head.next = left;
        left = left.next;
      } else {
        head.next = right;
        right = right.next;
      }
      head = head.next;
    }
    head.next = left == null ? right : left;
    return dummy.next;
  }

  //大根堆
  public ListNode mergeKListsPriorityQueue(ListNode[] lists) {
    if (lists == null || lists.length == 0) {
      return null;
    }
    PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, new Comparator<ListNode>() {
      @Override
      public int compare(ListNode o1, ListNode o2) {
        if (o1.val - o2.val > 0) {
          return 1;
        }
        if (o1.val - o2.val < 0) {
          return -1;
        }
        return 0;
      }
    });

    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    for (ListNode listNode : lists) {
      if (listNode != null) {
        queue.add(listNode);
      }
    }

    while (!queue.isEmpty()) {
      tail.next = queue.poll();
      tail = tail.next;
      if (tail.next != null) {
        queue.add(tail.next);
      }
    }
    return dummy.next;
  }

  //下一个排列
  //实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
  //如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
  //必须 原地 修改，只允许使用额外常数空间。
  public void nextPermutation(int[] nums) {
    if (nums == null || nums.length < 2) {
      return;
    }

    int left = nums.length - 2;
    while (left >= 0 && nums[left] >= nums[left + 1]) {
      left--;
    }

    System.out.println("left = " + left);

    if (left >= 0) {
      int right = nums.length - 1;
      //为什么要nums[right] <= nums[left]，而不是nums[right] < nums[left]
      //[1, 5, 1]为例，left = 0，right = 2，此时right应该右移，但是如果不加等号，1 = 1，就不会右移
      while (right > left && nums[right] <= nums[left]) {
        right--;
      }
      System.out.println("right = " + right);
      swap(nums, left, right);
    }

    reverse(nums, left + 1, nums.length - 1);
  }

  public void swap(int[] nums, int left, int right) {
    int temp = nums[left];
    nums[left] = nums[right];
    nums[right] = temp;
  }

  public void reverse(int[] nums, int left, int right) {
    while (left < right) {
      int temp = nums[left];
      nums[left] = nums[right];
      nums[right] = temp;
      left++;
      right--;
    }
  }

  //最长有效括号
  //给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
  //方法一：动态规划，因为是求最长，这种情况可以考虑动态规划
  //方法二：栈
  //方法三：双指针
  public int longestValidParentheses(String s) {
//    return longestValidParenthesesDP(s);
//    return longestValidParenthesesStack(s);
    return longestValidParenthesesTwoPointers(s);

  }

  public int longestValidParenthesesDP(String s) {
    if (s == null || s.equals("")) {
      return 0;
    }

    //DP方程：
    //这里是需要当位置是")"的时候需要判断，其他情况不需要判断，
    // 是")"的时候，又分为两种情况
    //情况一：如果最后一位是")"，且它的前一位是"("，那么动态方程就是dp[i] = dp[i - 2] + 2
    //情况二：如果最后一位是")"，且他的前一位是")"，那么动态方程就是dp[i] = dp[i - 1] + dp[i - dp[i - 1] - 2] + 2
    //情况二解释：为什么是dp[i - dp[i - 1] - 2]，因为要加上前面符合条件的有效字符串；比如"(()(()))"，在位置6，就是dp[6] = dp[5] + dp[6 - dp[5] - 2] + 2，
    int maxLength = 0;
    int dp[] = new int[s.length()];

    for (int i = 1; i < s.length(); i++) {
      if (s.charAt(i) == ')') {
        if (s.charAt(i - 1) == '(') {
          dp[i] = (i > 2 ? dp[i - 2] : 0) + 2;
        } else if (i - dp[i - 1] > 0 /**"())"**/
            && s.charAt(i - dp[i - 1] - 1) == '('  /**")()())"**/) {
          dp[i] = dp[i - 1] + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
        }
      }
      maxLength = Math.max(maxLength, dp[i]);
    }

    for (int i = 0; i < s.length(); i++) {
      System.out.println("i:" + dp[i]);
    }
    return maxLength;
  }

  public int longestValidParenthesesStack(String s) {
    if (s == null || s.equals("")) {
      return 0;
    }
    //思路是：栈是用来放角标的，操作栈分为两种情况
    //1。如果是"("， 把角标放入栈中
    //2。如果是")"，那么就从栈里取出"("
    //2。1但是当栈空了当时候，且字符是")"，就放入栈中，也就是最后一个没有被匹配的右括号的下标
    //2。2每次计算长度都是用当前位置，减去stack.pop()之后，栈顶的位置
    int maxLength = 0;

    Stack<Integer> stack = new Stack<>();
    //在初始化时，如果一开始栈为空，第一个字符为左括号的时候我们会将其放入栈中，这样就不满足提及的「最后一个没有被匹配的右括号的下标」，为了保持统一，我们在一开始的时候往栈中放入一个值为 -1−1 的元素。
    stack.push(-1);
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        stack.push(i);
      } else {
        stack.pop();
        if (stack.isEmpty()) {
          stack.push(i);
        } else {
          maxLength = Math.max(maxLength, i - stack.peek());
        }
      }
    }
    return maxLength;
  }

  public int longestValidParenthesesTwoPointers(String s) {
    int left = 0, right = 0, maxlength = 0;
    //分别记录左括号，右括号的数量
    //从左到右，碰到左括号，左括号计数加1，碰到右括号，右括号计数加1
    //如果右括号大于左括号的数量，那么清零
    //如果左括号的数量和右括号相等，此时是有效长度，记录下来
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        left++;
      } else {
        right++;
      }
      if (left == right) {
        maxlength = Math.max(maxlength, 2 * right);
      } else if (right > left) {
        left = right = 0;
      }
    }

    //因为前面只考虑了右括号多余左括号的时候，但是如果左括号一直大于右括号，那么就得不到有效长度，比如: "(()"
    //所以从右至左再来一遍即可
    left = right = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      if (s.charAt(i) == '(') {
        left++;
      } else {
        right++;
      }
      if (left == right) {
        maxlength = Math.max(maxlength, 2 * left);
      } else if (left > right) {
        left = right = 0;
      }
    }
    return maxlength;
  }

  //搜索旋转排序数组 [4, 5, 6, 7, 0, 1, 2]
  //整数数组 nums 按升序排列，数组中的值 互不相同 。
  //在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
  //给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
  //方法一：遍历，时间复杂度O(n)
  //方法二：二分查找时间复杂度O(logn)
  public int search(int[] nums, int target) {
    //二分查找，判断边界条件，主要是判断nums[mid]在nums[n-1]，前面，还是在nums[n-1]后面，已知nums[n-1] > nums[k] > nums[k - 1] > nums[0]
    //[4, 5, 6] 和 [7, 0, 1, 2]
    //情况一：nums[start]到nums[mid]有序，且nums[start]< target < nums[mid]，那么就在[start, mid - 1]范围内找，否则就在[mid + 1, end]中找
    //[4, 5, 6, 7, 0] 和 [1, 2]
    //情况二：nums[mid]到nums[end]有序，且nums[mid]< target < nums[end]，那么就在[mid + 1, end]范围内找，否则就在[start, mid - 1]中找
    if (nums == null || nums.length == 0) {
      return -1;
    }
    if (nums.length == 1) {
      return nums[0] == target ? 0 : -1;
    }

    int start = 0;
    int end = nums.length - 1;

    while (start <= end) {
      //取等号的原因：缩小范围的方法是在mid + ，或者mid - 1，所以存在start = end，且target刚好等于nums[start]/nums[end]的情况
      int mid = start + (end - start) / 2;

      if (nums[mid] == target) {
        return mid;
      }

      if (nums[start] <= nums[mid]) {
        //情况一：
        if (target >= nums[start] && target < nums[mid]) {
          end = mid - 1;
        } else {
          start = mid + 1;
        }
      } else {
        //情况二：
        if (target > nums[mid] && target <= nums[end]) {
          start = mid + 1;
        } else {
          end = mid - 1;
        }
      }
    }
    return -1;
  }

  //在排序数组中查找元素的第一个和最后一个位置
  //给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
  //如果数组中不存在目标值 target，返回 [-1, -1]。
  //方法一：遍历暴力查找，时间复杂度O(n)
  //方法二：两遍二分法，时间复杂度O(logn)
  public int[] searchRange(int[] nums, int target) {
    int[] result = new int[]{-1, -1};
    if (nums == null || nums.length == 0) {
      return result;
    }
    //第一次查找第一个大于等于target的数
    //第二次查找第一个大于target的数，然后减1
    //用一个boolean来区别是查找的第一个位置，还是第二个位置
    int left = searchRangeSub(nums, target, true);
    int right = searchRangeSub(nums, target, false) - 1;

    //因为target可能不存在数组中，所以需要double check一下
    if (left <= right && nums[left] == target && nums[right] == target) {
      result[0] = left;
      result[1] = right;
    }
    return result;
  }

  public int searchRangeSub(int[] nums, int target, boolean first) {
    int start = 0;
    int end = nums.length - 1;
    int result = nums.length;
    while (start <= end) {
      int mid = start + (end - start) / 2;
      //因为是要找到大于等于target的数
      if ((first && nums[mid] >= target) || nums[mid] > target) {
        end = mid - 1;
        result = mid;
      } else {
        start = mid + 1;
      }
    }
    return result;
  }

  //组合总和
  //给定一个无重复元素的正整数数组 candidates 和一个正整数 target ，找出 candidates 中所有可以使数字和为目标数 target 的唯一组合。
  //candidates 中的数字可以无限制重复被选取。如果至少一个所选数字数量不同，则两种组合是唯一的。 
  //对于给定的输入，保证和为 target 的唯一组合数少于 150 个。
  //回朔，时间复杂度O(n * n!)
  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    if (candidates == null || candidates.length == 0) {
      return result;
    }
    combinationSumSub(candidates, target, result, new ArrayList<>(), 0, 0);
    return result;
  }

  public void combinationSumSub(int[] candidates, int target, List<List<Integer>> result,
      List<Integer> list, int start, int sum) {
    if (sum > target) {
      return;
    }
    if (sum == target) {
      //这里需要new ArrayList<>(list)的原因：
      //因为list是一个引用，回朔修改的时候，会更改堆中(内存中的值)，而result里面add的是同一个引用
      //所以会出现：如果list最后一次修改的结果是[1,1,1]，往result里面添加了3次list，得到的结果就是result里面有3个[1,1,1]
      //所以这里需要new ArrayList<>(list)，这样就把当前list对象复制了一份，并创建一个新的索引指向新的对象
      result.add(new ArrayList<>(list));
//      result.add(list);
      return;
    }

    for (int i = start; i < candidates.length; i++) {
      sum += candidates[i];
      if (sum > target) {
        break;
      }
      list.add(candidates[i]);
      combinationSumSub(candidates, target, result, list, i, sum);
      sum -= candidates[i];
      list.remove(list.size() - 1);
    }
  }

  //接雨水
  //给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
  //方法一：暴力解法，算出每个位置起能接多少雨水，时间复杂度O(n^2)，空间复杂度O(1)
  //方法二：使用辅助数组，预处理，时间复杂度O(n)，空间复杂度O(n)
  //方法三：双指针，时间复杂度O(n)，空间复杂度O(1)
  public int trap(int[] height) {
    return trapViolent(height);
//    return trapTwoPointer(height);
//    return trapSlidingWindow(height);
  }

  //对于每一个位置来说，计算左边最大值和右边最大值，两个比较，然后减去当前位置的值，得到当前位置的接水量
  public int trapViolent(int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }

    int result = 0;
    for (int i = 1; i < array.length - 1; i++) {
      int leftMax = 0;
      int rightMax = 0;
      for (int l = 0; l < i; l++) {
        leftMax = Math.max(leftMax, array[l]);
      }
      for (int r = array.length - 1; r > i; r--) {
        rightMax = Math.max(rightMax, array[r]);
      }
      result += Math.max(0, Math.min(leftMax, rightMax) - array[i]);
    }
    return result;
  }

  public int trapSlidingWindow(int[] height) {
    if (height == null || height.length < 3) {
      return 0;
    }

    int result = 0;
    //这里两个辅助数组的长度是height数组长度 -2
    //因为对于leftMax来说，在height的height.length - 2，和height.length - 1位置上到数没有意义
    //因为对于rightMax来说，在height的0和1位置上到数没有意义
    int[] leftMax = new int[height.length - 2];
    int[] rightMax = new int[height.length - 2];
    leftMax[0] = height[0];
    rightMax[height.length - 3] = height[height.length - 1];

    for (int i = 1; i < height.length - 2; i++) {
      leftMax[i] = Math.max(leftMax[i - 1], height[i]);
    }

    for (int i = height.length - 4; i >= 0; i--) {
      rightMax[i] = Math.max(rightMax[i + 1], height[i + 2]);
    }

    for (int i = 1; i < height.length - 1; i++) {
      result += Math.max(0, Math.min(rightMax[i - 1], leftMax[i - 1]) - height[i]);
    }
    return result;
  }


  public int trapTwoPointer(int[] height) {
    if (height == null || height.length < 3) {
      return 0;
    }

    int leftIndex = 1;
    int rightIndex = height.length - 2;
    int leftMax = height[0];
    int rightMax = height[height.length - 1];
    int result = 0;

    while (leftIndex <= rightIndex) {
      //这里需要等于
      //因为我们算的是leftIndex/rightIndex位置上，能接到的水，所以在等于的时候算的是最后一个
      if (leftMax < rightMax) {
        //计算左面的雨水量
        result += Math.max(0, leftMax - height[leftIndex]);
        leftMax = Math.max(leftMax, height[leftIndex]);
        leftIndex++;
      } else {
        //计算右面的雨水量
        result += Math.max(0, rightMax - height[rightIndex]);
        rightMax = Math.max(rightMax, height[rightIndex]);
        rightIndex--;
      }
    }
    return result;
  }

  //全排列
  //给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
  //回朔，时间复杂度：O(n×n!)
  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums == null || nums.length == 0) {
      return result;
    }
    permuteSub(nums, result, new ArrayList<>(), new boolean[nums.length]);
    return result;
  }

  public void permuteSub(int[] nums, List<List<Integer>> result, List<Integer> list,
      boolean[] used) {
    if (list.size() == nums.length) {
      result.add(new ArrayList<>(list));
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      if (used[i]) {
        continue;
      }
      list.add(nums[i]);
      used[i] = true;
      permuteSub(nums, result, list, used);
      list.remove(list.size() - 1);
      used[i] = false;
    }
  }

  //旋转图像
  //给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
  //你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
  //方法：两次反转，先上下颠倒，再主对角线颠倒
  public void rotate(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
      return;
    }
    int n = matrix.length;

    //上下颠倒
    for (int i = 0; i < n / 2; i++) {
      for (int j = 0; j < n; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - i - 1][j];
        matrix[n - i - 1][j] = temp;
      }
    }

    //主对角线颠倒
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }
  }

  //字母异位词分组
  //给定一个字符串数组，将字母异位词组合在一起。可以按任意顺序返回结果列表。
  //字母异位词指字母相同，但排列不同的字符串。
  //遍历，排序，使用map，时间复杂度O(nklogk)，n表示n个字符，k表示字符最大的长度，因为遍历所以是n，每一个字符的排序是O(klogk)
  //空间复杂度O(nk)
  public List<List<String>> groupAnagrams(String[] strs) {
    if (strs == null || strs.length == 0) {
      return new ArrayList<>();
    }

    //将每一个字符都排序，因为每一个字符的字母都是相同的，所以排序后都是相等的，取得的hash值也是相等的，放入Map
    Map<String, List<String>> map = new HashMap<>();
    for (String str : strs) {
      char[] chars = str.toCharArray();
      Arrays.sort(chars);
      String key = new String(chars);
      List<String> list = map.getOrDefault(key, new ArrayList<>());
      list.add(str);
      map.put(key, list);
    }
    return new ArrayList<List<String>>(map.values());
  }

  //最大子序和
  //给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
  //方法：遍历，对于位置i来说，如果i前面的位置加和小于0，那么i前面的位置都可以不要，因为对于任意一个数，只有加上比0大的数才会更大
  //时间复杂度O(n)
  public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int current = 0;
    int result = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
      current += nums[i];
      result = Math.max(result, current);
      current = current > 0 ? current : 0;
    }
    return result;
  }

  //给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
  //数组中的每个元素代表你在该位置可以跳跃的最大长度。
  //判断你是否能够到达最后一个下标。
  //贪心算法，时间复杂度O(n)，空间复杂度O(1)
  public boolean canJump(int[] nums) {
    if (nums == null || nums.length == 0) {
      return false;
    }

    //从头遍历，维护一个能达到的最远距离
    // 计算在目前最远距离的范围内的位置，能达到的最远距离，不停迭代最远距离，判断能不能到最后
    //如果遍历时，某个位置不在不在最远距离里，就是不能达到最后
    int remoteIndex = 0;

    for (int i = 0; i < nums.length; i++) {
      if (i <= remoteIndex) {
        remoteIndex = Math.max(remoteIndex, i + nums[i]);
        if (remoteIndex >= nums.length - 1) {
          return true;
        }
      }
    }
    return false;
  }


  //合并区间
  //以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
  // 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
  //先排序，然后遍历比较右侧的位置，时间复杂度O(nlogn)，空间复杂度O(logn)
  public int[][] merge(int[][] intervals) {
    if (intervals == null || intervals.length == 0 || intervals[0] == null
        || intervals[0].length == 0) {
      return new int[0][0];
    }

    List<int[]> result = new ArrayList<>();
    //按照区间的起始位置从小到大排序
    Arrays.sort(intervals, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return o1[0] - o2[0];
      }
    });

    for (int i = 0; i < intervals.length; i++) {
      if (result.size() == 0) {
        result.add(intervals[i]);
      }
      int left = intervals[i][0];
      int right = intervals[i][1];
      if (result.get(result.size() - 1)[1] < left) {
        //如果当前数组的左边界，不在已知区间内
        result.add(intervals[i]);
      } else {
        //更新已知区间的右边界
        result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], right);
      }
    }
    return result.toArray(new int[result.size()][]);
  }


  //不同路径
  //一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
  //机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
  //问总共有多少条不同的路径？
  //动态规划，时间复杂度O(n^2)，空间复杂度O(n^2)
  public int uniquePaths(int m, int n) {
    if (m == 0 && n == 0) {
      return 0;
    }
    if (m == 0 || n == 0) {
      return 1;
    }

    int[][] dp = new int[m][n];
    //初始化状态：最左侧一列只有一条路径，最上面一行只有一条路径
    for (int i = 0; i < m; i++) {
      dp[i][0] = 1;
    }
    for (int i = 0; i < n; i++) {
      dp[0][i] = 1;
    }

    //动态方程dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
      }
    }

    //最终结果就是dp数组中最右下角的值
    return dp[m - 1][n - 1];
  }

  //最小路径和
  //给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
  //说明：每次只能向下或者向右移动一步。
  //动态规划：时间复杂度O(n^2)，空间复杂度O(n^2)
  public int minPathSum(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
      return 0;
    }
    int m = grid.length;
    int n = grid[0].length;

    int[][] dp = new int[m][n];
    //初始化状态：最左侧一列和最上面一行的值是当前位置的值加上上一个位置的值
    dp[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) {
      dp[i][0] = dp[i - 1][0] + grid[i][0];
    }
    for (int i = 1; i < n; i++) {
      dp[0][i] = dp[0][i - 1] + grid[0][i];
    }

    //动态方程dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
      }
    }

    //最终结果就是dp数组中最右下角的值
    return dp[m - 1][n - 1];
  }

  //爬楼梯
  //假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
  //每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
  //注意：给定 n 是一个正整数。
  //动态规划，时间复杂度(n)，空间复杂度O(n)
  public int climbStairs(int n) {
    if (n == 0 || n == 1) {
      return n;
    }
    int[] dp = new int[n];
    dp[0] = 1;
    dp[1] = 2;
    //dp[i] = dp[i - 1] + dp[i  -2]
    for (int i = 2; i < n; i++) {
      dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n - 1];
  }


  //编辑距离
  //给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
  //你可以对一个单词进行如下三种操作：
  //插入一个字符
  //删除一个字符
  //替换一个字符
  //动态规划，时间复杂度O(m * n)，空间复杂度O(m * n)
  public int minDistance(String word1, String word2) {
    if ((word1 == null || word1.equals("")) && (word2 == null || word2.equals(""))) {
      return 0;
    }
    if (word1 == null || word1.equals("")) {
      return word2.length();
    }
    if (word2 == null || word2.equals("")) {
      return word1.length();
    }

    int m = word1.length();
    int n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    //初始化：最左侧一列是字符word1，每一个位置需要修改的步骤都是当前位置的坐标值，
    //最上面一行是字符word2，每一个位置需要修改的步骤都是当前位置的坐标值
    for (int i = 0; i < m + 1; i++) {
      dp[i][0] = i;
    }
    for (int i = 0; i < n + 1; i++) {
      dp[0][i] = i;
    }

    //i位置表示word1的的第i个位置，j位置表示word2的的第j个位置
    //dp[i][j]表示，word1的前i个位置，和word2的前j个位置相等时需要进行多少步操作
    //分为三种情况：
    //一、word1的前i - 1个位置已经和word2的前j个位置相等相等了，那么要想word1的前i个位置已经和word2的前j个位置相等相等，
    // 只需要把word1的第i个位置上的字符删除就好，这种情况就是dp[i - 1][j] + 1
    //二、word1的前i个位置已经和word2的前j - 1个位置相等相等了，那么要想word1的前i个位置已经和word2的前j个位置相等相等，
    // 只需要把word2的第j个位置上的字符删除就好，这种情况就是dp[i][j - 1] + 1
    //三、word1的前i - 1个位置已经和word2的前j - 1个位置相等相等了，那么要想word1的前i个位置已经和word2的前j个位置相等相等，
    // 只需要让word1的第i个位置上的字符和word2的第j个位置上的字符相等就好，
    //这里又分为两种情况，
    //3.1 就是word1的第i个位置上的字符和word2的第j个位置上的字符已经相等，这种就是dp[i - 1][j - 1]
    //3.2 就是word1的第i个位置上的字符和word2的第j个位置上的字符不相等，这种就是dp[i - 1][j - 1] + 1
    //dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1
    //dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1] - 1) + 1
    for (int i = 1; i < m + 1; i++) {
      for (int j = 1; j < n + 1; j++) {
        int left = dp[i - 1][j] + 1;
        int right = dp[i][j - 1] + 1;
        int left_down = dp[i - 1][j - 1];
        if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
          left_down += 1;
        }
        dp[i][j] = Math.min(left, Math.min(right, left_down));
      }
    }

    return dp[m][n];
  }

  //颜色分类
  //给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
  //此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
  //
  public void sortColors(int[] nums) {
    if (nums == null || nums.length == 0) {
      return;
    }
    //方法一：快排的衍生问题：国旗问题，时间复杂度O(nlogn)，空间复杂度O(1)
//    sortColorsSub(nums, 0, nums.length - 1);
    //方法二：设置判断条件，时间复杂度O(n)，空间复杂度O(1)
    // 循环终止条件是 i == two，那么循环可以继续的条件是 i < two
    // 为了保证初始化的时候 [0, zero) 为空，设置 zero = 0，
    // 所以下面遍历到 0 的时候，先交换，再加
    int zero = 0;
    // 为了保证初始化的时候 [two, len - 1] 为空，设置 two = len
    // 所以下面遍历到 2 的时候，先减，再交换
    int two = nums.length;
    int i = 0;
    // 当 i == two 上面的三个子区间正好覆盖了全部数组
    // 因此，循环可以继续的条件是 i < two
    while (i < two) {
      if (nums[i] == 0) {
        swap(nums, i, zero);
        zero++;
        i++;
      } else if (nums[i] == 1) {
        i++;
      } else {
        two--;
        swap(nums, i, two);
      }
    }
  }


  public void sortColorsSub(int[] nums, int start, int end) {
    if (start >= end) {
      return;
    }

    //随机将某一个位置的数和end位置的数交换，因为之后partition方法中的比较是以nums[end]上的值作为pivot
    //如果没有这步，会死循环，或者stackOverFlow
    swap(nums, start + (int) (Math.random() * (end - start + 1)), end);
    int[] partition = partition(nums, start, end);
    sortColorsSub(nums, start, partition[0] - 1);
    sortColorsSub(nums, partition[1] + 1, end);
  }

  //将数组分成大，中，小三份，返回值是中间区域的两个角标
  private int[] partition(int[] nums, int start, int end) {
    int less = start - 1;
    int more = end;

    //这里比较start和more。是因为startmore之后是大于的范围，start之前是小于等于的范围
    while (start < more) {
      if (nums[start] < nums[end]) {
        //0,1,2,2,1,7,9  当start在数组4的位置，less在数组1的位置，less需要先加一到2的位置，然后和4位置的数交换，这样保持2位置之前的数都是在小于的范围，包括位置2
        less++;
        swap(nums, start, less);
        start++;
      } else if (nums[start] > nums[end]) {
        //0,1,1,8,2,1,9,7  当start在数组3的位置，more在数组7的位置，more需要先减一到6的位置，然后和位置3的数交换，这样保持6位置之后的数都是在大于的范围，不包括位置7
        more--;
        swap(nums, more, start);
      } else {
        //等于的区间移动start
        start++;
      }
    }

    //此时最后一位的数是等于区间的，此时more来到了大于的边界，也就是在more位置之后的数都是大于区间的，包括more位置上的数，所以交换more位置上的数和最后一位，交换之后，more就是等于区间的右边界
    //0,1,1,1,2,9,8,2
    swap(nums, more, end);
    //此时less是小于区域的边界，所以等于区域的左边界是less + 1
    return new int[]{less + 1, more};
  }


  //最小覆盖子串
  //给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
  //滑动窗口：时间复杂度O(n)，空间复杂度O(k)
  public String minWindow(String s, String t) {
    if (s == null || s.length() == 0 || t == null || t.length() == 0) {
      return "";
    }
    int[] need = new int[128];
    //记录需要的字符的个数
    for (int i = 0; i < t.length(); i++) {
      need[t.charAt(i)]++;
    }
    //l是当前左边界，r是当前右边界，size记录窗口大小，count是需求的字符个数，start是最小覆盖串开始的index
    int l = 0;
    int r = 0;
    int size = Integer.MAX_VALUE;
    int count = t.length();
    int start = 0;
    //遍历所有字符
    while (r < s.length()) {
      char c = s.charAt(r);
      if (need[c] > 0) {//需要字符c
        count--;
      }
      need[c]--;//把右边的字符加入窗口
      if (count == 0) {//窗口中已经包含所有字符
        while (l < r && need[s.charAt(l)] < 0) {
          need[s.charAt(l)]++;//释放右边移动出窗口的字符
          l++;//指针右移
        }
        if (r - l + 1 < size) {//不能右移时候挑战最小窗口大小，更新最小窗口开始的start
          size = r - l + 1;
          start = l;//记录下最小值时候的开始位置，最后返回覆盖串时候会用到
        }
        //l向右移动后窗口肯定不能满足了 重新开始循环
        need[s.charAt(l)]++;
        l++;
        count++;
      }
      r++;
    }
    return size == Integer.MAX_VALUE ? "" : s.substring(start, start + size);
  }

  //子集
  //给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
  //解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
  //回朔，时间复杂度O(n*2^n)，空间复杂度O(n)
  public List<List<Integer>> subsets(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new ArrayList<>();
    }

    List<List<Integer>> result = new ArrayList<>();
    subsetsSub(nums, result, new ArrayList<>(), 0);
    return result;
  }

  public void subsetsSub(int[] nums, List<List<Integer>> result, List<Integer> list, int start) {
    result.add(new ArrayList<>(list));
    for (int i = start; i < nums.length; i++) {
      list.add(nums[i]);
      subsetsSub(nums, result, list, i + 1);
      list.remove(list.size() - 1);
    }
  }


  //单词搜索
  //给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
  //单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
  //回朔
  public boolean exist(char[][] board, String word) {
    if (word == null || word.equals("")) {
      return false;
    }
    if (board == null || board.length == 0) {
      return false;
    }

    //visited[i][j]表示是否访问过board的这个位置
    boolean[][] visited = new boolean[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        //找到visited[i][j]位置上的字符和word开始字符的相等位置开始，上下左右进行深度优先搜索
        if (board[i][j] == word.charAt(0) && existSub(board, word, i, j, 0, visited)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean existSub(char[][] board, String word, int i, int j, int wordIndex,
      boolean[][] visited) {
    if (wordIndex == word.length()) {
      return true;
    }
    //边界校验
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
      return false;
    }

    //访问过，不可重复；board[i][j]上的字符，和word.charAt(wordIndex)上的字符不想等
    if (visited[i][j] || board[i][j] != word.charAt(wordIndex)) {
      return false;
    }

    visited[i][j] = true;
    //上下左右进行遍历
    if (existSub(board, word, i - 1, j, wordIndex + 1, visited)
        || existSub(board, word, i + 1, j, wordIndex + 1, visited)
        || existSub(board, word, i, j - 1, wordIndex + 1, visited)
        || existSub(board, word, i, j + 1, wordIndex + 1, visited)) {
      return true;
    }

    visited[i][j] = false;
    return false;
  }

  //柱状图中最大的矩形
  //给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
  //求在该柱状图中，能够勾勒出来的矩形的最大面积。
  //单调栈，时间复杂度O(n)，空间复杂度O(n)
  public int largestRectangleArea(int[] heights) {
    if (heights == null || heights.length == 0) {
      return 0;
    }

    int[] left = new int[heights.length];
    int[] right = new int[heights.length];

    //从左到右，找到对于位于位置i上到高度，左侧第一个比他小到位置
    //栈中存的是数组角标，保持这个栈里角标对应的高度是单调递增的
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < heights.length; i++) {
      //为了维持栈中数组是单调递增的，对于高度heights[i]，在他被放入栈中之前，需要把栈中大于等于他的数pop掉
      while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
        stack.pop();
      }
      //如果一个高度的左边没有比他大的，也就是栈中是空的，用-1来表示
      //此时栈顶的元素就是i位置左边第一个比他小的位置的角标
      left[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }
    stack.clear();

    //同理，从右到左，找到对于位于位置i上到高度，右侧第一个比他小到位置
    for (int i = heights.length - 1; i >= 0; i--) {
      while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
        stack.pop();
      }
      right[i] = stack.isEmpty() ? heights.length : stack.peek();
      stack.push(i);
    }

    //优化到方法：可以同一一次遍历获取左右边界
    //原理：首先从左到右，找到对于位于位置i上到高度，左侧第一个比他小到位置，
    // 那么在把比i位置大的元素pop之前，这个元素右侧第一个比他小的元素就i
//    for (int i = 0; i< heights.length; i++) {
//      while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
//        right[stack.peek()] = i;
//        stack.pop();
//      }
//      left[i] = stack.isEmpty() ? -1 : stack.peek();
//      stack.push(i);
//    }

    //对于每一个位置，计算矩形面积，获得最大值
    int result = 0;
    for (int i = 0; i < heights.length; i++) {
      result = Math.max((right[i] - left[i] - 1) * heights[i], result);
    }
    return result;
  }


  //最大矩形
  //给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
  public int maximalRectangle(char[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return 0;
    }

    int[] heights = new int[matrix[0].length];
    int maxArea = 0;

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == '1') {
          heights[j] += 1;
        } else {
          heights[j] = 0;
        }
      }
      maxArea = Math.max(maxArea, largestRectangleArea(heights));
    }
    return maxArea;
  }

  // 二叉树的中序遍历
  //给定一个二叉树的根节点 root ，返回它的 中序 遍历。
  public List<Integer> inorderTraversal(TreeNode root) {
    //中序遍历：左根右
    List<Integer> result = new ArrayList<>();

    //递归版本，时间复杂度：O(n)，空间复杂度O(n)
//    inorderTraversalRec(root, result);
    //非递归版本，时间复杂度：O(n)，空间复杂度O(n)
//    result = inorderTraversalStack(root);
    Morris(root, result);

    return result;
  }

  public void inorderTraversalRec(TreeNode root, List<Integer> result) {
    if (root == null) {
      return;
    }
    inorderTraversalRec(root.left, result);
    result.add(root.value);
    inorderTraversalRec(root.right, result);
  }

  public List<Integer> inorderTraversalStack(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) {
      return result;
    }
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;

    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.push(curr);
        curr = curr.left;
      } else {
        curr = stack.pop();
        result.add(curr.value);
        curr = curr.right;
      }
    }
    return result;
  }

  //Morris序，时间复杂度O(n)，空间复杂度O(1)
  public void Morris(TreeNode head, List<Integer> result) {
    if (head == null) {
      return;
    }

    TreeNode curr = head;
    TreeNode mostRight = null;

    while (curr != null) {
      //不停的找该节点的左子树
      mostRight = curr.left;
      if (mostRight != null) {
        while (mostRight.right != null && mostRight.right != curr) {
          //如果不等于null就证明右节点有值
          //如果为等于curr，也就是它的父节点，就说明没走过
          mostRight = mostRight.right;
        }

        if (mostRight.right == null) {
          //走到了叶节点，让改节点的右子树指针指向父节点
          //每一个叶节点，都可以看作是某个节点的左子树的最右节点
          mostRight.right = curr;
          result.add(curr.value); //1. 加在这里就是前序遍历，根左右
          curr = curr.left;
          continue;
        } else {
          //此时，如果mostRight.right != null就说明走过了
          //也就是跳过了上面的while循环，来到了某个节点的最右节点
          mostRight.right = null;
        }
      } else {
        result.add(curr.value); //2. 加在这里就是前序遍历，根左右
      }

      //第一种情况：curr是某个节点的左子树最右节点，让curr = curr.right，也就是让curr回到它的父节点，
      // 在下一次循环的时候，通过while找到左子树的最右节点，然后通过mostRight.right = null，把左子树的最右节点的右节点（也就是指向curr节点的指针去掉）
      //让父节点去右子树了
      //第二种情况：就是没有左子树，根据if (mostRight != null) 判断走到这里
//      result.add(curr.value); //加在这里就是中序遍历
      curr = curr.right;
    }

  }


  //不同的二叉搜索树
  //给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
  //动态规划：时间复杂度： O(n^2)，空间复杂度：O(n)
  public int numTrees(int n) {
    if (n < 2) {
      return 1;
    }

    //初始化
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;

    //动态方程：dp[i] = dp[j - 1] * dp[i - j], j在[1, i]区间内求和
    //也就是假设n = 7；那么结果就是以1，2，3，4，5，6，7分别为根节点，分别有多少种情况的和 DP[7] = DP[6] + .... + DP[1]
    //同时，如果以4为根节点，就是[1, 2, 3]为左子树，[5, 6, 7]为右子树的情况的乘积 DP[4] = DP[4 - 1] * DP[ 7 - 4]
    //推导为动态方程
    for (int i = 2; i < n + 1; i++) {
      for (int j = 1; j <= i; j++) {
        dp[i] += dp[j - 1] * dp[i - j];
      }
    }
    return dp[n];
  }

  //验证二叉搜索树
  //给定一个二叉树，判断其是否是一个有效的二叉搜索树。
  //假设一个二叉搜索树具有如下特征：
  //节点的左子树只包含小于当前节点的数。
  //节点的右子树只包含大于当前节点的数。
  //所有左子树和右子树自身必须也是二叉搜索树。
  //中序遍历，时间复杂度O(n)，空间复杂度O(n)
  public boolean isValidBST(TreeNode root) {
    if (root == null) {
      return false;
    }
    //只需要判断中序遍历是不是递增的就行
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    double preVal = -Double.MAX_VALUE;

    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.push(curr);
        curr = curr.left;
      } else {
        curr = stack.pop();
        if (curr.value <= preVal) {
          return false;
        }
        preVal = curr.value;
        curr = curr.right;
      }
    }
    return true;
  }

  //对称二叉树
  //给定一个二叉树，检查它是否是镜像对称的。
  //递归，时间复杂度O(n)，空间复杂度O(n)
  public boolean isSymmetric(TreeNode root) {
    if (root == null) {
      return false;
    }
    return isSymmetricSub(root.left, root.right);
  }


  public boolean isSymmetricSub(TreeNode left, TreeNode right) {
    if (left == null || right == null) {
      return left == right;
    }
    if (left.value != right.value) {
      return false;
    }
    boolean leftRes = isSymmetricSub(left.left, right.right);
    boolean rightRes = isSymmetricSub(left.right, right.left);
    return leftRes && rightRes;
  }

  // 二叉树的层序遍历
  //给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
  //BFS，递归，时间复杂度O(n)，空间复杂度O(n)
  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) {
      return result;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> list = new ArrayList<>();
      for (int i = 0; i < levelSize; i++) {
        TreeNode tempRoot = queue.poll();
        list.add(tempRoot.value);
        if (tempRoot.left != null) {
          queue.offer(tempRoot.left);
        }

        if (tempRoot.right != null) {
          queue.offer(tempRoot.right);
        }
      }
      result.add(list);

    }
    return result;

  }

  //二叉树的最大深度
  //给定一个二叉树，找出其最大深度。
  //二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
  //说明: 叶子节点是指没有子节点的节点。
  //深度优先搜索，时间复杂度O(n)，空间复杂度O(height)
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    return Math.max(left, right) + 1;
  }

  //从前序与中序遍历序列构造二叉树
  //给定一棵树的前序遍历 preorder 与中序遍历  inorder。请构造二叉树并返回其根节点。
  //递归，时间复杂度O(n)，空间复杂度O(n)
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    //前序遍历：跟左右
    //中序遍历：左根右
    if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) {
      return null;
    }
    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inorderMap.put(inorder[i], i);
    }
    return buildTreeSub(inorderMap, preorder, inorder, 0, preorder.length - 1, 0,
        inorder.length - 1);
  }

  public TreeNode buildTreeSub(Map<Integer, Integer> inorderMap, int[] preorder, int[] inorder,
      int preorderStart, int preorderEnd, int inorderStart, int inorderEnd) {
    if (preorderStart > preorderEnd) {
      return null;
    }

    //此时前序遍历中preorderStart的位置就是某一个子树的根节点，在中序遍历中定位到这个根节点的位置
    int inorderRoot = inorderMap.get(preorder[preorderStart]);
    TreeNode root = new TreeNode(inorder[inorderRoot]);
    //得到左子树的大小
    int leftSize = inorderRoot - inorderStart;
    root.left = buildTreeSub(inorderMap, preorder, inorder, preorderStart + 1, preorderStart + leftSize, inorderStart, inorderRoot - 1);
    root.right = buildTreeSub(inorderMap, preorder, inorder, preorderStart + leftSize + 1, preorderEnd, inorderRoot + 1, inorderEnd);
    return root;
  }

  //二叉树展开为链表
  //给你二叉树的根结点 root ，请你将它展开为一个单链表：
  //展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
  //展开后的单链表应该与二叉树 先序遍历 顺序相同。
  //前序遍历，时间复杂度O(n)，空间复杂度O(n)
  public void flatten(TreeNode root) {
    if (root == null) {
      return;
    }

    //放入前序遍历中，然后重新构建
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    stack.push(curr);
    List<TreeNode> result = new ArrayList<>();

    while (!stack.isEmpty()) {
      curr = stack.pop();
      System.out.println(curr);
      result.add(curr);
      if (curr.right != null) {
        stack.push(curr.right);
      }

      if (curr.left != null) {
        stack.push(curr.left);
      }
    }

    curr = root;
    for (int i = 1; i < result.size(); i++) {
      curr.right = result.get(i);
      curr.left = null;
      curr = curr.right;
    }
  }

  // 买卖股票的最佳时机
  //给定一个数组prices ，它的第i个元素 prices[i] 表示一支给定股票第i天的价格。
  //你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
  //返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
  //通过辅助数组，时间复杂度O(n)，空间复杂度O(n)
  public int maxProfit(int[] prices) {

    if (prices == null || prices.length == 0) {
      return 0;
    }

    int[] helper = new int[prices.length - 1];
    for (int i = 1; i < prices.length; i++) {
      helper[i - 1] = prices[i] - prices[i - 1];
    }

    int result = 0;
    int curr = 0;
    for (int i = 0; i < helper.length; i++) {
      curr += helper[i];
      curr = curr >=0 ? curr : 0;
      result = Math.max(result, curr);
    }
    return result;
  }

  // 二叉树中的最大路径和
  //路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。
  // 该路径 至少包含一个 节点，且不一定经过根节点。
  //路径和 是路径中各节点值的总和。
  //给你一个二叉树的根节点 root ，返回其 最大路径和 。
  //时间复杂度O(n)，空间复杂度O(n)
  public Integer maxPathSumMaxValue = Integer.MIN_VALUE;
  public int maxPathSum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    maxPathSumSub(root);
    return maxPathSumMaxValue;
  }

  public int maxPathSumSub(TreeNode root) {
    if (root == null) {
      return 0;
    }

    //分别取得这个节点的左子树、右子树路径和
    int left = Math.max(maxPathSumSub(root.left), 0);
    int right = Math.max(maxPathSumSub(root.right), 0);

    //获取当前节点最长路径和
    int pathSum = left + right + root.value;

    //记录最大路径和
    maxPathSumMaxValue = Math.max(maxPathSumMaxValue, pathSum);
    //返回当前节点的最大路径和，包含当前节点，和左子树或者右子树中的一条
    return root.value + Math.max(left, right);
  }

}




















