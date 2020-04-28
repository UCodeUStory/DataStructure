package com.wangpos.datastructure.algorithm;

import java.util.Arrays;

public class DynamicMaxLongStr {

    public static void main(String[] args) {

        int arr[] = new int[]{2, 1, 5, 3, 6, 4, 8, 9, 7};
        find(arr);
    }

    private static void find(int[] arr) {
        //存储第i个位置结尾，最长递增子串长度

        //根据最后一个dp 值向前遍历，找打小于他的一个值，并且dp[i] = dp[7]-1

        int dp[] = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
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
        for (int k = dp.length-1; k >= 0; k--) {
            if (dp[k] > max) {
                max = dp[k];
                maxIndex = k;
            }
        }

        System.out.println("max="+max +"maxIndex="+maxIndex);
        System.out.println(arr[maxIndex]);
        for (int m = maxIndex; m >= 0; m--) {
            if (arr[m] < arr[maxIndex] && dp[m] == dp[maxIndex] - 1) {
                maxIndex = m;
                System.out.println(arr[m]);
            }
        }
    }
}
