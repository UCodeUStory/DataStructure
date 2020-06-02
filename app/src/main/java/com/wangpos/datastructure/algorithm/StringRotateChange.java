package com.wangpos.datastructure.algorithm;


/**
 * 旋转字符串
 * <p>
 * <p>
 */
public class StringRotateChange {

    public static void main(String[] args) {

        String s1 = "abcd";
        String s2 = "dbac";//true

        System.out.println("是否是旋转串=" + isRotateString(s1, s2));

        String s3 = "abcd";
        String s4 = "cadb";//false

        System.out.println("是否是旋转串=" + isRotateString(s3, s4));
    }

    public static boolean isRotateString(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0 || s1.length() != s2.length()) {
            return false;
        }
        if (s1 == s2) {
            return true;
        }

        char[] chas1 = s1.toCharArray();
        char[] chas2 = s2.toCharArray();

        /**
         * 从上向下比较
         */
        int start = 0;
        int end = chas2.length-1;
        for (int i = chas1.length-1; i >0; i--) {
            if (chas1[i] != chas2[end]) {
                if (chas1[i] != chas2[start]) {
                    return false;
                } else {
                    start++;
                }
            }
        }

        return true;
    }
}
