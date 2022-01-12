package com.example.leetcode.service;

import com.example.leetcode.utils.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
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

    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
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
  //这里使用的二分查找的办法，两个数组分别取中位数的一半记性比较，小的一半就被过滤
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
      //考虑奇偶不同的情况，在字符中间加一个无意义的标识符
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
    int n = nums.length;
    //从小到大的排序，之后好减少时间复杂度，排序时间复杂度O(nlogn)
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();

    for (int first = 0; first < nums.length; first++) {
      //过滤相同的组合，将相同的数字跳过
      if (first > 0 && nums[first] == nums[first - 1]) {
        continue;
      }

      //因为之前对数组进行了从小到大的排序，所以第三个数的取值可以从右到左，因为first + second在变大，所以third要变小
      int third = n - 1;
      int target = -nums[first];

      //第二个数字的取值要从first之后
      for (int second = first + 1; second < n; second++) {
        //过滤相同的组合，将相同的数字跳过
        if (second > first + 1 && nums[second] == nums[second - 1]) {
          continue;
        }

        //不断减小第三个数的取值，知道符合条件
        while (second < third && nums[second] + nums[third] > target) {
          third--;
        }

        //如果第二个数的指针和第三个数的指针重合了，就不用继续了
        //这里的break是跳出了第二个数字的循环，因为随着second的增大，不可能存在着a+b+c = 0
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
    //找到变小的第一个数
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

    //此时最后一位的数是等于区间的，此时more来到了大于的边界，也就是在more位置之后(包括more)的数都是大于区间的，包括more位置上的数，所以交换more位置上的数和最后一位，交换之后，more就是等于区间的右边界
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
    //前序遍历：根左右
    //中序遍历：左根右
    //在前序遍历中找到跟节点，然后去前序遍历里分割
    if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) {
      return null;
    }
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < inorder.length; i++) {
      map.put(inorder[i], i);
    }

    return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
  }

  public TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart,
      int inEnd, Map<Integer, Integer> map) {
    if (preStart > preEnd || inStart > inEnd) {
      return null;
    }

    TreeNode root = new TreeNode(preorder[preStart]);
    int inorderRoot = map.get(preorder[preStart]);

    root.left = buildTree(preorder, preStart + 1, preStart + inorderRoot - inStart, inorder,
        inStart, inorderRoot - 1, map);
    root.right = buildTree(preorder, preStart + inorderRoot - inStart + 1, preEnd, inorder,
        inorderRoot + 1, inEnd, map);
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
      curr = curr >= 0 ? curr : 0;
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

  //最长连续序列
  //给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
  //请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
  //时间复杂度O(n)，空间复杂度O(n)
  public int longestConsecutive(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    //将所有数组放入hash中储存，这样找某个数字的时间复杂度就是O(1)
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
      set.add(nums[i]);
    }

    int longest = 0;
    for (int num : nums) {
      //如果一个数x，他的最长长度是y，那么，从x，到x+y一定都存在hash中
      //相反，如果一个数是一个区间的开始值，比如是x，那么x - 1一定不在hash中
      //这样就可以跳过很多个判断，节省时间复杂度
      if (!set.contains(num - 1)) {
        int currentNum = num;
        int currentLength = 1;

        while (set.contains(currentNum + 1)) {
          currentNum++;
          currentLength++;
        }
        longest = Math.max(longest, currentLength);
      }
    }
    return longest;
  }

  //只出现一次的数字
  //给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
  public int singleNumber(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    //方法一：利用hash，时间复杂度O(n)，空间复杂度O(n)
//    Map<Integer, Integer> map = new HashMap<>();
//    for (int num : nums) {
//      map.put(num, map.getOrDefault(num, 0) + 1);
//    }
//
//    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//      if (entry.getValue() == 1) {
//        return entry.getKey();
//      }
//    }
//    return 0;

    //方法二：位运算，使用异或（二进制位同值则取0，异值则取1），时间复杂度O(n)，空间复杂度O(1)
    //异或的特点：
    //1. 任何数和0异或，都等于本身
    //2. 任何数和本身异或，都等于0
    //3. 满足交换律和结合律
    int single = 0;
    for (int num : nums) {
      single ^= num;
    }
    return single;

  }

  //单词拆分
  //给定一个非空字符串s和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
  //动态规划，时间复杂度O(n^2)，空间复杂度O(n)
  public boolean wordBreak(String s, List<String> wordDict) {
    if (s == null || s.equals("")) {
      return true;
    }

    Set<String> wordDictSet = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;

    //dp[i] = dp[j] && check(s.subString(j, i))
    //dp[j]表示字符串s中(0, j - 1)位置上的字符分割成单词后，是否都被wordDict包含
    //同理，dp[i]表示字符串s中(0, i - 1)位置上的字符分割成单词后，是否都被wordDict包含
    for (int i = 1; i <= s.length(); i++) {
      for (int j = 0; j < i; j++) {
        if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
          dp[i] = true;
          //跳到最外层循环
          break;
        }
      }
    }
    return dp[s.length()];
  }


  //环形链表
  //给定一个链表，判断链表中是否有环。
  //如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
  //为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
  //注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
  //如果链表中存在环，则返回 true 。 否则，返回 false 。
  //双指针，时间复杂度O(n)，空间复杂度O(1)
  public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) {
      return false;
    }

    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != slow) {
      if (fast == null || fast.next == null) {
        return false;
      }
      fast = fast.next.next;
      slow = slow.next;
    }

    return true;
  }

  //环形链表 II
  //给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
  //为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
  //如果 pos 是 -1，则在该链表中没有环。注意，pos 仅仅是用于标识环的情况，并不会作为参数传递到函数中。
  //双指针，时间复杂度O(n)，空间复杂度O(1)
  public ListNode detectCycle(ListNode head) {
    if (head == null || head.next == null) {
      return null;
    }

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

  //LRU 缓存机制，less frequency use最近最少使用
  //运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制 。
  //实现 LRUCache 类：
  //LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
  //int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
  //void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。

  /**
   * Your LRUCache object will be instantiated and called as such: LRUCache obj = new
   * LRUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
   */
  class LRUCache {

    //需要自己构建一个双向链表和hash表
    //时间复杂度：对于put和get都是O(1)。
    //空间复杂度：O(capacity)
    class FakeDoubleLinkedListNode {

      int key;
      int value;
      FakeDoubleLinkedListNode prev;
      FakeDoubleLinkedListNode next;

      public FakeDoubleLinkedListNode() {
      }

      public FakeDoubleLinkedListNode(int key, int value) {
        this.key = key;
        this.value = value;
      }
    }

    private Map<Integer, FakeDoubleLinkedListNode> map = new HashMap<>();
    //size 表示当前链表中有多少元素
    private int size;
    private int capacity;
    private FakeDoubleLinkedListNode head;
    private FakeDoubleLinkedListNode tail;

    public LRUCache(int capacity) {
      //初始化一个自己构建的双向链表
      this.size = 0;
      this.capacity = capacity;
      head = new FakeDoubleLinkedListNode();
      tail = new FakeDoubleLinkedListNode();
      head.next = tail;
      tail.prev = head;
    }

    public int get(int key) {
      FakeDoubleLinkedListNode node = map.get(key);
      if (node == null) {
        return -1;
      }
      moveToHead(node);
      return node.value;
    }

    public void put(int key, int value) {
      FakeDoubleLinkedListNode node = map.get(key);
      if (node == null) {
        //在链表中不存在，需要判断链表的大小是否超过capacity
        //不超过，就加入，size+1，超过了，需要剔除尾部的节点，在hash表中删除这个尾部的节点，并在链表和hash表中加入这个节点
        FakeDoubleLinkedListNode newNode = new FakeDoubleLinkedListNode(key, value);
        map.put(key, newNode);
        addToHead(newNode);
        size++;
        if (size > capacity) {
          FakeDoubleLinkedListNode tail = removeTail();
          map.remove(tail.key);
          size--;
        }
        return;
      }

      //在链表中存在，覆盖值，并将这个node放到头部
      node.value = value;
      moveToHead(node);
    }

    private FakeDoubleLinkedListNode removeTail() {
      FakeDoubleLinkedListNode prev = tail.prev;
      deleteNode(prev);
      return prev;
    }

    public void moveToHead(FakeDoubleLinkedListNode node) {
      //分为两步，在链表中把这个node删除
      //把这个node添加到头部
      deleteNode(node);
      addToHead(node);
    }

    public void deleteNode(FakeDoubleLinkedListNode node) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
    }

    public void addToHead(FakeDoubleLinkedListNode node) {
      node.prev = head;
      node.next = head.next;
      head.next.prev = node;
      head.next = node;
    }
  }

  //排序链表
  //给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
  //二分+归并，时间复杂度O(n * logn)，空间复杂度O(logn)，这里空间复杂度主要取决于递归调用的栈空间
  public ListNode sortList(ListNode head) {
    //看到时间复杂度有logn，首先想到二分法
    //然后再归并排序，也就是合并两个链表
    return sortListSub(head);
  }

  public ListNode sortListSub(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }

    //找到链表的中点
    ListNode mid = findMiddle(head);
    //排序右边的链表
    ListNode right = sortListSub(mid.next);
    mid.next = null;
    //排序左边的链表
    ListNode left = sortListSub(head);
    return merge(left, right);
  }

  //合并两个链表，升序
  private ListNode merge(ListNode left, ListNode right) {
    ListNode dummy = new ListNode(0);
    ListNode last = dummy;

    while (left != null && right != null) {
      if (left.val < right.val) {
        last.next = left;
        left = left.next;
      } else {
        last.next = right;
        right = right.next;
      }
      last = last.next;
    }

    if (left != null) {
      last.next = left;
    }

    if (right != null) {
      last.next = right;
    }
    return dummy.next;
  }

  //找到当前链表的中点
  public ListNode findMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head.next;

    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  //乘积最大子数组
  //给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
  //动态规划，时间复杂度O(n)，空间复杂度O(n)
  public int maxProduct(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    //方法一：时间复杂度O(n)，空间复杂度O(n)
    //方法一：时间复杂度O(n)，空间复杂度O(1)
    return maxProduct1(nums);
//    return maxProduct2(nums);
  }

  //方法一：时间复杂度O(n)，空间复杂度O(n)
  public int maxProduct1(int[] nums) {
    int[] max = new int[nums.length];
    int[] min = new int[nums.length];
    max[0] = nums[0];
    min[0] = nums[0];
    int result = nums[0];

    for (int i = 1; i < nums.length; i++) {
      max[i] = nums[i];
      min[i] = nums[i];
      if (nums[i] >= 0) {
        max[i] = Math.max(nums[i], max[i - 1] * nums[i]);
        min[i] = Math.min(nums[i], min[i - 1] * nums[i]);
      } else {
        max[i] = Math.max(nums[i], min[i - 1] * nums[i]);
        min[i] = Math.min(nums[i], max[i - 1] * nums[i]);
      }
      result = Math.max(result, max[i]);
    }
    return result;
  }

  //方法一：时间复杂度O(n)，空间复杂度O(1)
  public int maxProduct2(int[] nums) {
    //方法二：是方法一的优化，因为位置i上的最大值，之后位置i - 1上有关，所以用一个值来记录i-1位置上的最大值，最小值
    int max = nums[0];
    int min = nums[0];
    int result = nums[0];

    for (int i = 1; i < nums.length; i++) {
      int maxPre = max;
      int minPre = min;
      //Math.max(maxPre * nums[i], minPre * nums[i])，Math.min(minPre * nums[i], maxPre * nums[i])：
      //表示判断一下在不确定nums[i]是整数还是负数的情况下，看一下那个值大/小
      //然后和外面的nums[i]，也就是当前的数进行比较，更新出当前位置上的最大值，最小值
      max = Math.max(nums[i], Math.max(maxPre * nums[i], minPre * nums[i]));
      min = Math.min(nums[i], Math.min(minPre * nums[i], maxPre * nums[i]));
      result = Math.max(result, max);
    }
    return result;
  }

  //最小栈
  //设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
  class MinStack {

    /**
     * initialize your data structure here.
     */

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MinStack() {
      this.stackData = new Stack<>();
      this.stackMin = new Stack<>();
    }

    public void push(int val) {
      if (this.stackMin.isEmpty()) {
        stackMin.push(val);
      } else {
        if (getMin() >= val) {
          stackMin.push(val);
        }
      }
      stackData.push(val);
    }

    public void pop() {
      if (stackData.isEmpty()) {
        return;
      }
      Integer pop = stackData.pop();
      if (pop <= getMin()) {
        stackMin.pop();
      }
    }

    public int top() {
      Integer peek = stackData.peek();
      return peek;
    }

    public int getMin() {
      return stackMin.peek();
    }
  }

  /**
   * Your MinStack object will be instantiated and called as such: MinStack obj = new MinStack();
   * obj.push(val); obj.pop(); int param_3 = obj.top(); int param_4 = obj.getMin();
   */

  //相交链表
  //给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
  //双指针，时间复杂度O(m + n)，空间复杂度O(1)
  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }

    //假设headA和headB重合的部分长度c；不重合的地方。headA长a，headB长b
    //那么headA的长度就是a+c，headB的长度就是b+c
    //如果有相交的地方，指针pA走过的长度就是a+c+b，指针pB走过的长度就是b+c+a
    //如果两个没有交点，就是pA=pB=null

    ListNode pA = headA;
    ListNode pB = headB;
    while (pA != pB) {
      pA = pA == null ? headB : pA.next;
      pB = pB == null ? headA : pB.next;
    }

    return pA;
  }

  //多数元素
  //给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
  //你可以假设数组是非空的，并且给定的数组总是存在多数元素。
  //摩尔计数法，时间复杂度O(n)，空间复杂度O(1)
  public int majorityElement(int[] nums) {
    //摩尔计数法：
    //假设A是这个大于一半的数，我们碰到他，就让count+1，不是他，就让count-1；
    //如果count=0了，就证明前面的A出现的次数，被不是A出现的次数抵消了，这样我们重新取当前的数B，在重复上一步
    //直到最后
    int count = 0;
    Integer curr = null;
    for (int num : nums) {
      if (count == 0) {
        curr = num;
      }
      count += (num == curr) ? 1 : -1;
    }

    //如果是[1,2,3]的情况，在leetcode中没有这种例子
    //这里自己加一个校验
    count = 0;
    for (int num : nums) {
      if (num == curr) {
        count++;
      }
    }
    curr = count > nums.length / 2 ? curr : null;

    return curr;
  }

  //求众数2
  //给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。
  //摩尔计数法，时间复杂度O(n)，空间复杂度O(1)
  public List<Integer> majorityElement2(int[] nums) {
    List<Integer> result = new ArrayList<>();
    if (nums == null || nums.length == 0) {
      return new ArrayList<>();
    }

    int countA = 0;
    int countB = 0;
    int currA = nums[0];
    int currB = nums[0];

    for (int num : nums) {
      if (currA == num) {
        countA++;
        continue;
      }

      if (currB == num) {
        countB++;
        continue;
      }

      if (countA == 0) {
        currA = num;
        countA++;
        continue;
      }

      if (countB == 0) {
        currB = num;
        countB++;
        continue;
      }

      countA--;
      countB--;
    }

    //double check
    countA = 0;
    countB = 0;
    for (int num : nums) {
      if (currA == num) {
        countA++;
      } else if (currB == num) {
        countB++;
      }
    }

    if (countA > nums.length / 3) {
      result.add(currA);
    }
    if (countB > nums.length / 3) {
      result.add(currB);
    }
    return result;

  }

  //打家劫舍
  //你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
  //给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置的情况下，一夜之内能够偷窃到的最高金额。
  //动态规划，时间复杂度O(n)，空间复杂度O(n)
  public int rob(int[] nums) {
    //dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
    if (nums == null || nums.length == 0) {
      return 0;
    }
    if (nums.length == 1) {
      return nums[0];
    }

    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);

    for (int i = 2; i < dp.length; i++) {
      dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
    }

    return dp[nums.length - 1];
  }

  //岛屿数量
  //给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
  //岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
  //此外，你可以假设该网格的四条边均被水包围。
  //dfs，时间复杂度O(mn)，空间复杂度O(mn)
  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int count = 0;
    int rowLength = grid.length;
    int columnLength = grid[0].length;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == '1') {
          count++;
          numIslandsDFSSub(grid, i, j, rowLength, columnLength);
        }
      }
    }
    return count;
  }

  public void numIslandsDFSSub(char[][] grid, int row, int column, int rowLength,
      int columnLength) {
    if (row < 0 || column < 0 || row >= rowLength || column >= columnLength
        || grid[row][column] == '0') {
      return;
    }

    grid[row][column] = '0';
    numIslandsDFSSub(grid, row - 1, column, rowLength, columnLength);
    numIslandsDFSSub(grid, row + 1, column, rowLength, columnLength);
    numIslandsDFSSub(grid, row, column - 1, rowLength, columnLength);
    numIslandsDFSSub(grid, row, column + 1, rowLength, columnLength);

  }

  //反转链表
  //给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
  //时间复杂度O(n)，空间复杂度O(n)
  public ListNode reverseList(ListNode head) {
    if (head == null) {
      return null;
    }

    ListNode prev = null;
    while (head != null) {
      ListNode temp = head.next;
      head.next = prev;
      prev = head;
      head = temp;
    }

    //最后的时候head就等于null，而prev在head的前一个，所以返回prev
    return prev;
  }

  //课程表
  //你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
  //在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
  //例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
  //请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
  //使用拓扑排序，时间复杂度O(n)，空间复杂度O(n)
  public boolean canFinish(int numCourses, int[][] prerequisites) {
    if (numCourses == 0) {
      return true;
    }
    if (prerequisites == null || prerequisites.length == 0) {
      return true;
    }
    //记录以每一个位置为课程，需要多少节前置课
    int[] inDegrees = new int[numCourses];
    // 记录每一个位置可以作为哪些课的前置课
    List<List<Integer>> adjacency = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      adjacency.add(new ArrayList<>());
    }
    for (int[] p : prerequisites) {
      inDegrees[p[0]]++;
      adjacency.get(p[1]).add(p[0]);
    }

    //记录哪些课不需要前置课
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (inDegrees[i] == 0) {
        queue.add(i);
      }
    }

    //实际能上多少节课
    int credit = 0;
    while (!queue.isEmpty()) {
      Integer preCourse = queue.poll();
      for (int course : adjacency.get(preCourse)) {
        inDegrees[course]--;
        if (inDegrees[course] == 0) {
          queue.add(course);
        }
      }
      credit++;
    }
    return numCourses == credit;
  }


  //实现 Trie (前缀树)
  //Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
  //请你实现 Trie 类：
  //Trie() 初始化前缀树对象。
  //void insert(String word) 向前缀树中插入字符串 word 。
  //boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
  //boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
  class Trie {

    private boolean isEnd;
    private Trie[] nexts;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
      isEnd = false;
      nexts = new Trie[26];
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
      if (word == null || word.equals("")) {
        return;
      }

      char[] wordChar = word.toCharArray();
      Trie node = this;
      for (int i = 0; i < wordChar.length; i++) {
        int index = wordChar[i] - '0';
        if (node.nexts[index] == null) {
          node.nexts[index] = new Trie();
        }
        node = node.nexts[index];
      }
      node.isEnd = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
      Trie node = searchPrefix(word);
      return node != null && node.isEnd;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
      return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String prefix) {
      Trie node = this;
      char[] wordChar = prefix.toCharArray();
      for (int i = 0; i < wordChar.length; i++) {
        int index = wordChar[i] - '0';
        if (node.nexts[index] == null) {
          return null;
        }
        node = node.nexts[index];
      }
      return node;
    }
  }

  /**
   * Your Trie object will be instantiated and called as such: Trie obj = new Trie();
   * obj.insert(word); boolean param_2 = obj.search(word); boolean param_3 =
   * obj.startsWith(prefix);
   */

  //数组中的第K个最大元素
  //给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
  //请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
  public int findKthLargest(int[] nums, int k) {
    if (nums == null || nums.length < k) {
      return 0;
    }
    return findKthLargestSub(nums, 0, nums.length - 1, nums.length - k);
  }

  public int findKthLargestSub(int[] nums, int start, int end, int k) {
    int partition = partition1(nums, start, end);
    if (partition == k) {
      return nums[partition];
    } else {
      return partition > k ? findKthLargestSub(nums, start, partition - 1, k)
          : findKthLargestSub(nums, partition + 1, end, k);
    }
  }

  public int partition1(int[] arr, int l, int r) {
    //此时arr[r]就是用来做比较的数（目标数）
    int less = l - 1;
    int more = r;
    while (l < more) {
      if (arr[l] > arr[r]) {
        //如果l位置的数，比目标数要大，
        //那么让more的位置向前移动一位，并且交换l位置和more位置的数，因为more之后（包括more位置）表示比目标数大的数
        swap(arr, l, --more);
      } else if (arr[l] < arr[r]) {
        //如果l位置的数，比目标数要小，
        //那么先让less向右移动一位，然后交换l位置的数，和右移后的less位的数
        //因为less位置表示小于目标数（包括less），所以交换的是less下一位的数，这个位置是等于的位置的数
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
    return more;
  }

  //最大正方形
  //在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
  //动态规划，时间复杂度O(mn)，空间复杂度O(mn)
  public int maximalSquare(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return 0;
    }
    int maxSize = 0;
    int row = matrix.length;
    int column = matrix[0].length;
    //dp每一个位置，表示以matrix的这个位置为右下角，最大的正方形的变长是多少
    int[][] dp = new int[row][column];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        if (matrix[i][j] == '1') {
          if (i == 0 || j == 0) {
            dp[i][j] = 1;
          } else {
            //动态方程
            dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
          }
          maxSize = Math.max(maxSize, dp[i][j]);
        }
      }
    }
    return maxSize * maxSize;
  }

  //翻转二叉树
  //翻转一棵二叉树。
  //递归，递归到最底下，然后替换左右
  //时间复杂度O(n)，空间复杂度O(n)
  public TreeNode invertTree(TreeNode root) {
    if (root == null) {
      return null;
    }

    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);

    root.right = left;
    root.left = right;
    return root;

  }

  //回文链表
  //给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
  //借助一个栈，借助先进后出的特性，时间复杂度O(n)，空间复杂度O(n)
  public boolean isPalindrome(ListNode head) {
//    if (head == null) {
//      return false;
//    }
//
//    Stack<ListNode> stack = new Stack<>();
//    ListNode curr = head;
//
//    while (curr != null) {
//      stack.push(curr);
//      curr = curr.next;
//    }
//
//    while (!stack.isEmpty()) {
//      curr = stack.pop();
//      if (curr.val != head.val) {
//        return false;
//      }
//      head = head.next;
//    }
//    return true;
    //解法2，时间复杂度为O(N), 空间复杂度为O(1)
    if (head == null || head.next == null) {
      return false;
    }

    //慢指针
    ListNode node1 = head;
    //快指针
    ListNode node2 = head;
    while (node2.next != null && node2.next.next != null) {
      node1 = node1.next;
      node2 = node2.next.next;
    }

    //此时快指针改为指向中点，也就是node2的位置是中点的位置
    //奇数的时候，来到中间，偶数的时候，来到中点的前一个位置
    node2 = node1.next;

    //让链表中点前面的节点指向null，然后开始翻转后半部分链表
    node1.next = null;//node1 = pre
    ListNode node3 = null;//node3 = next

    while (node2 != null) {
      node3 = node2.next;
      node2.next = node1;
      node1 = node2;
      node2 = node3;
    }

    node3 = node1; //node3就是最后一个节点
    node2 = head;

    //check palindrome
    boolean result = true;
    while (node2 != null && node1 != null) {
      if (node1.val != node2.val) {
        result = false;
        break;
      }
      node1 = node1.next;
      node2 = node2.next;
    }

    //恢复链表后半部分
    node1 = null;
    while (node3 != null) {
      node2 = node3.next;
      node3.next = node1;
      node1 = node3;
      node3 = node2;
    }

    return result;
  }

  //二叉树的最近公共祖先
  //给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
  //时间复杂度O(n)，空间复杂度O(n)
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    //若想找到公共祖先，就是能是下面3种情况的一种：
    //1。p和q在root的子树中，且分别在root的两侧
    //2。p是root，q在root的左或者右子树中
    //3。q是root，p在root的左或者右子树中

    //走到叶节点了，获取当前节点就是p/或q，
    if (root == null || p == root || q == root) {
      return root;
    }
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    //自下而上，所以一定会找到深度最大的
    //root的左子树和右子树都不包含p，q
    if (left == null && right == null) {
      return null;
    }
    //root的左子树不包含p，q，可能是p，q在右子树，也有可能是p或者q其中之一在右子树
    if (left == null) {
      return right;
    }
    //root的右子树不包含p，q，可能是p，q在左子树，也有可能是p或者q其中之一在左子树
    if (right == null) {
      return left;
    }
    return root;
  }

  //除自身以外数组的乘积
  //给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
  //时间复杂度O(n)，空间复杂度O(1)
  public int[] productExceptSelf(int[] nums) {
    if (nums == null || nums.length == 0) {
      return nums;
    }

    int[] result = new int[nums.length];
    result[0] = 1;

    //现正序算一遍，算出每个位置之前的和
    //比如[5, 8,   9,     10,           12       ]
    //得到[1, 5, 5 * 8, 5 * 8 * 9, 5 * 8 * 9 * 10]
    for (int i = 1; i < nums.length; i++) {
      result[i] = result[i - 1] * nums[i - 1];
    }

    //然后再反过来计算，在上面的基础上，让每个数乘以后面的数，这样就跳过自身了
    //比如[5,                           8,              9,               10,           12       ]
    //得到[1 * 8 * 9 * 10 * 12, 5 * 9 * 10 * 12, 5 * 8 * 10 * 12, 5 * 8 * 9 * 12, 5 * 8 * 9 * 10]
    int temp = 1;
    for (int i = nums.length - 1; i >= 0; i--) {
      result[i] *= temp;
      temp *= nums[i];
    }
    return result;
  }

  //滑动窗口最大值
  //给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
  //返回滑动窗口中的最大值。
  public int[] maxSlidingWindow(int[] nums, int k) {
    if (nums == null || nums.length < k) {
      return null;
    }

    int[] result = new int[nums.length - k + 1];
    //维护一个从大到小的链表，存的是角标
    LinkedList<Integer> windowMax = new LinkedList<>();
    int index = 0;

    for (int i = 0; i < nums.length; i++) {

      while (!windowMax.isEmpty() && nums[windowMax.peekLast()] <= nums[i]) {
        windowMax.pollLast();
      }

      windowMax.addLast(i);

      if (windowMax.peekFirst() == i - k) {
        //左边要出边界了
        windowMax.pollFirst();
      }
      if (i >= k - 1) {
        result[index] = nums[windowMax.peekFirst()];
        index++;
      }
    }
    return result;
  }

  //搜索二维矩阵 II
  //编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
  //每行的元素从左到右升序排列。
  //每列的元素从上到下升序排列。
  //从左下或者右上开始，时间复杂度O(Math.max(matrix.length, matrix[0].length))，空间复杂度O(1)
  public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return false;
    }
    int row = matrix.length;
    int rowStart = 0;
    int columnStart = matrix[0].length - 1;
    while (rowStart < row && columnStart >= 0) {
      if (matrix[rowStart][columnStart] == target) {
        return true;
      } else if (matrix[rowStart][columnStart] > target) {
        columnStart--;
      } else {
        rowStart++;
      }
    }
    return false;

  }

  // 完全平方数
  //给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
  //给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。
  //完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
  //动态规划，时间复杂度O(n^3/2)，空间复杂度O(n)
  public int numSquares(int n) {
    if (n == 0) {
      return 0;
    }

    //动态数组，每一个位置表示到当前位置最少需要几个完全平方数的和
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      int min = Integer.MAX_VALUE;
      //动态方程：dp[i] = 1 + {min(i - j * j) [j = 1 .... i^1/2]}
      //假设i = 10，j就等于1，2，3，这样找到当j=3的时候，min最小，dp[10] = dp[10 - 3 * 3] + 1 = dp[1] + 1
      //也就是10位置，可以由9+1得到，也就是3 * 3 + 1 * 1
      for (int j = 1; j * j <= i; j++) {
        min = Math.min(min, dp[i - j * j]);
      }
      dp[i] = min + 1;
    }
    return dp[n];
  }

  //移动零
  //给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
  //双指针，时间复杂度O(n)，空间复杂度O(1)
  public void moveZeroes(int[] nums) {
    if (nums == null || nums.length == 0) {
      return;
    }

    int left = 0;
    int right = 0;

    //left的左边是都已经处理过的
    //right的右边，包括right都是还没处理过的
    //每次都是拿right位置上的数和left位置上的数做交换
    //同时维持：
    // 1.left，right中间的区域都是0
    // 2.left的左边都是非0
    while (left < nums.length && right < nums.length) {
      if (nums[right] != 0) {
        swap(nums, left, right);
        left++;
      }
      right++;
    }
  }

  //寻找重复数
  //给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
  //假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
  //你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。
  //类似环形链表的解决思路，快慢指针，时间复杂度O(n)，空间复杂度O(1)
  public int findDuplicate(int[] nums) {

    int fast = nums[nums[0]];
    int slow = nums[0];

    while (slow != fast) {
      slow = nums[slow];
      fast = nums[nums[fast]];
    }

    slow = 0;
    while (slow != fast) {
      slow = nums[slow];
      fast = nums[fast];
    }

    return slow;

  }

  //二叉树的序列化与反序列化
  //序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
  //请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
  //提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。
//  public class Codec {
//
//    // Encodes a tree to a single string.
//    public String serialize(TreeNode root) {
//
//    }
//
//    // Decodes your encoded data to tree.
//    public TreeNode deserialize(String data) {
//
//    }
//  }

  //最长递增子序列
  //给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
  //子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
  public int lengthOfLIS(int[] nums) {

    if (nums == null || nums.length == 0) {
      return 0;
    }
    //动态规划，时间复杂度O(N*N)
//    int[] dp = new int[nums.length];
//    dp[0] = 1;
//
//    int max = 1;
//    //动态方程方程：dp[i] = Math.max(dp[0] ~ dp[i - 1]) + 1，(如果dp[i]大于dp[0] ~ dp[i - 1]中d元素)，否则就是1
//    for (int i  = 1; i < nums.length; i++) {
//      for (int j = 1; j < i; j++) {
//        if (nums[j] > nums[i]) {
//          dp[i] = Math.max(dp[i], dp[j] + 1);
//        }
//        max = Math.max(max, dp[i]);
//      }
//
//    }
//    return max;

    //动态规划 + 二分查找，时间复杂度O(n)
    //辅助数组，表示到当前位置位置，增幅最小的，
    //   比如：[10,9,2,5,3,7,21,18]
    //   [10] ->[9] -> [2] -> [2,5] -> [2,3] -> [2,3,7] -> [2,3,7,21] -> [2,3,7,18]
    //辅助数组：[2,3,7,18,0,0,0,0] -> result = 4

    int[] dp = new int[nums.length + 1];
    dp[1] = nums[0];
    int length = 1;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > dp[length]) {
        dp[++length] = nums[i];
      } else {
        //二分查找，找到nums[i]合适的位置
        int left = 1;
        int right = length;
        while (left <= right) {
          int mid = left + (right - left) / 2;
          if (nums[i] > dp[mid]) {
            left = mid + 1;
          } else {
            right = mid - 1;
          }
        }
        dp[left] = nums[i];
      }
    }
    return length;
  }

  //删除无效的括号
  //给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
  //返回所有可能的结果。答案可以按 任意顺序 返回。
//  public List<String> removeInvalidParentheses(String s) {
//
//  }

  //最佳买卖股票时机含冷冻期
  //给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
  //设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
  //你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
  //卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。

  public int maxProfit1(int[] prices) {
    //具体参考StockServiceImpl
    return 0;
  }

  //目标和
  //给你一个整数数组 nums 和一个整数 target 。
  //向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
  //例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
  //返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
  int findTargetSumWaysCount = 0;

  public int findTargetSumWays(int[] nums, int target) {
//    return findTargetSumWaysSub(nums, target);
    return findTargetSumWaysDP(nums, target);
  }

  //回朔，时间复杂度O(2^n)，空间复杂度O(n)
  public int findTargetSumWaysSub(int[] nums, int target) {
    findTargetSumWaysBackTrack(nums, target, 0, 0);
    return findTargetSumWaysCount;
  }

  public void findTargetSumWaysBackTrack(int[] nums, int target, int index, int sum) {
    if (index == nums.length - 1) {
      if (target == sum) {
        findTargetSumWaysCount++;
      }
      return;
    }

    findTargetSumWaysBackTrack(nums, target, index + 1, sum + nums[index]);
    findTargetSumWaysBackTrack(nums, target, index + 1, sum - nums[index]);
  }

  public int findTargetSumWaysDP(int[] nums, int target) {
    //动态规划
    //每一个数都有两个状态：+，-
    //dp[i][j] = dp[i - 1][j + nums[i]] + dp[i - 1][j - nums[i]]
    //注意：dp[1][0] = dp[0][0+1] + dp[0][-1];这里表示在初始化的时候要考虑减法的情况
    int sum = 0;
    for (Integer num : nums) {
      sum += num;
    }

    if (Math.abs(sum) < Math.abs(target)) {
      return 0;
    }

    int length = nums.length;
    //考虑正负的情况
    int colLength = sum * 2 + 1;
    int[][] dp = new int[length][colLength];

    //初始化
    if (nums[0] == 0) {
      //加，减0，共两种情况
      dp[0][sum] = 2;
    } else {
      dp[0][sum + nums[0]] = 1;
      dp[0][sum - nums[0]] = 1;
    }

    for (int i = 1; i < length; i++) {
      for (int j = 0; j < colLength; j++) {
        int l = (j - nums[i] >= 0) ? j - nums[i] : 0;
        int r = (j + nums[i] < colLength) ? j + nums[i] : 0;
        dp[i][j] = dp[i - 1][l] + dp[i - 1][r];
      }
    }

    return dp[length - 1][sum + target];
  }


  //汉明距离
  //两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
  //给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
  public int hammingDistance(int x, int y) {
    //x^y可以得到所有不相同的位置
    int z = x ^ y;
    int result = 0;
    while (z != 0) {
      //z于1做与运算，如果z最后一位是1，就得到1，如果是0就得到0
      result += z & 1;
      //z向右移动一位
      z >>= 1;
    }
    return result;
  }

  //找到所有数组中消失的数字
  //给你一个含n个整数的数组nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
  public List<Integer> findDisappearedNumbers(int[] nums) {
    int[] help = new int[nums.length];
    for (int num : nums) {
      help[num - 1] = 1;
    }

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
      if (help[i] == 0) {
        result.add(i + 1);
      }
    }
    return result;
  }

  //找到字符串中所有字母异位词
  //给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
  //异位词 指字母相同，但排列不同的字符串。
  //时间复杂度O(n)，空间复杂度O(1)
  public List<Integer> findAnagrams(String s, String p) {
    int sLength = s.length();
    int pLength = p.length();

    if (sLength < pLength) {
      return new ArrayList<>();
    }

    List<Integer> result = new ArrayList<>();
    int[] count = new int[26];

    //得到s和p中前几位字母差多少位
    for (int i = 0; i < pLength; i++) {
      count[s.charAt(i) - 'a']++;
      count[p.charAt(i) - 'a']--;
    }
    int differ = 0;
    for (int i = 0; i < 26; i++) {
      if (count[i] != 0) {
        differ++;
      }
    }

    //如果没有差，说明s的前几位字符和p是异位词
    if (differ == 0) {
      result.add(0);
    }

    for (int i = 0; i < sLength - pLength; i++) {
      //左边界准备往右移动，也就是要将i位置上的字符去掉
      if (count[s.charAt(i) - 'a'] == 1) {
        differ--;
      } else if (count[s.charAt(i) - 'a'] == 0) {
        differ++;
      }
      count[s.charAt(i) - 'a']--;

      //右边界准备往右移动，
      if (count[s.charAt(i + pLength) - 'a'] == -1) {
        differ--;
      } else if (count[s.charAt(i + pLength) - 'a'] == 0) {
        differ++;
      }
      count[s.charAt(i + pLength) - 'a']++;

      if (differ == 0) {
        result.add(i + 1);
      }

    }

    return result;
  }

  //路径总和 III
  //给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
  //路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
  //时间复杂度O(n)，空间复杂度O(n)
  //map中放的是从跟节点房目前节点位置的路径和
  public int pathSum(TreeNode root, int targetSum) {
    if (root == null) {
      return 0;
    }
    Map<Long, Integer> map = new HashMap<>();
    map.put(0L, 1);
    return dfs(root, map, 0l, targetSum);
  }

  public int dfs(TreeNode root, Map<Long, Integer> map, Long curr, int targetSum) {
    if (root == null) {
      return 0;
    }

    int ret = 0;
    curr += root.value;
    ret = map.getOrDefault(curr - targetSum, 0);
    map.put(curr, map.getOrDefault(curr, 0) + 1);
    ret += dfs(root.left, map, curr, targetSum);
    ret += dfs(root.right, map, curr, targetSum);
    map.put(curr, map.getOrDefault(curr, 0) - 1);
    return ret;
  }


  //把二叉搜索树转换为累加树
  //给出二叉搜索树的根节点，该树的节点值各不相同，请你将其转换为累加树(Greater Sum Tree)，使每个节点node的新值等于原树中大于或等于node.val的值之和。
  //提醒一下，二叉搜索树满足下列约束条件：
  //节点的左子树仅包含键小于节点键的节点。
  //节点的右子树仅包含键大于节点键的节点。
  //左右子树也必须是二叉搜索树。
  //时间复杂度O(n)，空间复杂度O(n)
  int convertBSTSum = 0;

  public TreeNode convertBST(TreeNode root) {
    //每一个节点都是该节点右子树的和
    //中序遍历是左根右，这个的遍历结果是一个递增的数组
    //反序中序遍历是右左根，这个遍历结果是一个递减的数组
    //然后把遍历过的节点加和就好
    if (root == null) {
      return null;
    }

    convertBST(root.right);
    convertBSTSum += root.value;
    root.value = convertBSTSum;
    convertBST(root.left);
    return root;
  }

  //二叉树的直径
  //给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
  //理解成求最长路径的边长
  public int maxDiameter = 0;

  public int diameterOfBinaryTree(TreeNode root) {
    maxDiameter = 1;
    diameterOfBinaryTreeSub(root);
    return maxDiameter - 1;

  }

  public int diameterOfBinaryTreeSub(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int left = diameterOfBinaryTreeSub(root.left);
    int right = diameterOfBinaryTreeSub(root.right);
    maxDiameter = Math.max(maxDiameter, left + right + 1);
    return Math.max(left, right) + 1;
  }


  //和为K的子数组
  //给定一个整数数组和一个整数 k，你需要找到该数组中和为k的连续的子数组的个数。
  //时间复杂度O(n)，空间复杂度O(n)
  public int subarraySum(int[] nums, int k) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    //思路是，有一个只不停来记录到位置i为止，之前的和sum是多少，同时用一个map来记录这个sum值出现的次数
    //每当到达一个新位置i，求出sum后，检查map中sum-k出现的次数，就是对应的值
    //理论是:pre[i]表示i之前的和，那么在其中的某个位置j到i这个区间中，k出现的次数，就是pre[i] - k

    int result = 0;
    //用来记录当前数的和
    int sum = 0;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      result += map.getOrDefault(sum - k, 0);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return result;
  }


  //最短无序连续子数组
  //给你一个整数数组nums，你需要找出一个连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
  //请你找出符合题意的 最短子数组，并输出它的长度。
  //双指针，时间复杂度O(n)，空间复杂度O(1)
  public int findUnsortedSubarray(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    int leftIndex = -1;
    int rightIndex = -1;
    int leftMin = Integer.MAX_VALUE;
    int rightMax = Integer.MIN_VALUE;

    for (int i = 0; i < nums.length; i++) {
      //从左往右，不断更行遍历过的最大值，如果碰到小于最大值的，那么这里就可能是右边界之一，一直到最右
      if (rightMax > nums[i]) {
        rightIndex = i;
      } else {
        rightMax = nums[i];
      }

      //从后往前，只有碰到当前数大于后面最大数的时候，左指针才会更新成当前的数
      //否则是更新目前遍历过的数为止，最小的数
      if (leftMin < nums[nums.length - i - 1]) {
        leftIndex = nums.length - i - 1;
      } else {
        leftMin = nums[nums.length - i - 1];
      }
    }

    return rightIndex == -1 ? 0 : rightIndex - leftIndex + 1;
  }

  //合并二叉树
  //给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
  //你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
  //递归，时间复杂度O(min(m,n))，空间复杂度O(min(m,n)
  public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) {
      return null;
    }
    if (root1 == null) {
      return root2;
    }

    if (root2 == null) {
      return root1;
    }

    TreeNode left = mergeTrees(root1.left, root2.left);
    TreeNode right = mergeTrees(root1.right, root2.right);
    TreeNode newRoot = new TreeNode(root1.value + root2.value);
    newRoot.left = left;
    newRoot.right = right;
    return newRoot;

  }


  // 任务调度器
  //给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。
  // 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
  //然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
  //你需要计算完成所有任务所需要的 最短时间 。
  //时间复杂度O(n)，空间复杂度O(n)
  public int leastInterval(char[] tasks, int n) {
    if (tasks == null || tasks.length == 0) {
      return 0;
    }
    if (n == 0) {
      return tasks.length;
    }

    //[A, B, C, D, F, G, #]
    //[A, B, C, D, E, F, #]
    //[A, B, C, D, E, F, #]
    //[A, B, C, D, E, F, G]
    //[A, B, C, D, E, F, G]
    //[A, B, C]
    //这里maxLength = 5，maxCount = 3， n = 6
    //思路是：找到出现次数最多的字符，记录出现的次数，maxLength，这里是6
    //同时记录出现次数最多的字符的个数，maxCount，这里是3
    //n=6，也就意味着同一个字符中间需要隔6个，所以数组的宽度是6 + 1 = 7
    //最终结果就是（6 - 1）* （6 + 1）+ 3 = 38

    int maxLength = 0;
    Map<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < tasks.length; i++) {
      int length = map.getOrDefault(i, 0) + 1;
      map.put(tasks[i], length);
      maxLength = Math.max(maxLength, length);
    }

    int maxCount = 0;
    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
      if (entry.getValue() == maxLength) {
        maxCount++;
      }
    }

    return Math.max((maxLength - 1) * (n + 1) + maxCount, tasks.length);
  }


  //回文子串
  //给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
  //具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
  //manacher算法，时间复杂度O(n)，空间复杂度O(n)
  public int countSubstrings(String s) {
    if (s == null || s.equals("")) {
      return 0;
    }

    //为了考虑奇偶情况，字符之间添加无意义字符
    char[] chars = manacherStringSub(s);
    int[] radiusArray = new int[chars.length];
    //中心点
    int center = -1;
    //最右边界
    int rightBound = -1;

    int result = 0;
    for (int i = 0; i < chars.length; i++) {
      radiusArray[i] = rightBound > i ? Math.min(rightBound - i, radiusArray[2 * center - i]) : 1;
      while (i + radiusArray[i] < chars.length && i - radiusArray[i] > -1) {
        if (chars[i + radiusArray[i]] == chars[i - radiusArray[i]]) {
          radiusArray[i]++;
        } else {
          break;
        }
      }

      if (i + radiusArray[i] > rightBound) {
        center = i;
        rightBound = i + radiusArray[i];
      }

      result += (radiusArray[i] / 2);
    }
    return result;
  }


  public char[] manacherStringSub(String s) {
    char[] chars = s.toCharArray();
    //ab -> #a#b#
    char[] result = new char[chars.length * 2 + 1];
    int charsIndex = 0;
    for (int i = 0; i < result.length; i++) {
      result[i] = (i % 2) == 0 ? '#' : chars[charsIndex++];
    }
    return result;
  }


  // 每日温度
  //请根据每日气温列表 temperatures，请计算在每一天需要等几天才会有更高的温度。如果气温在这之后都不会升高，请在该位置用0来代替。
  //时间复杂度O(n)，空间复杂度O(n)
  public int[] dailyTemperatures(int[] temperatures) {
    if (temperatures == null || temperatures.length == 0) {
      return null;
    }

    //维护一个递减的栈
    //如果进来的温度比栈顶的元素要打，那么这个温度就是栈顶位置下一个增高的温度，取差就是栈顶位置对应的结果
    Stack<Integer> stack = new Stack<>();
    int[] result = new int[temperatures.length];

    for (int i = 0; i < temperatures.length; i++) {
      while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
        int index = stack.pop();
        result[index] = i - index;
      }
      stack.push(i);
    }

    return result;
  }


  //零钱兑换
  //给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
  //计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
  //你可以认为每种硬币的数量是无限的。
  public int coinChange(int[] coins, int amount) {

    //动态规划
    int max = amount + 1;
    int[] dp = new int[max];
    Arrays.fill(dp, max);
    dp[0] = 0;

    for (int i = 1; i < dp.length; i++) {
      for (int j = 0; j < coins.length; j++) {
        if (coins[j] <= i) {
          dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
        }
      }
    }

    return dp[amount] > amount ? -1 : dp[amount];

  }

  //分割等和子集
  //给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
  //时间复杂度O(n*sum)，空间复杂度O(n*sum)
  public boolean canPartition(int[] nums) {
    //0-1背包问题
    int sum = 0;
    for (int num : nums) {
      sum += num;
    }

    if (sum % 2 != 0) {
      return false;
    }
    sum = sum / 2;
    int length = nums.length;

    boolean[][] dp = new boolean[length][sum + 1];
    for (int i = 0; i < length; i++) {
      dp[i][0] = true;
    }

    if (nums[0] <= sum) {
      dp[0][nums[0]] = true;
    }
    for (int i = 1; i < length; i++) {
      for (int j = 1; j <= sum; j++) {
        if (nums[i] > j) {
          dp[i][j] = dp[i - 1][j];
        } else {
          dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
        }
      }
    }

    return dp[length - 1][sum];
  }

  //除法求值
  //给你一个变量对数组 equations 和一个实数值数组 values 作为已知条件，其中 equations[i] = [Ai, Bi] 和 values[i] 共同表示等式 Ai / Bi = values[i] 。每个 Ai 或 Bi 是一个表示单个变量的字符串。
  //另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj = ? 的结果作为答案。
  //返回 所有问题的答案 。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替代这个答案。
  //注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
  //输入：equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
  //输出：[6.00000,0.50000,-1.00000,1.00000,-1.00000]
  //输入：equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
  //输出：[3.75000,0.40000,5.00000,0.20000]
  //a/b=1.5  b/c=2.5  bc/cd=5.0 -> a/c=? c/b=?  bc/cd=?  cd/bc=?
//  public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
//
//  }

  // 打家劫舍 III
  //在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。
  // 这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。
  // 一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
  // 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
  //计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
  Map<TreeNode, Integer> fFunction = new HashMap<>();
  Map<TreeNode, Integer> gFunction = new HashMap<>();

  public int rob3(TreeNode root) {
    //每个节点分成两个状态
    //1。选取该节点，那么就不能选取他的子节点，所以当前节点的最大值F(O) = node.val + G(L) + G(R)
    //2。不选取该节点，那么可以选取他的子节点，所以当前节点的最大值G(O) = MAX(F(L),G(L)) + MAX(F(R),G(R))
    //其中G函数，表示不选这个节点时，他的子树的最大值，L和R表示这个节点左右子节点
    //其中F函数，表示选这个节点时，这个节点子树的最大值
    //使用后序遍历，左右根，同时用两个map分别记录每一个节点对应的两个函数的值
    robSub(root);
    int result = Math.max(fFunction.getOrDefault(root, 0), gFunction.getOrDefault(root, 0));
    return result;
  }

  public void robSub(TreeNode root) {
    if (root == null) {
      return;
    }

    robSub(root.left);
    robSub(root.right);
    fFunction.put(root,
        root.value + gFunction.getOrDefault(root.left, 0) + gFunction.getOrDefault(root.right, 0));
    gFunction.put(root,
        Math.max(gFunction.getOrDefault(root.left, 0), fFunction.getOrDefault(root.left, 0)) + Math
            .max(gFunction.getOrDefault(root.right, 0), fFunction.getOrDefault(root.right, 0)));
  }

  //字符串解码
  //给定一个经过编码的字符串，返回它解码后的字符串。
  //编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
  //你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
  //此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
  public String decodeString(String s) {
    //方法一：借助栈，时间复杂度O(n)，空间复杂度O(n)
    StringBuffer sb = new StringBuffer();

    //存放非数字之外的字符
    Stack<String> stack1 = new Stack<>();
    //存放数字
    Stack<Integer> stack2 = new Stack<>();
    int multi = 0;

    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '[') {
        //将现有的字符串放入栈中，并同步放入对应的数字
        stack1.add(sb.toString());
        stack2.add(multi);
        multi = 0;
        sb = new StringBuffer();
      } else if (s.charAt(i) == ']') {
        //将上次存入的数拿出来，乘以数字
        StringBuffer temp = new StringBuffer();
        int currMulti = stack2.pop();
        for (int j = 0; j < currMulti; j++) {
          temp.append(sb);
        }
        sb = new StringBuffer(stack1.pop() + temp);

      } else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
        //获取一个完整的数
        multi = multi * 10 + Integer.parseInt(s.charAt(i) + "");
      } else {
        //也就是字符
        sb.append(s.charAt(i));
      }
    }

    return sb.toString();

  }

  //递归，时间复杂度O(n)，空间复杂度O(n)
  public String decodeString1(String s) {
    return dfs(s, 0)[0];
  }

  private String[] dfs(String s, int i) {
    StringBuilder res = new StringBuilder();
    int multi = 0;
    while (i < s.length()) {
      if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
        multi = multi * 10 + Integer.parseInt(String.valueOf(s.charAt(i)));
      } else if (s.charAt(i) == '[') {
        String[] tmp = dfs(s, i + 1);
        i = Integer.parseInt(tmp[0]);
        while (multi > 0) {
          res.append(tmp[1]);
          multi--;
        }
      } else if (s.charAt(i) == ']') {
        return new String[]{String.valueOf(i), res.toString()};
      } else {
        res.append(String.valueOf(s.charAt(i)));
      }
      i++;
    }
    return new String[]{res.toString()};
  }


  //根据身高重建队列
  //假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
  //请你重新构造并返回输入数组 people 所表示的队列。返回的队列应该格式化为数组 queue ，其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
  public int[][] reconstructQueue(int[][] people) {
    //输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
    //输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
    if (people == null || people.length == 0) {
      return null;
    }

    Arrays.sort(people, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        if (o1[0] == o2[0]) {
          //如果高度相等，按照位置序号递增排序
          return o1[1] - o2[1];
        } else {
          //如果高度不相等，按照按照身高倒叙排列
          return o2[0] - o1[0];
        }
      }
    });
    //[[7,0],[7,1],[6,1],[5,0],[5,2],[4,4]]

    List<int[]> list = new LinkedList<>();
    for (int[] i : people) {
      list.add(i[1], i);
    }
    return list.toArray(new int[list.size()][2]);
  }

  //K 个一组翻转链表
  //给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
  //k 是一个正整数，它的值小于或等于链表的长度。
  //如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
  //时间复杂度O(n)，空间复杂度O(1)
  public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode pre = dummy;

    while (head != null) {
      ListNode tail = pre;
      //此时找到了k个节点之后
      for (int i = 0; i < k; i++) {
        tail = tail.next;
        if (tail == null) {
          return dummy.next;
        }
      }

      ListNode tailNext = tail.next;
      ListNode[] reverse = myReverse(head, tail);
      head = reverse[0];
      tail = reverse[1];
      pre.next = head;
      tail.next = tailNext;
      pre = tail;
      head = tail.next;
    }

    return dummy.next;

  }

  //前 K 个高频元素
  //给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
  public ListNode[] myReverse(ListNode head, ListNode tail) {
    ListNode prev = tail.next;
    ListNode p = head;
    while (prev != tail) {
      ListNode nex = p.next;
      p.next = prev;
      prev = p;
      p = nex;
    }
    return new ListNode[]{tail, head};
  }

  public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> occurrences = new HashMap<Integer, Integer>();
    for (int num : nums) {
      occurrences.put(num, occurrences.getOrDefault(num, 0) + 1);
    }

    // int[] 的第一个元素代表数组的值，第二个元素代表了该值出现的次数
    PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
      public int compare(int[] m, int[] n) {
        return m[1] - n[1];
      }
    });
    for (Map.Entry<Integer, Integer> entry : occurrences.entrySet()) {
      int num = entry.getKey(), count = entry.getValue();
      if (queue.size() == k) {
        if (queue.peek()[1] < count) {
          queue.poll();
          queue.offer(new int[]{num, count});
        }
      } else {
        queue.offer(new int[]{num, count});
      }
    }
    int[] ret = new int[k];
    for (int i = 0; i < k; ++i) {
      ret[i] = queue.poll()[0];
    }
    return ret;
  }


}




















