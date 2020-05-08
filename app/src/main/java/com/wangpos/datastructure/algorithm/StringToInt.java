package com.wangpos.datastructure.algorithm;

public class StringToInt {

    public static void main(String[] args) {

    }

    /**
     * 检测是否是数字字符
     * @param chas
     * @return
     */
    public boolean isValid(char[] chas) {
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

        for (int i = 1; i < chas.length; i++) {
            if (chas[i] < '0' || chas[i] > '9') {
                return false;
            }
        }
        return true;
    }
}
