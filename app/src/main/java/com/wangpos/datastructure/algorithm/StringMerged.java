package com.wangpos.datastructure.algorithm;

import java.util.List;

public class StringMerged {

    public static int N = 27;
    public static String[] resultArray = new String[27];

    public static void main(String[] args) {
        String str = "ABC";

        String str2 = "DEF";

        String str3 = "GHI";

        findAllKind(str);
        findAllKind(str2);
//        findAllKind(str3);
        for (String s : resultArray) {
            System.out.print(s + " ");
        }
//
//        System.out.println(getCount(27, 0));
//        System.out.println(getCount(27, 1));
//        System.out.println(getCount(27, 2));

    }

    public static int index = 0;

    public static int lastLength = 27;

    private static String[] findAllKind(String str) {
        char[] chas = str.toCharArray();
        for (int i = 0; i < resultArray.length; i++) {
            char chasValue = ' ';
            if (index == 0) {
                lastLength = 9;
                if (i < N / 3) {
                    chasValue = chas[0];
                } else if (i < N / 3 * 2) {
                    chasValue = chas[1];
                } else if (i < N) {
                    chasValue = chas[2];
                }
            } else {
                if (i % getCount(N, index - 1) < getCount(N, index)) {//0~3
                    chasValue = chas[0];
                } else if (i < getCount(N, index) * 2) {
                    chasValue = chas[1];
                }
            }

            if (resultArray[i] != null) {
                resultArray[i] = resultArray[i] + chasValue;
            } else {
                resultArray[i] = "" + chasValue;
            }
        }

        index++;
        return resultArray;
    }

    private static int getCount(int n, int index) {
        int result = n;
        if (index == 0) {
            return result = n / 3;
        } else {
            return getCount(n / 3, --index);
        }
    }


}
