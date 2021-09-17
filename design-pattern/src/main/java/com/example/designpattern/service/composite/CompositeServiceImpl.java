package com.example.designpattern.service.composite;

import org.springframework.stereotype.Service;

/**
 * 组合模式
 */
@Service
public class CompositeServiceImpl {


  public void composite() {
    BranchNode root = new BranchNode("root");
    BranchNode chapter1 = new BranchNode("chapter1");
    Node node11 = new LeafNode("node11");
    Node node12 = new LeafNode("node12");

    BranchNode chapter2 = new BranchNode("chapter2");
    BranchNode branch21 = new BranchNode("branch21");
    Node node211 = new LeafNode("node211");
    Node node212 = new LeafNode("node212");

    root.add(chapter1);
    root.add(chapter2);

    chapter1.add(node11);
    chapter1.add(node12);

    chapter2.add(branch21);
    branch21.add(node211);
    branch21.add(node212);
    /**
     *                    root
     *                  /       \
     *          chapter1        chapter2
     *          /       \           \
     *        node11   node12       branch21
     *                              /     \
     *                          node211  node212
     */
  }
}
