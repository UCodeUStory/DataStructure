package com.wangpos.datastructure.algorithm;

import java.util.Arrays;

public class DynamicMaxLongSubString {

    public static void main(String[] args) {

        String str1 = "1AB2345CD";

        String str2 = "12345EF";


        String maxLongsubSequence = getMaxLongSubString(str1, str2);

        System.out.println(maxLongsubSequence);
    }

    /**
     * 1 1 1 1 1 1 1
     * 1 1 1 1 1 1 1
     * 1 1 1 1 1 1 1
     * 1 1 1 1 1 1 1
     * 1 1 2 2 2 2 2
     * 1 1 2 3 3 3 3
     * 1 1 2 3 4 4 4
     * 1 1 2 3 4 4 4
     * 1 1 2 3 4 4 4
     * [2, 3, 4, 5]
     *
     * @param str1
     * @param str2
     * @return
     */
    private static String getMaxLongSubString(String str1, String str2) {


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
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (charArray1[i] == charArray2[j]
                        && charArray1[i - 1] == charArray2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        printArray(dp, str1.length(), str2.length());
        char resultArray[] = new char[dp[str1.length() - 1][str2.length() - 1]];
        int i = resultArray.length - 1;


        for (int j = str1.length() - 1; j > 0; j--) {
            int a = dp[j][str2.length() - 1];
            int b = dp[j - 1][str2.length() - 1];
            if (a > b) {
                resultArray[i--] = charArray1[j];
            }
            if (i == 0 && a <= b) {
                resultArray[i--] = charArray1[j];
            }
            if (i < 0) {
                break;
            } else {
                continue;
            }
        }


        return Arrays.toString(resultArray);
    }


    private static void printArray(int[][] m, int l1, int l2) {
        for (int i = 0; i < l1; i++) {
            for (int j = 0; j < l2; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}






