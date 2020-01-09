package com.wangpos.datastructure.leetcode;

import java.util.Stack;

public class LeetCode1214 {

    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        searchRoot11(root1, stack1);
        searchRoot22(root2, stack2);
        int t = 0;
        int small =  stack1.pop();
        int large =  stack2.pop();
        while (!stack1.empty() || !stack2.empty()) {
            t = small + large;
            if (t == target) {
                return true;
            } else if (t > target) {
                // 取较小的
                if(!stack1.empty()) {
                    small = stack1.pop();
                }else{
                    break;
                }
            } else {
                // 取较大的
                if(!stack2.empty()) {
                    large = stack2.pop();
                }else{
                    break;
                }
            }
        }
        return false;
    }

    private void searchRoot11(TreeNode root1, Stack stack) {
        if (root1 == null) {
            return;
        }
        searchRoot11(root1.getLeft(), stack);
        stack.add(root1.getVal());
        searchRoot11(root1.getRight(), stack);
    }

    private void searchRoot22(TreeNode root1, Stack stack) {

        if (root1 == null) {
            return;
        }
        searchRoot22(root1.getRight(), stack);
        stack.add(root1.getVal());
        searchRoot22(root1.getLeft(), stack);
    }
//
//    fun twoSumBSTs2(root1: TreeNode?, root2: TreeNode?, target: Int): Boolean {
//
//        val stack1 = Stack<Int>()//从小到大 top最大
//        val stack2 = Stack<Int>()//从大到小
//        searchRoot11(root1, stack1)
//        searchRoot22(root2, stack2)
//        var t = 0
//        var small = stack1.pop()
//        var large = stack2.pop()
//        while (stack1.isNotEmpty() && stack2.isNotEmpty()) {
//            t = small + large
//            println(small)
//            println(large)
//            if (t == target) {
//                return true
//            } else if (t > target) {
//                // 取较小的
//                small = stack1.pop()
//            } else {
//                // 取较大的
//                large = stack2.pop()
//            }
//        }
//        return false
//    }
//
//    fun searchRoot11(root1: TreeNode?, stack: Stack<Int>) {
//        if (root1 == null) {
//            return
//        }
//        searchRoot11(root1.left, stack)
//        stack.add(root1.`val`)
//        searchRoot11(root1.right, stack)
//    }
//
//    fun searchRoot22(root2: TreeNode?, stack: Stack<Int>) {
//        if (root2 == null) {
//            return
//        }
//        searchRoot22(root2.right, stack)
//        stack.add(root2.`val`)
//        searchRoot22(root2.left, stack)
//    }
}
