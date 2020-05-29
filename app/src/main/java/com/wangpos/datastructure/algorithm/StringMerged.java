package com.wangpos.datastructure.algorithm;

import java.util.HashSet;
import java.util.List;

public class StringMerged {

    public static int N = 262144;//9组数据
    private static String[] resultArray = new String[262144];
    private static int itemSize = 4;//每次最大4个
    public static int index = 0;
    private static HashSet<String> sets = new HashSet<>();

    public HashSet<String> findAllKindSplice(String str) {
        sets.clear();//每次都清理掉集合
        char[] chas = new char[itemSize];

        char[] strArray = str.toCharArray();
        for (int i = 0; i < chas.length; i++) {
            chas[i] = ' ';
            if (i < str.length()) {
                chas[i] = strArray[i];
            }
        }

        int length = itemSize;
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
                    } else {
//                        chasValue = '*';
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

            if (resultArray[i] != null && chasValue != ' ') {
                resultArray[i] = resultArray[i] + chasValue;
            } else {
                if (chasValue != ' ') {
                    resultArray[i] = "" + chasValue;
                } else {
                    resultArray[i] = "";
                }
            }
            //去掉多于元素，因为本次拼接长度一定比上次长
            if (resultArray[i].length() > index) {
                sets.add(resultArray[i]);
            }
        }

        index++;
//        return resultArray;
        return sets;
    }

    private static int getCount(int n, int index) {
        if (index == 0) {
            return n / itemSize;
        } else {
            return getCount(n / itemSize, --index);
        }
    }




}
