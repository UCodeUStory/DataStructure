package com.wangpos.datastructure.java;

import android.util.Log;

/**
 * Created by qiyue on 2017/11/17.
 */

public class Person {


    private String name;

    private int number;
//    private String name;

    public Person(String name){
        this.name = name;
    }
    /**
     * synchronized 修饰非静态，方法，默认获取自身对象的锁，所以在多线程的情况下
     *
     * 只有单例模式才能保证同步
     *
     */

    public synchronized void say()
    {
        for (int i=0;i<10000;i++) {

            Log.i("info",name +"说话内容="+ i);
        }

    }

}


