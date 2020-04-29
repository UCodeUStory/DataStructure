package com.wangpos.datastructure.algorithm;

import java.util.Arrays;
import java.util.Comparator;

public class DynamicNestLetter {

    public static void main(String[] args) {
        int arr[][] = new int[][]{{3, 4}, {2, 3}, {4, 5}, {1, 3}, {2, 2}, {3, 6}, {1, 2}, {3, 2}, {2, 4}};
   //     int arr[][] = new int[][]{{3, 4}, {2, 3}, {4, 5}, {1, 3}, {2, 2}, {3, 6}};

        int result = calculateTwoSplit(getSortedEnvelopes(arr));
        System.out.println(result);
    }

    /**
     * 将数组按照按照长度排序，然后求宽度的最长增长子串
     *
     * @param arr
     * @return
     */
    private static int calculateTwoSplit(Envelope[] arr) {

        //存储第i个位置结尾，最长递增子串长度

        //根据最后一个dp 值向前遍历，找打小于他的一个值，并且dp[i] = dp[7]-1

        int dp[] = new int[arr.length];
        //有效值
        int ends[] = new int[arr.length];
        ends[0] = arr[0].wid;
        dp[0] = 1;
        // 0 right有效区
        int right = 0;
        for (int i = 1; i < arr.length; i++) {
            int l = 0;
            int r = right;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (arr[i].wid > ends[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            if (l > right) {//有效区扩张
                right = l;
            }
            ends[l] = arr[i].wid;
            dp[i] = l + 1;

        }

        return right + 1;

    }

    //这个排序数组，我们长度是按小到大，所以只需要看宽度的递增子序列即可
    public static Envelope[] getSortedEnvelopes(int[][] matrix) {
        Envelope[] res = new Envelope[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = new Envelope(matrix[i][0], matrix[i][1]);
        }
        Arrays.sort(res, new EnvelopeComparator());
        return res;
    }

}

class Envelope {
    public int len;
    public int wid;

    public Envelope(int weight, int hight) {
        len = weight;
        wid = hight;
    }
}

class EnvelopeComparator implements Comparator<Envelope> {
    @Override
    public int compare(Envelope o1, Envelope o2) {
        return o1.len != o2.len ? o1.len - o2.len : o2.wid - o1.wid;
    }
}





