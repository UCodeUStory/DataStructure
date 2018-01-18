package com.wangpos.datastructure.core;

import android.os.AsyncTask;

/**
 * Created by qiyue on 2018/1/18.
 */

public class AlignText {

    private int maxLength;

    private boolean addSpace = false;

    private AlignText(int maxLength,boolean space) {
        this.maxLength = maxLength;
        this.addSpace = space;
    }

    public static AlignText as(int maxLength){
        return new AlignText(maxLength,false);
    }

    public static AlignText asAddSpace(int maxLength){
        return new AlignText(maxLength,true);
    }

    public String alignLeft(String text){
        if (maxLength - maxLength<=0){
            return text;
        }
        int length = text.length();
        if (addSpace){
            text = " "+text;
        }
        text = left(text, length);
        if (addSpace){
            text = text + " ";
        }
        return text;
    }



    private String alignRight(String text){
        if (maxLength - maxLength<=0){
            return text;
        }
        int length = text.length();
        String space = "";
        if (addSpace){
            text = " "+text;
        }
        space = left(space, length);
        text = space + text;

        if (addSpace){
            text = text + " ";
        }
        return text;

    }

    private String alignCenter(String text){

        if (maxLength - maxLength<=0){
            return text;
        }
        int length = text.length();
        String space = "";


        if (addSpace){
            text = " "+text;
        }
        for (int i = 0;i<(maxLength-length)/2;i++){
            space += " " ;
        }

        text = space + text;
        if (addSpace){
            text = text + " ";
        }
        return text;

    }

    private static String alignCenter(String text,int maxLength){

        if (maxLength - maxLength<=0){
            return text;
        }
        int length = text.length();
        String space = "";
        for (int i = 0;i<(maxLength-length)/2;i++){
            space += " " ;
        }

        text = space + text;

        return text;

    }

    private static String alignRight(String text,int maxLength){
        if (maxLength - maxLength<=0){
            return text;
        }
        int length = text.length();
        String space = "";
        for (int i = 0;i<maxLength-length;i++){
            space += " " ;
        }

        text = space + text;

        return text;

    }

    /**
     * 相当于左对齐
     * @param text
     * @param maxLength
     * @return
     */
    private static String alignLeft(String text ,int maxLength){
        if (maxLength - maxLength<=0){
            return text;
        }
        int length = text.length();

        for (int i = 0;i<maxLength-length;i++){
            text += " ";
        }

        return text;

    }


    /**
     * 格式化输出多个字符
     * @param splitStr 分隔符
     * @param text 文本参数
     * @return 字符串
     */
    public static String join(String splitStr,String ...text){

        String result = "";
        for (String s : text) {
            result += splitChar(splitStr,s);
        }
        return result;

    }

    private static String splitChar(String splitStr,String text){
        text = splitStr + text + splitStr;
        return text;
    }

    private String left(String text, int length) {
        for (int i = 0;i<maxLength-length;i++){
            text += " ";
        }
        return text;
    }

}
