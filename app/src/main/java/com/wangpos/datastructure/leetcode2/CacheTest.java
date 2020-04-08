package com.wangpos.datastructure.leetcode2;

import android.util.Log;

import java.lang.reflect.Field;

public class CacheTest {

    public static void main(String args[]) {

        Cache<String> cache = new Cache<String>();
        Class eclz = cache.getClass();
        System.out.println("diskCache class is:" + eclz.getName());

        Field[] fs = eclz.getDeclaredFields();
        for (Field f : fs) {
            System.out.println("Field name " + f.getName() + " type:" + f.getType().getName());
        }


        Character a = 'a';
        Character b = '啊';
        Integer c = 5;
        System.out.println(a.SIZE);
        System.out.println(b.SIZE);

//        diskCache class is:com.wangpos.datastructure.leetcode2.Cache
//        Field name element type:java.lang.Object

        Integer integer1 = new Integer(212);
        Integer integer2 = new Integer(212);

        if (integer1 == integer2) {
            System.out.println("true");
        }else{
            System.out.println("false");
        }

        String str = null;

        try {
            str.toString();
        }catch (Exception e){
            System.out.println("程序异常");
            e.printStackTrace();
        }

        System.out.println("程序执行结束");
    }


    static class A {
        public static void printAContent() {

        }
    }
}
