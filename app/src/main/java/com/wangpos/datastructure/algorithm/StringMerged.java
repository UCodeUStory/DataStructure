package com.wangpos.datastructure.algorithm;

import java.util.List;

public class StringMerged {

    public static int N = 16;//9个数据
    public static String[] resultArray = new String[16];

    public static int itemSize = 4;

    public static void main(String[] args) {

        String str = "ABC";

        String str2 = "EFGH";
        System.out.println("startTime " + System.currentTimeMillis());
        findAllKindSplice(str);
        System.out.println("endTime " + System.currentTimeMillis());
//        findAllKindSplice(str2);
//        findAllKindSplice(str3);
//        findAllKindSplice(str4);
        for (String s : resultArray) {
            System.out.print(s + " ");
        }
//
//        System.out.println(getCount(27, 0));
//        System.out.println(getCount(27, 1));
//        System.out.println(getCount(27, 2));

    }

    public static int index = 0;

    private static String[] findAllKindSplice(String str) {
        char[] chas = new char[itemSize];

        char[] strArray = str.toCharArray();
        for (int i = 0; i < chas.length; i++) {
            chas[i] = ' ';
            if (i < str.length() - 1) {
                chas[i] = strArray[i];
            }
        }

        int length = str.length();
        for (int i = 0; i < resultArray.length; i++) {
            char chasValue = ' ';
            if (index == 0) {
                if (i < N / length) {
                    chasValue = chas[0];
                } else if (i < N / length * 2) {
                    chasValue = chas[1];
                } else if (i < N / length * 3) {
                    chasValue = chas[2];
                } else if (i < N) {
                    if (chas[3] != ' ') {
                        chasValue = chas[3];
                    }
                }
            } else {
                if (i % getCount(N, index - 1) < getCount(N, index)) {//0~3
                    chasValue = chas[0];
                } else if (i % getCount(N, index - 1) < getCount(N, index) * 2) {
                    chasValue = chas[1];
                } else if (i % getCount(N, index - 1) < getCount(N, index) * 3) {
                    chasValue = chas[2];
                } else if (i % getCount(N, index - 1) < getCount(N, index) * 4) {
                    if (chas[3] != ' ') {
                        chasValue = chas[3];
                    }
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
        if (index == 0) {
            return n / itemSize;
        } else {
            return getCount(n / itemSize, --index);
        }
    }

    /**
     * 如果上一个结果为空
     * 并且当前还是以上一个结果
     */
    public void search() {

    }


}
