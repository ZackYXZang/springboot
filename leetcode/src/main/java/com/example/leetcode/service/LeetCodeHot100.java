package com.example.leetcode.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

    for (int i = height.length - 4; i >=0; i--) {
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

  public void permuteSub(int[] nums, List<List<Integer>> result, List<Integer> list, boolean[] used) {
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

}

