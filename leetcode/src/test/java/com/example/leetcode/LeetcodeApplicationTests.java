package com.example.leetcode;

import com.example.leetcode.service.ArrayServiceImpl;
import com.example.leetcode.service.ArraySortImplTwo;
import com.example.leetcode.service.TreeServiceImpl;
import com.example.leetcode.service.ZuoClassDPPractice;
import com.example.leetcode.service.ZuoClassGraphPractice;
import com.example.leetcode.service.ZuoClassSlidingWindowPractice;
import com.example.leetcode.service.ZuoClassThreePractice;
import com.example.leetcode.service.ZuoClassThreePractice.DoubleNode;
import com.example.leetcode.utils.Graph;
import com.example.leetcode.utils.GraphGenerator;
import com.example.leetcode.utils.GraphNode;
import com.example.leetcode.utils.Node;
import com.example.leetcode.utils.ParentNode;
import com.example.leetcode.utils.RandomNode;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired
  private TreeServiceImpl treeService;

  @Autowired
  private ZuoClassGraphPractice zuoClassGraphPractice;

  @Autowired
  private ZuoClassDPPractice zuoClassDPPractice;

  @Autowired
  private ZuoClassSlidingWindowPractice zuoClassSlidingWindowPractice;

  @Test
  void contextLoads() throws Exception {


//    int[] array = new int[]{1, 5, 4, 3, 2, 6, 8, 7, 9};
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

    /**
     * 1,   2,  3,  4
     * 5,   6,  7,  8
     * 9,  10, 11, 12
     * 13, 14, 15, 16
     */

//    int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
//    leetCode.rotateMatrix(matrix);
//    leetCode.printMatrix(matrix);

    //之子打印矩阵
//    leetCode.printZigMatrix(matrix);

//    Node head1 = new Node(9);
//    head1.next = new Node(0);
//    head1.next.next = new Node(1);
//    head1.next.next.next = new Node(4);
//    head1.next.next.next.next = new Node(2);
//    head1.next.next.next.next.next = new Node(3);
//    head1.next.next.next.next.next.next = new Node(5);
//    Node node = leetCode.reverseNodeList(head1);
//    printLinkedList(node);

//    boolean palindrome = leetCode.isPalindrome(head1);

//    Node node = leetCode.listPartition(head1, 3);
//    printLinkedList(node);

//    DoubleNode head2 = new DoubleNode(1);
//    head2.next = new DoubleNode(2);
//    head2.next.last = head2;
//    head2.next.next = new DoubleNode(3);
//    head2.next.next.last = head2.next;
//    head2.next.next.next = new DoubleNode(4);
//    head2.next.next.next.last = head2.next.next;
//
//    DoubleNode doubleNode = leetCode.reverseDoubleNodeList(head2);
//    printDoubleLinkedList(doubleNode);

//    RandomNode head = null;
//
//    head = new RandomNode(1);
//    head.next = new RandomNode(2);
//    head.next.next = new RandomNode(3);
//    head.next.next.next = new RandomNode(4);
//    head.next.next.next.next = new RandomNode(5);
//    head.next.next.next.next.next = new RandomNode(6);
//
//    head.rand = head.next.next.next.next.next; // 1 -> 6
//    head.next.rand = head.next.next.next.next.next; // 2 -> 6
//    head.next.next.rand = head.next.next.next.next; // 3 -> 5
//    head.next.next.next.rand = head.next.next; // 4 -> 3
//    head.next.next.next.next.rand = null; // 5 -> null
//    head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4
//
//    printRandLinkedList(head);
//    RandomNode randomNode = leetCode.copyRandomNodeList(head);
//    printRandLinkedList(randomNode);

    // 1->2->3->4->5->6->7->null
//    Node head1 = new Node(1);
//    head1.next = new Node(2);
//    head1.next.next = new Node(3);
//    head1.next.next.next = new Node(4);
//    head1.next.next.next.next = new Node(5);
//    head1.next.next.next.next.next = new Node(6);
//    head1.next.next.next.next.next.next = new Node(7);
//
//    // 0->9->8->6->7->null
//    Node head2 = new Node(0);
//    head2.next = new Node(9);
//    head2.next.next = new Node(8);
//    head2.next.next.next = head1.next.next.next.next.next; // 8->6
//    //2 无环，在6相交
//    System.out.println(leetCode.getIntersectNode(head1, head2).value);
//
//    // 1->2->3->4->5->6->7->4...
//    head1 = new Node(1);
//    head1.next = new Node(2);
//    head1.next.next = new Node(3);
//    head1.next.next.next = new Node(4);
//    head1.next.next.next.next = new Node(5);
//    head1.next.next.next.next.next = new Node(6);
//    head1.next.next.next.next.next.next = new Node(7);
//    //head1有环，在4
//    head1.next.next.next.next.next.next = head1.next.next.next; // 7->4
//
//    // 0->9->8->2...
//    head2 = new Node(0);
//    head2.next = new Node(9);
//    head2.next.next = new Node(8);
//    head2.next.next.next = head1.next; // 8->2
//    //3。2 head1和head2环外相交，交点在2
//    System.out.println(leetCode.getIntersectNode(head1, head2).value);
//
//    // 0->9->8->6->7->4->5->6..
//    head2 = new Node(0);
//    head2.next = new Node(9);
//    head2.next.next = new Node(8);
//    head2.next.next.next = head1.next.next.next.next.next; // 8->6
//    //3。3 head1和head2环上相交， 交点是6或者4
//    System.out.println(leetCode.getIntersectNode(head1, head2).value);

//    TreeNode head = new TreeNode(1);
//    head.left = new TreeNode(2);
//    head.right = new TreeNode(3);
//    head.left.left = new TreeNode(4);
//    head.left.right = new TreeNode(5);
//    head.right.left = new TreeNode(6);
//    head.right.right = new TreeNode(7);
//
//    int nodeNum = treeService.getNodeNum(head);
//    System.out.println(nodeNum);

//    PrintBinaryTree.printTree(head);
//    treeService.postOrderUnRecur(head);

//    ParentNode head = new ParentNode(1);
//    head.left = new ParentNode(2);
//    head.left.parent = head;
//    head.right = new ParentNode(3);
//    head.right.parent = head;
//
//    head.left.left = new ParentNode(4);
//    head.left.left.parent = head.left;
//    head.left.right = new ParentNode(5);
//    head.left.right.parent = head.left;
//
//    head.right.left = new ParentNode(6);
//    head.right.left.parent = head.right;
//    head.right.right = new ParentNode(7);
//    head.right.right.parent = head.right;
//    ParentNode successorNode = getSuccessorNode(head.right.left);
//    System.out.println(successorNode.value);
//
//    ParentNode successorNode = treeService.getSuccessorNode(head.right.right);
//    System.out.println(successorNode == null ? "null" : successorNode.value);


    //图
//    Graph graph = generateGraph();
//    zuoClassGraphPractice.dfs(graph.nodes.get(1));

//    int[] num = {2,7,9,3,1};
//    int rob = rob(num);
//    System.out.println(rob);
//    String a  = "abcd";
//    zuoClassDPPractice.printAllPermutations1(a);
//    zuoClassDPPractice.printAllSubsquence("zang");
//    int[][] matrix = { { 1, 3, 5, 9 }, { 8, 1, 3, 5 }, { 5, 0, 6, 1 }, { 8, 8, 4, 0 } };
////    int minPath = zuoClassDPPractice.minPath(matrix);
//    int[] arr = {1, 2, 3};
//    int aim = 5;
//    boolean money = zuoClassDPPractice.money(arr, aim);
//    System.out.println(money);
    int[] array = {3, 1, 2, 4, 1, 2};
    int water1 = zuoClassSlidingWindowPractice.getWater1(array);
    System.out.println(water1);
    int water2 = zuoClassSlidingWindowPractice.getWater2(array);
    System.out.println(water2);
    int water3 = zuoClassSlidingWindowPractice.getWater3(array);
    System.out.println(water3);

  }

  public int rob(int[] nums) {
    if (nums.length == 0) {
      return 0;
    }

    int length = nums.length;
    int[] dp = new int[length + 1];
    dp[0] = 0;
    dp[1] = nums[0];
    for (int k = 2; k <= length; k++) {
      dp[k] = Math.max(dp[k-1], nums[k-1] + dp[k-2]);
    }
    return dp[length];
  }

  public Graph generateGraph() {
    int[][] matrix = {{0, 1, 2}, {0, 1, 3}, {0, 1, 4}, {0, 2, 3}, {0, 2, 7}, {0, 7, 3}, {0, 3, 5}, {0, 4, 6}};
    Graph graph = GraphGenerator.createGraph(matrix);
    return graph;
  }

  public ParentNode getSuccessorNode(ParentNode node) {
    if (node == null) {
      return null;
    }

    //如果右子树不为空
    if (node.right != null) {
      return getLeftMost(node.right);
    }

    //如果右子树为空
    ParentNode parent = node.parent;
    ParentNode curr = node;
    while (parent != null && parent.left != curr) {
      curr = parent;
      parent = curr.parent;
    }

    return parent;

  }

  public ParentNode getLeftMost(ParentNode node) {
    if (node == null) {
      return null;
    }

    ParentNode curr = node;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }

  public static void printRandLinkedList(RandomNode head) {
    RandomNode cur = head;
    System.out.print("order: ");
    while (cur != null) {
      System.out.print(cur.value + " ");
      cur = cur.next;
    }
    System.out.println();
    cur = head;
    System.out.print("rand:  ");
    while (cur != null) {
      System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
      cur = cur.next;
    }
    System.out.println();
  }


  public static void printLinkedList(Node head) {
    System.out.print("Linked List: ");
    while (head != null) {
      System.out.print(head.value + " ");
      head = head.next;
    }
  }

  public static void printDoubleLinkedList(DoubleNode head) {
    System.out.print("Double Linked List: ");
    DoubleNode end = null;
    while (head != null) {
      System.out.print(head.value + " ");
      end = head;
      head = head.next;
    }
    System.out.print("| ");
    while (end != null) {
      System.out.print(end.value + " ");
      end = end.last;
    }
    System.out.println();
  }


  public String printArray(int[] array) {
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
