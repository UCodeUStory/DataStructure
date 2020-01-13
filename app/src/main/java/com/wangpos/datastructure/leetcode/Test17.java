package com.wangpos.datastructure.leetcode;

public class Test17 {

    public static void main() {
        int[] nums = new int[]{1, 2, 3, 4};
        int[] count = new Test17().decompressRLElist(nums);
        System.out.println(count.length);
    }

    public int[] decompressRLElist(int[] nums) {

        int count = 0;
        int index = 0;
        int[] resultArray;
        for (int i = 0; i < nums.length; i++) {
            // 0 1 2 3 4 5
            if ((i + 1) % 2 != 0) {
                count += nums[i];
            }
        }
        resultArray = new int[count];

        for (int i = 0; i < nums.length; i++) {
            if ((i + 1) % 2 != 0) {
                for (int j = 0; j < nums[i]; j++) {
                    resultArray[index] = nums[i + 1];
                    index++;
                }
            }

        }
        return resultArray;

    }

    public int[][] matrixBlockSum(int[][] mat, int K) {

        int[][] resultArray = new int[mat.length][mat[0].length];

        for (int i = 0; i < mat.length; i++) {
//i - K <= r <= i + K, j - K <= c <= j + K
//            for (int m = 0; m < mat[i].length; m++) {
                int aa = i-K;
                if(aa<0){
                    aa=0;
                }
                int sum = 0;
                for (int a = aa; a <= i + K && a < mat.length; a++) {
                    int bb = i-K;
                    if (bb<0){
                        bb = 0;
                    }
                    for (int b=bb; b <= i + K && b < mat[i].length ; b++) {
                        sum += mat[a][b];
                    }
                }
//                resultArray[i][m] = sum;
//            }
        }

        return resultArray;
    }
}
