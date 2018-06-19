package com.wangpos.datastructure.java;

import android.util.Log;

/**
 * Created by qiyue on 2018/6/16.
 */

public class ThreadOneZero {


    public static void testPrint(){
        CustomLock lock = new CustomLock();

        Thread zero_thread = new PrintThreadZero(lock);
        Thread one_thread = new PrintThreadOne(lock);

        one_thread.start();
        zero_thread.start();

    }

    //自定义锁，通过标识位设置优先打印顺序，这样就和两个线程启动顺序无关了
    public static class CustomLock {
        public boolean isPriorityPrintZero = true;
    }


    static class PrintThreadZero extends Thread{

        private CustomLock lock;

        public PrintThreadZero(CustomLock llock){
            this.lock = llock;
        }

        @Override
        public void run() {
            super.run();
            for(int i=0;i<=1000;i++){
//                System.out.println("0——————————————————00");
                synchronized (lock){
//                    System.out.println("0——————————————————11");
                    if (lock.isPriorityPrintZero){
                        System.out.println("0");
                        lock.isPriorityPrintZero = false;
                        lock.notify();
//                        System.out.println("0——————————————————22");
                    }

                    try {
//                        System.out.println("0——————————————————33");
                        lock.wait();
//                        System.out.println("0——————————————————44");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class PrintThreadOne extends Thread{

        private CustomLock lock;

        public PrintThreadOne(CustomLock llock){
            this.lock = llock;
        }
        @Override
        public void run() {
            super.run();
            for(int i=0;i<1000;i++){
//                System.out.println("1——————————————————00");
                synchronized (lock){
//                    System.out.println("1——————————————————11");
                    if (!lock.isPriorityPrintZero){
                        System.out.println("1");
                        lock.isPriorityPrintZero = true;
                        lock.notify();
//                        System.out.println("1——————————————————22");
                    }

                    try {
//                        System.out.println("1——————————————————33");
                        lock.wait();
//                        System.out.println("1——————————————————44");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

/*

0——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33

1——————————————————00
1——————————————————11
1
1——————————————————22
1——————————————————33

0——————————————————44 //第一次被wait处后面被执行

0——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33

1——————————————————44 //第二次被wait处后面被执行


1——————————————————00
1——————————————————11
1
1——————————————————22
1——————————————————33

0——————————————————44 //

0——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33
1——————————————————44//

 */


/*
0——————————————————00
1——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33
1——————————————————11
1
1——————————————————22
1——————————————————33
0——————————————————44
0——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33
1——————————————————44
1——————————————————00
1——————————————————11
1
1——————————————————22
1——————————————————33
0——————————————————44
0——————————————————00
0——————————————————11
 */



/*
1——————————————————00
1——————————————————11
0——————————————————00
1——————————————————33
0——————————————————11
0
0——————————————————22
0——————————————————33
1——————————————————44
1——————————————————00
1——————————————————11
1
1——————————————————22
1——————————————————33
0——————————————————44
0——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33
1——————————————————44
1——————————————————00
1——————————————————11
1
1——————————————————22
1——————————————————33
0——————————————————44
0——————————————————00
0——————————————————11
0
0——————————————————22
0——————————————————33
1——————————————————44
1——————————————————00
1——————————————————11
1
1——————————————————22
 */