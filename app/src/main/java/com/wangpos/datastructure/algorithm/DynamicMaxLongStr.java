package com.wangpos.datastructure.algorithm;

import java.util.Arrays;

public class DynamicMaxLongStr {

    public static void main(String[] args) {

        int arr[] = new int[]{2, 1, 5, 3, 6, 4, 8, 9, 7};
//        find(arr);
        /**
         * [1, 1, 2, 2, 3, 3, 4, 5, 4]
         * max=5maxIndex=7
         * 9
         * 8
         * 4
         * 3
         * 1
         */
        findTwoSplit(arr);
    }

    // O N*N
    private static void find(int[] arr) {
        //存储第i个位置结尾，最长递增子串长度

        //根据最后一个dp 值向前遍历，找打小于他的一个值，并且dp[i] = dp[7]-1

        int dp[] = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                //与前面每一个比较如果大于就比较一下对应dp值是否是最大的赋值给当前
                //只要比当前的大就要比较一下，以当前值为子串的最大长度
                if (arr[i] > arr[j]) {
                    //这个公式你要品，你细品
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        System.out.println(Arrays.toString(dp));

        int max = 0;
        int maxIndex = 0;
        //找到最大值
        for (int k = dp.length - 1; k >= 0; k--) {
            if (dp[k] > max) {
                max = dp[k];
                maxIndex = k;
            }
        }

        System.out.println("max=" + max + "maxIndex=" + maxIndex);
        System.out.println(arr[maxIndex]);
        for (int m = maxIndex; m >= 0; m--) {
            if (arr[m] < arr[maxIndex] && dp[m] == dp[maxIndex] - 1) {
                maxIndex = m;
                System.out.println(arr[m]);
            }
        }
    }

    // O N*N; 通过二分查找优化成Ologn

    /**
     * 通过递增子串的规则，我们发现
     * <p>
     * 每次我们都找一个最小
     *
     * @param arr
     */
    private static void findTwoSplit(int[] arr) {
        //存储第i个位置结尾，最长递增子串长度

        //根据最后一个dp 值向前遍历，找打小于他的一个值，并且dp[i] = dp[7]-1

        int dp[] = new int[arr.length];
        //有效值
        int ends[] = new int[arr.length];
        ends[0] = arr[0];
        dp[0] = 1;
        // 0 right有效区
        int right = 0;
        for (int i = 1; i < arr.length; i++) {
            int l = 0;
            int r = right;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (arr[i] > ends[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            if (l > right) {//有效区扩张
                right = l;
            }
            ends[l] = arr[i];
            dp[i] = l + 1;

//            for (int j = 0; j < i; j++) {
//                //与前面每一个比较如果大于就比较一下对应dp值是否是最大的赋值给当前
//                if (arr[i] > arr[j]) {
//                    //这个公式你要品，你细品
//                    dp[i] = Math.max(dp[i], dp[j] + 1);
//                }
//            }
        }

        System.out.println(Arrays.toString(dp));

        int max = 0;
        int maxIndex = 0;
        //找到最大值
        for (int k = dp.length - 1; k >= 0; k--) {
            if (dp[k] > max) {
                max = dp[k];
                maxIndex = k;
            }
        }

        System.out.println("max=" + max + "maxIndex=" + maxIndex);
        System.out.println(arr[maxIndex]);
        for (int m = maxIndex; m >= 0; m--) {
            if (arr[m] < arr[maxIndex] && dp[m] == dp[maxIndex] - 1) {
                maxIndex = m;
                System.out.println(arr[m]);
            }
        }
    }
}
