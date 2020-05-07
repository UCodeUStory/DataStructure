package com.wangpos.datastructure.algorithm;

import java.util.Arrays;
import java.util.Comparator;

public class DynamicMaxLongSubSequence {

    public static void main(String[] args) {

        String str1 = "1A2C3D4B56";

        String str2 = "B1D23CA45B6A";


        String maxLongsubSequence = getMaxLongSubSequence(str1, str2);

        System.out.println(maxLongsubSequence);
    }

    private static String getMaxLongSubSequence(String str1, String str2) {


        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        int[][] dp = new int[str1.length()][str2.length()];

        dp[0][0] = (charArray1[0] == charArray2[0] ? 1 : 0);

        //填充第一行
        for (int i = 1; i < str1.length(); i++) {
            int count = (charArray1[i] == charArray2[0] ? 1 : 0);
            dp[i][0] = Math.max(dp[i - 1][0], count);
        }
        //填充第一列
        for (int j = 1; j < str2.length(); j++) {
            int count = (charArray1[0] == charArray2[j] ? 1 : 0);
            dp[0][j] = Math.max(dp[0][j - 1], count);
        }

        for (int i = 1; i < str1.length(); i++) {
            for (int j = 1; j < str2.length(); j++) {
                //去除两边最大值
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (charArray1[i] == charArray2[j]) {
                    //相等 并不是直接增加1 ,因为前面有可能增加过，所以要和前一行前一列的值+1比较
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }

        int m = str1.length() - 1;
        int n = str2.length() - 1;
        //获取子序列
        char[] res = new char[dp[str1.length() - 1][str2.length() - 1]];

        int index = res.length - 1;
        while (index >= 0) {
            if (n > 0 && dp[m][n] == dp[m][n - 1]) {
                n--;
            } else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
                m--;
            } else {
                res[index--] = charArray1[m];
                m--;
                n--;
            }
        }
        return Arrays.toString(res);
    }

}






