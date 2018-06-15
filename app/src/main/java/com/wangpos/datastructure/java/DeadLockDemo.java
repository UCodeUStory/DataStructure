package com.wangpos.datastructure.java;

/**
 * Created by qiyue on 2018/6/15.
 */

public class DeadLockDemo {

    private Object lock1 = new Object();
    private Object lock2 = new Object();


    public void startAllThread(){
        new Thread_One().start();
        new Thread_Two().start();
    }

    class Thread_One extends Thread{

        @Override
        public void run() {
            super.run();
            synchronized (lock1){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){

                    System.out.println("线程 1在执行");
                }
            }
        }
    }

    class Thread_Two extends Thread{
        @Override
        public void run() {
            super.run();
            synchronized (lock2){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){

                    System.out.println("线程 2在执行");
                }
            }
        }
    }
}
