package com.wangpos.datastructure.algorithm;

public class StringMinShortDistance {

    public static void main(String args[]) {

        String[] strings = new String[]{"1", "3", "3", "3", "2", "3", "1"};
        System.out.println(minDistance(strings, "2", "1"));

    }

    public static int minDistance(String[] strs, String str1, String str2) {
        if (str1 == null || str2 == null) {
            return -1;
        }
        if (str1.equals(str2)) {
            return 0;
        }
        int last1 = -1;
        int last2 = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != strs.length; i++) {
            if (strs[i].equals(str1)) {
                if (last2 != -1) {
                    min = Math.min(min, i - last2);
                }
                last1 = i;
            }
            if (strs[i].equals(str2)) {
                if (last1 != -1) {
                    min = Math.min(min, i - last1);
                }
                last2 = i;
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    //如果实现时间复杂度O(1)，那么我们只能采用Hash表，因为两个参数，所以Value 还得用一个hash表
    //最后我们把这个表先生成就好了
}
