package com.wangpos.datastructure.algorithm;

import java.util.Arrays;

public class StringReverse {


    public static void main(String[] args) {

        String str = "I love you";

        String result = getReverse2(str);
        System.out.println(result);

    }

    //通过char 数组事项
    private static String getReverse(String str) {
        char[] chas = str.toCharArray();
        int start = 0;
        int end = chas.length - 1;
        /**
         * 实现思路 先全部反转，然后再局部反转
         */
        reverse(chas, start, end);

        int l = -1;
        int r = -1;
        for (int i = 0; i < chas.length; i++) {
            if (chas[i] != ' ') {
                //找到l
                if (i == 0 || chas[i - 1] == ' ') {
                    l = i;
                }
                //找到每一个r
                if (i == chas.length - 1 || chas[i + 1] == ' ') {
                    r = i;
                }
            }
            if (l != -1 && r != -1) {
                reverse(chas, l, r);
                l = -1;
                r = -1;
            }
        }

        return Arrays.toString(chas);
    }

    //通过String 数组也可以实现，但是会产生临时对象
    private static String getReverse2(String str) {
        String strArray[] = str.split(" ");

        int start = 0;
        int end = strArray.length - 1;
        String tmp = "";
        while (start < end) {
            tmp = strArray[start];
            strArray[start] = strArray[end];
            strArray[end] = tmp;
            start++;
            end--;
        }

        String result = "";//StringBuilder
        for (int i = 0; i < strArray.length; i++) {
            if (i == strArray.length - 1) {
                result = result+strArray[i];
            } else {
                result = result + strArray[i] + " ";
            }

        }
        return result;
    }


    public static void reverse(char[] chas, int start, int end) {
        char tmp = 0;
        while (start < end) {
            tmp = chas[start];
            chas[start] = chas[end];
            chas[end] = tmp;
            start++;
            end--;
        }
    }

}
