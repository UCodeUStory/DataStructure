package com.wangpos.datastructure.leetcode;

import java.util.Arrays;

public class Test18 {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(Arrays.toString(new Test18().getNoZeroIntegers(1010)));
    }

    public int[] getNoZeroIntegers(int n) {

        for (int a = 1; a < n; a++) {
            int c = n - a;
            String as = a+"";
            if(as.contains("0")){
                continue;
            }
            String s = c+"";
            if(s.contains("0")){
                continue;
            }
            int result[] = new int[2];
            result[0] = a;
            result[1] = c;
            return result;
        }
        return new int[0];
    }


}
