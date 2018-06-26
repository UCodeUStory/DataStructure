package com.wangpos.datastructure.java;

import android.util.Log;

import com.wangpos.datastructure.java.thread.ThreadExcutor;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qiyue on 2018/6/25.
 */

public class CJThreadPool {

    //创建
    private volatile boolean RUNNING = true;

    LinkedBlockingQueue<Runnable> blockingQueues;

    private final static int DEFAULT_CORESIZE = 3;

    private final static int DEFAULT_QUEUESIZE = 10;


    HashSet<WorkThread> works = new HashSet<>();

    private int coreSize = DEFAULT_CORESIZE;

    private int queueSize = DEFAULT_QUEUESIZE;

    private int currentStartThreadSize = 0;
    boolean shutdown = false;


    public CJThreadPool() {
        this(DEFAULT_CORESIZE, DEFAULT_QUEUESIZE);
    }

    public CJThreadPool(int a_coreSize, int a_queueSize) {
        this.coreSize = a_coreSize;
        this.blockingQueues = new LinkedBlockingQueue<Runnable>(a_queueSize);
        Log.i("qy","create");
    }

    public void execute(Runnable a_runnable) {
        if (currentStartThreadSize < coreSize) {
            currentStartThreadSize++;
//            a_runnable.run();
//            Log.i("qy", "add" + currentStartThreadSize);
            WorkThread workThread = new WorkThread();
            workThread.start();
            works.add(workThread);
        }

        try {
//            Log.i("qy","put-1==="+blockingQueues.size());
            blockingQueues.put(a_runnable);
//            Log.i("qy","put-2===="+blockingQueues.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();

            while (true && RUNNING) {
                try {

                    Runnable current = blockingQueues.take();
                    current.run();
                    Log.i("qy", "我消费了一个" + Thread.currentThread().getName() + "====" + current + " size = " + blockingQueues.size());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void shutdown() {
        RUNNING = false;

        works.clear();
        blockingQueues.clear();
    }


}
