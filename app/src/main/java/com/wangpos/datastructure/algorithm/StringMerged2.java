package com.wangpos.datastructure.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 两个集合映射组合算法
 */
public class StringMerged2 {
    public static Node root = new Node(null, "", null);

    public static List<Node> currentNodeList = new ArrayList<>();

    public static void main(String[] args) {
        String str1 = "ABC";
        String str2 = "DEF";
        String str3 = "GHI";
        String str4 = "JKL";
        String str5 = "MNO";
        String str6 = "PQRS";
        String str7 = "TUVW";
        String str8 = "XYZ";
        findMerged(str1);
        findMerged(str2);
        findMerged(str3);
        findMerged(str4);
        findMerged(str5);
        findMerged(str6);
        findMerged(str7);

        System.out.println("start=" + System.currentTimeMillis());
        List<String> results = findMerged(str8);
        System.out.println("end=" + System.currentTimeMillis());
//        for (Leaf leaf : currentLeafList) {
//            System.out.print(leaf.value + ",");
//        }

        System.out.println("总结果数量：" + results.size());
//        for (String result : results) {
//            System.out.print(result + ",");
//        }

//        List<String> results = findMerged(str1);
    }

    public static List<String> findMerged(String str) {
        char chas[] = str.toCharArray();

        if (currentNodeList.size() == 0) {
            List<Node> list = new ArrayList<>();
            for (char cha : chas) {
                list.add(new Node(root, String.valueOf(cha), null));
            }
            currentNodeList = list;
        } else {
            List<Node> nodeList = new ArrayList<>();//底部叶子节点
            for (Node node : currentNodeList) {
                List<Node> childList = new ArrayList<>();//创建每一个孩子集合
                for (char cha : chas) {
                    Node child = new Node(node, String.valueOf(cha), null);
                    childList.add(child);
                    nodeList.add(child);
                }
                node.childs = childList;
            }
            currentNodeList = nodeList;
        }

        List<String> results = new ArrayList<>();
        for (Node node : currentNodeList) {
            String end = "";
            Node current = node;
            while (current != null) {
                end = current.value + end;
                current = current.parent;
            }
            results.add(end);
        }
        return results;
    }


    static class Node {
        public Node parent;
        public String value;
        public List<Node> childs;

        public Node(Node p, String value, List<Node> childs) {
            this.parent = p;
            this.childs = childs;
            this.value = value;
        }
    }
}
