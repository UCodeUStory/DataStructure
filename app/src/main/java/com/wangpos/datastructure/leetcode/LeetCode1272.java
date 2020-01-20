package com.wangpos.datastructure.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 给你一个 有序的 不相交区间列表 intervals 和一个要删除的区间 toBeRemoved， intervals 中的每一个区间 intervals[i] = [a, b] 都表示满足 a <= x < b 的所有实数  x 的集合。
 *
 * 我们将 intervals 中任意区间与 toBeRemoved 有交集的部分都删除。
 *
 * 返回删除所有交集区间后， intervals 剩余部分的 有序 列表。
 *
 *
 * 示例 1：
 *
 * 输入：intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6]
 * 输出：[[0,1],[6,7]]
 * 示例 2：
 *
 * 输入：intervals = [[0,5]], toBeRemoved = [2,3]
 * 输出：[[0,2],[3,5]]
 *
 */
public class LeetCode1272 {

    public static void main(String[] args) {
        LeetCode1272 testObject = new LeetCode1272();
//        int[][] nums = new int[][]{new int[]{3, 4}, new int[]{0, 3}, new int[]{0, 2}, new int[]{5, 7}};
//        int[] toBeRemoved = new int[]{1, 6};

//        int[][] nums = new int[][]{new int[]{0, 5}};
//        int[] toBeRemoved = new int[]{2, 3};

//        int[][] nums = new int[][]{new int[]{-5, -4}, new int[]{-3, -2}, new int[]{1, 2}, new int[]{3, 5},new int[]{8,9}};
//        int[] toBeRemoved = new int[]{-1, 4};

        int[][] nums = new int[][]{new int[]{0, 100}};
        int[] toBeRemoved = new int[]{0, 50};
        testObject.removeInterval(nums, toBeRemoved);
    }

    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {

        //排序分别比较两个条件，按顺序比较
        sortArray(intervals, new int[]{0, 1});

        /**
         *
         *  0 2  0 3   3   4   5  7
         * 1 6
         *
         * 0 1  6 7
         *
         */


        List<List<Integer>> result = new ArrayList<>();
        int[] lastSaveArray = null;
        for (int[] interval : intervals) {
            if (interval[0] <= toBeRemoved[0]) {
                if (interval[1] > toBeRemoved[0]) {
                    //修改 入队列

                    int backupDataRight = interval[1];
                    if (interval[0] != toBeRemoved[0]) {
                        interval[1] = toBeRemoved[0];

                        if (lastSaveArray == null) {
                            lastSaveArray = saveResult(result, interval);
                        } else if (lastSaveArray[0] == interval[0] && lastSaveArray[1] == interval[1]) {
                            continue;
                        } else {
                            lastSaveArray = saveResult(result, interval);
                        }

                    }

                    if (backupDataRight >= toBeRemoved[1]) {
                        int newArray[] = new int[]{toBeRemoved[1], backupDataRight};
                        lastSaveArray = saveResult(result, newArray);
                    }

                } else {
                    lastSaveArray = saveResult(result, interval);
                }
            } else {
                //  8 9 -1 4
                //
                if (interval[1] <= toBeRemoved[1]) {
                    // 8 9   1  10
                    //重复空间
//                    lastSaveArray = saveResult(result, interval);
                } else {
                    if (interval[0] <= toBeRemoved[1]) {
                        interval[0] = toBeRemoved[1];
                    }
                    lastSaveArray = saveResult(result, interval);
                }
            }
        }


        return result;
    }

    private int[] saveResult(List<List<Integer>> result, int[] interval) {
        int[] lastSaveArray;
        lastSaveArray = interval;
        List<Integer> cell = new ArrayList<Integer>();
        cell.add(interval[0]);
        cell.add(interval[1]);
        result.add(cell);
        return lastSaveArray;
    }

    private void sortArray(int[][] intervals, final int[] order) {
        Arrays.sort(intervals, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                int[] one = (int[]) o1;
                int[] two = (int[]) o2;

                for (int i = 0; i < order.length; i++) {
                    int k = order[i];
                    if (one[k] > two[k]) {
                        return 1;
                    } else if (one[k] < two[k]) {
                        return -1;
                    } else {
                        continue;  //如果按一条件比较结果相等，就使用第二个条件进行比较。
                    }
                }
                return 0;
            }
        });
    }
}
