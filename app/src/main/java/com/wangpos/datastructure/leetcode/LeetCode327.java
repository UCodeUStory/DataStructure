package com.wangpos.datastructure.leetcode;

import java.util.ArrayList;
import java.util.List;


class Solution {
    public int countRangeSum(int[] nums, int lower, int upper) {
        if(nums.length==0){
            return 0;
        }
        LeetCode327 leetCode327 = new LeetCode327(nums);
        return leetCode327.countRange(lower, upper);
    }
}

public class LeetCode327 {
    SegementTree<Integer> tree;

    public LeetCode327(int[] nums) {

        Merger<Integer> merger = new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                return a + b;
            }
        };

        Integer data[] = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            data[i] = nums[i];
        }
        tree = new SegementTree(data, merger);
        tree.printSegementTree();

    }

    public int countRange(int i, int j) {
        return tree.countRangeSum(i, j);
    }


    public interface Merger<E> {
        public E merge(E a, E b);
    }

    public static class SegementTree<E> {
        public E[] data;

        public E[] tree;

        public Merger<E> merger;

        public SegementTree(E[] data, Merger<E> merger) {
            if (data.length == 0) {
                return;
            }
            this.merger = merger;
            int length = data.length;
            this.data = (E[]) new Object[length];
            //复制数据到data中
            for (int i = 0; i < length; i++) {
                this.data[i] = data[i];
            }
            //总共n个叶子节点，n-1个非叶子节点
            tree = (E[]) new Object[length * 4];
            //构造线段树
            buildSegementTree(0, 0, length - 1);
        }

        public void buildSegementTree(int treeIndex, int left, int right) {
            if (left == right) {
                //如果left==right，证明递归结束，在对应的index设置data里left的值
                tree[treeIndex] = data[left];
                return;
            }
            //tree中父节点为treeIndex,的左右孩子的index
            int leftChildIndex = getLeftChild(treeIndex);
            int rightChildIndex = getRightChild(treeIndex);
            int mid = left + (right - left) / 2;//如果偶数左边右边一样多，如果是奇数，算到左边中
            //构造左右孩子节点
            buildSegementTree(leftChildIndex, left, mid);
            buildSegementTree(rightChildIndex, mid + 1, right);
            //根据左右孩子的值，通过合成器，决定父节点的值
            tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
        }

        public int getLeftChild(int index) {
            return 2 * index + 1;
        }

        public int getRightChild(int index) {
            return 2 * index + 2;
        }

        public void printSegementTree() {
            System.out.println("开始打印线段树----------");
            System.out.println("线段树数据的长度为" + data.length);
            for (int i = 0; i < tree.length; i++) {
                System.out.println("位置" + i + ": " + tree[i]);
            }

            System.out.println("打印线段树结束----------");
        }

        public int countRangeSum(int left, int right) {
            int l = left;
            int r = right;

            List<Integer> posList = new ArrayList<Integer>();
            queryCount(0, 0, data.length - 1, l, r, posList);
            return posList.size();
        }

        public void queryCount(int treeIndex, int left, int right, int l, int r, List<Integer> posList) {
            if (left == right) {
                //如果left==right，证明递归结束，在对应的index设置data里left的值
//                System.out.println("size>>>>>" + tree[treeIndex]);
                tree[treeIndex] = data[left];
                if ((Integer) tree[treeIndex] >= l && (Integer) tree[treeIndex] <= r) {
                    posList.add(treeIndex);
                }
                return;
            }
            //tree中父节点为treeIndex,的左右孩子的index
            int leftChildIndex = getLeftChild(treeIndex);
            int rightChildIndex = getRightChild(treeIndex);
            int mid = left + (right - left) / 2;//如果偶数左边右边一样多，如果是奇数，算到左边中
            //构造左右孩子节点
            queryCount(leftChildIndex, left, mid, l, r, posList);
            queryCount(rightChildIndex, mid + 1, right, l, r, posList);
            //根据左右孩子的值，通过合成器，决定父节点的值
            if ((Integer) tree[treeIndex] >= l && (Integer) tree[treeIndex] <= r) {
                posList.add(treeIndex);
            }
            tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
//            System.out.println("size>>" + tree[treeIndex]);
        }

    }
}
