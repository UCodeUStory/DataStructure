package com.wangpos.datastructure.java;

import android.util.Log;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by qiyue on 2018/6/25.
 */

public class CJThreadPool {

     BlockingQueue<Runnable> blockingQueues ;

     private final static int DEFAULT_CORESIZE = 3;

     private final static int DEFAULT_QUEUESIZE = 10;


    static HashSet<WorkThread> works = new HashSet<>();

    private int coreSize = 3;

    private int queueSize = 10;

    private int currentStartThreadSize = 0;

    public CJThreadPool(){
        this(DEFAULT_CORESIZE,DEFAULT_QUEUESIZE);
    }

    public CJThreadPool(int a_coreSize,int a_queueSize){
        this.coreSize = a_coreSize;
        this.blockingQueues = new ArrayBlockingQueue<Runnable>(a_queueSize);
    }

    public void execute(Runnable a_runnable){
        if (currentStartThreadSize<coreSize){
            currentStartThreadSize++;
            Log.i("qy","add"+currentStartThreadSize);
            WorkThread workThread = new WorkThread();
            workThread.start();
            works.add(workThread);
        }else{
            Log.i("qy","else"+currentStartThreadSize);
            try {
                blockingQueues.put(a_runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    class WorkThread extends Thread{
        @Override
        public void run() {
            super.run();

            while (true) {
                try {
                    Runnable current = blockingQueues.take();
                    current.run();
                    Log.i("qy", "我消费了一个" + Thread.currentThread().getName()+"====" +current);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
