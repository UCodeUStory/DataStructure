package com.wangpos.datastructure.leetcode2;

import java.util.TreeMap;

public class LeetCode352 {


    public static void main(String args[]) {
        SummaryRanges summaryRanges = new SummaryRanges();
        summaryRanges.addNum(6);
        summaryRanges.addNum(6);
        summaryRanges.addNum(0);
        summaryRanges.addNum(4);
        summaryRanges.addNum(8);
        summaryRanges.addNum(7);
        summaryRanges.addNum(6);
        summaryRanges.addNum(4);
        summaryRanges.addNum(7);
        summaryRanges.addNum(5);

        int[][] resultArray = summaryRanges.getIntervals();
        for (int[] ints : resultArray) {
            System.out.print(ints[0]);
            System.out.print(" - ");
            System.out.println(ints[1]);
        }

    }

    static class SummaryRanges {

        /**
         * Initialize your data structure here.
         */

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        public SummaryRanges() {

        }

        public void addNum(int data) {
            Integer leftKey = treeMap.floorKey(data);
//            System.out.println("左边挨着的Key" + leftKey);
            Integer rightKey = treeMap.higherKey(data);
//            System.out.println("右边边挨着的Key" + leftKey);
            updateData(data, leftKey, rightKey);

        }

        private void updateData(Integer data, Integer leftKey, Integer rightKey) {
            boolean leftIsUpdate = false;
            boolean rightIsUpdate = false;
            if (leftKey != null) {
                leftIsUpdate = checkUpdateLeftData(data, leftKey);
            }

            if (rightKey != null) {
                rightIsUpdate = checkUpdateRightData(data, rightKey);
            }

            //有更新去处理合并
            if (leftIsUpdate || rightIsUpdate) {
                //前后都有值
                if (leftKey != null && rightKey != null) {
                    checkMerge(leftKey, rightKey);
                }
            }

            //都没更新就加入新的
            if (!leftIsUpdate && !rightIsUpdate) {
                addNewData(data);
            }
        }

        private void checkMerge(Integer leftKey, Integer rightKey) {
            Integer leftValue = treeMap.get(leftKey);
            Integer rightValue = null;
            //相同证明不存在需要合并的right
            if (leftKey != leftValue) {
                rightValue = treeMap.get(leftValue);
            }
            if (rightValue != null) {
                merge(leftKey, leftValue, rightValue);
            }
        }

        private void addNewData(Integer data) {
            treeMap.put(data, data);
        }

        private void merge(Integer leftKey, Integer rightKey, Integer rightValue) {
            treeMap.put(leftKey, rightValue);
            treeMap.remove(rightKey);
        }

        private boolean checkUpdateRightData(Integer data, Integer rightKey) {
            Integer rightValue = treeMap.get(rightKey);
//            System.out.println("右边挨着的值" + rightValue);

            if (data == rightKey) {
                return true;
            } else if ((data + 1) == rightKey) {
                treeMap.put(data, rightValue);
                treeMap.remove(rightKey);
                return true;
            } else {
                return false;
            }

        }

        private boolean checkUpdateLeftData(int data, Integer leftKey) {
            Integer leftValue = treeMap.get(leftKey);
//            System.out.println("左边挨着的值" + leftValue);
            if (data <= leftValue) {
                return true;
            } else {
                if (data == leftValue + 1) {
                    treeMap.put(leftKey, data);
                    return true;
                } else {
                    return false;
                }
            }
        }

        public int[][] getIntervals() {

            int arrays[][] = new int[treeMap.size()][2];
            int index = 0;
            for (Integer integer : treeMap.keySet()) {
                arrays[index][0] = integer;
                arrays[index][1] = treeMap.get(integer);
                index++;
            }
            return arrays;
        }
    }
}
