package com.wangpos.datastructure.algorithm;

public class StringAnagram {

    public static void main(String[] args) {
        String targetString1 = "abcee";
        String targetString2 = "bcdea";

        boolean result = isDeformation(targetString1, targetString2);

        System.out.println("结果：" + result);

    }

    /**
     * 为什么要创建256个长度数组
     * <p>
     * ASCII编码表中一共有256个字符
     * <p>
     * 前128个为常用的字符 如 运算符 字母 数字等 键盘上可以显示的
     * 后 128个为 特殊字符 是键盘上找不到的字符
     *
     * // 为什么有小于0 就是false,
     *   //首先前提我们判断过是长度相等的
     *   //如果chas1 中种类多和chas1种类数量不一样，就一定会出现负数，
     *   //出现负数有两种，第一种是种类多的变为负数，第二种是没有的变为负数
     * @param targetString1
     * @param targetString2
     * @return
     */
    private static boolean isDeformation(String targetString1,
                                         String targetString2) {
        if (targetString1 == null || targetString2 == null
                || targetString1.length() != targetString2.length()
        ) {
            return false;
        }

        char[] chas1 = targetString1.toCharArray();
        char[] chars2 = targetString2.toCharArray();
        //
        int[] map = new int[256];

        for (char c : chas1) {
            map[c]++;
        }
        for (char c2 : chars2) {
            map[c2]--;
            if (map[c2] < 0) {
                return false;
            }
        }

        return true;
    }
}
