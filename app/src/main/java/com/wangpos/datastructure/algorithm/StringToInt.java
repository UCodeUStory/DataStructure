package com.wangpos.datastructure.algorithm;

public class StringToInt {

    public static void main(String[] args) {

        String str = "-123";
        String str2 = "a2342";
        String str3 = "02342";
        int result = convert(str);

        System.out.println(result);
    }

    /**
     * 检测是否是数字字符
     *
     * @param chas
     * @return
     */
    public static boolean isValid(char[] chas) {
        //判断第一位是0到9的之外的数，而不是负号
        if (chas[0] != '-' && (chas[0] < '0' || chas[0] > '9')) {
            return false;
        }
        //如果第一位为负号，后面的不可以是0 也不能没有值
        if (chas[0] == '-' && (chas.length == 1 || chas[1] == '0')) {
            return false;
        }
        //第一位是0 但是长度大于1 false
        if (chas[0] == '0' && chas.length > 1) {
            return false;
        }

        //第二位以后是0 到9的数
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] < '0' || chas[i] > '9') {
                return false;
            }
        }
        return true;
    }

    public static int convert(String str) {
        if (str == null || str.equals("")) {
            return 0;//不能转
        }
        char[] chas = str.toCharArray();
        if (!isValid(chas)) {
            return 0;//不符合要求
        }

        //判断正负数
        boolean posi = chas[0] != '-';
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MAX_VALUE % 10;
        int res = 0;
        int cur = 0;
        //-2147483648 2147483647
        //
        //左程云. 程序员代码面试指南IT名企算法与数据结构题目最优解（第2版） (Chinese Edition) (Kindle位置3654). Kindle 版本.
        //
        //左程云. 程序员代码面试指南IT名企算法与数据结构题目最优解（第2版） (Chinese Edition) (Kindle位置3654). Kindle 版本.
        //以负数进行计算 比如
        // 123   第一次 -1  res = 0*10 -1 =-1 小于12
        // 第二次 res = -1*10 -2 = -12 小于12 如果大于直接溢出，不管下一位是什么
        // 第三次 res = -12*10 -3 = -123  如果等于 就比较最后一个值
        for (int i = posi ? 0 : 1; i < chas.length; i++) {
            cur = '0' - chas[i];//当前字符所代表的负数形式
            if ((res < minq) || (res == minq && cur < minr)) {
                return 0;
            }
            res = res * 10 + cur;

        }
        //判断正数最大值
        if (posi && res == Integer.MIN_VALUE) {
            return 0;//不能转
        }
        return posi ? -res : res;
    }
}
