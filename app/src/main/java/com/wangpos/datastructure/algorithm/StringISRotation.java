package com.wangpos.datastructure.algorithm;

public class StringISRotation {

    public static void main(String[] args) {

        String a = "abcd1";
        String b = "1abcd";

        boolean result = isRotation(a, b);

        System.out.println("结果：" + result);
    }

    private static boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        String b2 = b + b;
        return  StringKMP.getIndexOf(b2,a)!=-1;
    }

    //KMP算法求是否包含子串 ，时间复杂度O(n+p)
    private static int getIndexOf(String s, String m) {

        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }

        char[] ss = s.toCharArray();
        char[] ms = m.toCharArray();
        int si = 0;
        int mi = 0;
//        int []next = getNextArray(ms);
        return 0;

    }
}
