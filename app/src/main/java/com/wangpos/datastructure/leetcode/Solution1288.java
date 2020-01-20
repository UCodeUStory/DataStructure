package com.wangpos.datastructure.leetcode;

import java.util.Arrays;
import java.util.Comparator;

class Solution1288 {

    /**
     * 按区间开始排序如下，遍历的时候，只需要记录已遍历区间中的结束的最大值，只要后面的区间结束小于等于最大值，
     * 就KO掉。区间开始一样只要保留一个即可。
     *
     * 因为开始值我们肯定都是最小的
     * @param intervals
     * @return
     */
    public int removeCoveredIntervals(int[][] intervals) {
        int max2 = 0;
        int min1 = 0;
        sort(intervals, new int[] {0,1});

        //保留已遍历的最大的,只要自己2小了，就被删除
        int len = intervals.length;
        for (int i = 0; i < intervals.length; i++) {
            if(i-1 >= 0 && intervals[i-1][0] == intervals[i][0] && intervals[i-1][1] >=intervals[i][1]){
                len--;
                continue;
            }
            else if(intervals[i][1] <= max2){
                len--;
                continue;
            }else if (intervals[i][1] > max2){
                max2 = intervals[i][1];
            }
        }
        return len;
    }

    public static void sort(int[][] ob, final int[] order) {
        Arrays.sort(ob, new Comparator<Object>() {
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

