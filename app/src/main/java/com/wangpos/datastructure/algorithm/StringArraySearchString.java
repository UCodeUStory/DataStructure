package com.wangpos.datastructure.algorithm;

public class StringArraySearchString {

    public static void main(String args[]) {
        String str = "a";

        String[] strArrays = new String[]{null, "a", null, "a", null, "b", null, "c"};

        System.out.println(getIndex(strArrays, str));
    }

    //二分法查找，
    private static int getIndex(String[] strs, String str) {
        if (strs == null || strs.length == 0 || str == null) {
            return -1;
        }
        int res = -1;
        int left = 0;
        int right = strs.length - 1;
        int mid = 0;
        int i = 0;

        while (left < right) {
            mid = (left + right) / 2;
            if (strs[mid] != null && strs[mid].equals(str)) {
                res = mid;//返回最左边,所以要继续遍历
                right = mid - 1;
            } else if (strs[mid] != null) {
                if (strs[mid].compareTo(str) < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                i = mid;
                while (strs[i] == null && --i >= left) ;

                if (i < left || strs[i].compareTo(str) < 0) {
                    left = mid + 1;
                } else {
                    res = strs[i].equals(str) ? i : res;
                    right = i - 1;
                }
            }
        }


        return res;
    }
}
