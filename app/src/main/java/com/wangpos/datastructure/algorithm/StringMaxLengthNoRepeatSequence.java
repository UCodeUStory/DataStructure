package com.wangpos.datastructure.algorithm;

public class StringMaxLengthNoRepeatSequence {

    public static void main(String[] args) {
        String str = "abcdaef";
        String str2 = "abcdaefd";
        System.out.println(getMaxLengthNoRepeatSequence(str2));

    }

    /**
     *  分析
     *  abcda
     *  当我们编译如果不之前没有出现过就算在内，如果出现过就保存当前不重复长度，
     *  然后将启示位置跳转到重复元素第一次出现的地方，比如重复元素默认位置是-1
     *  当array[4]位置又出现a ，判断曾经出现过，
     *  我们保存元素可以使用一个256数组即可，数组里面对应位置保存的是对应位置
     *
     * @param s
     * @return
     */
    public static int getMaxLengthNoRepeatSequence(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] chas = s.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int len = 0;//最长长度
        int pre = -1;//保存前一个位置
        int cur = 0;//保存当前位置到前一个重复位置的长度
        for (int i = 0; i != chas.length; i++) {
            pre = Math.max(pre, map[chas[i]]);//重复字符串的头部，如果map[chas[i]]有值，证明就是重复的
            cur = i - pre;
            len = Math.max(len, cur);
            map[chas[i]] = i;
        }
        return len;
    }

}
