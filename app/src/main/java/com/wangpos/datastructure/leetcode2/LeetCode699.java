package com.wangpos.datastructure.leetcode2;

import com.wangpos.datastructure.leetcode.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class LeetCode699 {

    public static void main(String args[]) {
        Solution solution = new Solution();

        int[][] positions = new int[][]{new int[]{9, 7}, new int[]{1, 9}, new int[]{3, 1}};
        List<Integer> resultList = solution.fallingSquares(positions);
        System.out.println(Arrays.toString(resultList.toArray()));
    }

    static class Solution {

        //X轴每个点对应高度
        HashMap<Integer, Integer> xAxisHeights = new HashMap<>();
        //每个点
        List<Integer> postionHeights = new ArrayList<>();
        //当前最大高度
        Integer currentMaxHeight = 0;

        TreeMap<Integer, Integer> lineRecords = new TreeMap<>();

        public List<Integer> fallingSquares(int[][] positions) {
            for (int[] position : positions) {
                addPosition(position);
            }
            return postionHeights;
        }

        private void addPosition(int[] position) {
            int length = position[0] + position[1];
            int increaseHeight = position[1];
            int coverMaxHeight = 0;
            for (int start = position[0]; start <= length; start++) {
                Integer oldHeight = xAxisHeights.get(start);
                if (oldHeight != null) {
                    if (oldHeight > coverMaxHeight) {
                        coverMaxHeight = oldHeight;
                    }
                }
            }
            int newMaxHeight = coverMaxHeight + increaseHeight;
            //去掉两个端点，假设第二块恰好挨着第一块落下端点的高度应该是原先值


            Integer leftKey = lineRecords.floorKey(position[0]);
            Integer leftValue = lineRecords.get(leftKey);
            if (position[0] < leftValue) {
                xAxisHeights.put(position[0], newMaxHeight);
            }

            Integer rightKey = lineRecords.higherKey(length);
            if (length != rightKey) {
                xAxisHeights.put(length, newMaxHeight);
            }


            for (int start = position[0] + 1; start < length; start++) {
                //判断开始和结束点是否落在边上，还是里面
                xAxisHeights.put(start, newMaxHeight);
            }
            if (newMaxHeight > currentMaxHeight) {
                currentMaxHeight = newMaxHeight;
            }

            //计算正方形以单位1的覆盖x 取出所有覆盖点，比较最大高度，最大高度的点值为加上新的点高度，其他点一并更新

            //更新每个点的高度，

            // 并与当前最高比较如果大于更新currentMaxHeight

            //向posinoHeights记录当前时间点的最大高度
//            int l = position[0];
//            int r = length;
//            if (position[0] < leftValue) {
//                xAxisHeights.put(leftKey, length);//合并左边
//                l = leftKey;
//            } else {
//                if(length)
//            }


            lineRecords.put(position[0], position[0] + position[1]);
            postionHeights.add(currentMaxHeight);
        }
    }
}
