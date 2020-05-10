package com.wangpos.datastructure.algorithm;

public class StringToStatisticsString {


    public static void main(String[] args) {

        String target  = "aaabbbbbccc3333cccdee";

        String result = getCountString(target);

        System.out.println(result);

    }

    public static String getCountString(String str) {

        char[] chs = str.toCharArray();
        String res = String.valueOf(chs[0]);

        int num = 1;

        for (int i = 1; i < chs.length; i++) {
            if (chs[i] != chs[i - 1]) {
                res = contact(res,String.valueOf(num),String.valueOf(chs[i]));
                num = 1;
            }else{
                num++;
            }
        }
        return contact(res,String.valueOf(num),"");
    }

    //统计字符串用_分割开，可以区分里面含数字的情况
    public static String contact(String s1,String s2,String s3){
        return s1+"_"+s2+(s3.equals("")?s3:"_"+s3);
    }

}
