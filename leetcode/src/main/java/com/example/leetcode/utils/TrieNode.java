package com.example.leetcode.utils;

/**
 * @author yuxiangzang
 * @create 2021-03-23-下午6:53
 * @desc
 **/
public class TrieNode {

  //经过这个节点的次数
  public int path;

  //以这个节点为结尾的单词个数
  public int end;

  //该节点的子节点，一般是26个，对应26个字母，如果没有这个字母的，位置上为null
  //这样在字典树中，字母表示的是路径
  public TrieNode[] nexts;

  public TrieNode() {
    path = 0;
    end = 0;
    nexts = new TrieNode[26];
  }
}
