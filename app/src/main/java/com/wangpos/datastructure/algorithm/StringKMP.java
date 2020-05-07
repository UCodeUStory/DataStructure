package com.wangpos.datastructure.algorithm;

public class StringKMP {

    public static void main(String[] args) {
        String str1 = "ababacdcdefcdcag";
        String str2 = "cdca";

        System.out.println(getIndexOf(str1, str2));
    }

    static int getIndexOf(String source, String target) {
        return kmp(source.toCharArray(), target.toCharArray());
    }

    static int kmp(char[] s, char[] p) {
        int nextLength = Math.min(s.length, p.length);
        int i = 0;
        int j = 0;
        int[] next = new int[nextLength];
        getNextVal(p, next);
        int sLen = s.length;
        int pLen = p.length;
        while (i < sLen && j < pLen) {
            //①如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++
            if (j == -1 || s[i] == p[j]) {
                i++;//等于-1 和匹配成功都++,为什么-1还++,表示前面还没有匹配成功
                j++;
            } else {
                //②如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]
                //next[j]即为j所对应的next值
                j = next[j];
            }
        }
        if (j == pLen)
            return i - j;  //返回p在S中的位置
        else
            return -1;
    }


    /**
     * 获取K值数组
     *
     * @param p
     * @param next
     *
     */
    static void getNextVal(char[] p, int next[]) {
        int pLen = p.length;
        next[0] = -1;
        int k = -1;
        int j = 0;
        while (j < pLen-1) {
            //p[k]表示前缀，p[j]表示后缀
            if (k == -1 || p[j] == p[k]) {
                j++;
                k++;
//                next[j] = k;

                if (p[j] != p[k])
                    next[j] = k;   //之前只有这一行
                else
                    //因为不能出现p[j] = p[ next[j ]]，所以当出现时需要继续递归，k = next[k] = next[next[k]]
                    next[j] = next[k];

            } else {
                k = next[k];

            }
        }

    }
}
