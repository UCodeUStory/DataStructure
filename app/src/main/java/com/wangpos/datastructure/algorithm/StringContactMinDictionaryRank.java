package com.wangpos.datastructure.algorithm;

import java.util.Arrays;
import java.util.Comparator;


/**
 * 排序比较就可以了，重要的是排序条件
 */
public class StringContactMinDictionaryRank {

    public static void main(String[] args) {
        String[] strArray = new String[]{"abc", "bcd", "acd"};

        System.out.println(lowString(strArray));

    }

    public static class MyComparator implements Comparator<String>{
        @Override
        public int compare(String o1, String o2) {
            return (o1+02).compareTo(o2+o1);
        }
    }
    public static String lowString(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        Arrays.sort(strs, new MyComparator());
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }
        return res;
    }
}
