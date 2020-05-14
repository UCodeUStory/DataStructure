package com.wangpos.datastructure.algorithm;

public class StringReplace {

    public static void main(String[] args) {


    }

    public static void replace(char[] chas) {
        if (chas == null || chas.length == 0) {
            return;
        }
        int num = 0;
        int len = 0;
        for (len = 0; len < chas.length && chas[len] != 0; len++) {
            if (chas[len] == ' ') {
                num++;
            }
        }

        int j = len + num * 2 - 1;
        for (int i = len - 1; i > -1; i--) {
            if (chas[i] != ' ') {
                chas[j--] = chas[i];
            } else {
                chas[j--] = '0';
                chas[j--] = '2';
                chas[j--] = '%';
            }
        }
    }

    /**
     * 有字符串的包含数组和* ,现在把星移动到所有数字前面
     * @param chas
     */
    public void modify(char[]chas){

        if(chas ==null ||chas.length==0){
            return;
        }
        int j = chas.length-1;
        for(int i= chas.length-1;i>-1;i--){
            if(chas[i]!='*'){
                chas[j--] = chas[i];
            }
        }
        for(;j>-1;){
            chas[j--] = '*';
        }
    }
}
