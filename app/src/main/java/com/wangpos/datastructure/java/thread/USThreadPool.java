package com.wangpos.datastructure.java.thread;

import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by qiyue on 2018/6/14.
 */

public class USThreadPool {

    private Set<WorkThread> threadSet = new HashSet<>();

    private Queue<Runnable> queue = new LinkedList<>();

    private static final String TAG = USThreadPool.class.getSimpleName();

    public USThreadPool(int size){

        for (int i=0;i<size;i++){
            WorkThread workThread = new WorkThread();
            workThread.start();
            threadSet.add(workThread);
        }
    }

    public void submit(Runnable runnable){
        queue.add(runnable);
    }


    class WorkThread extends Thread{
        @Override
        public void run() {
            super.run();

            while (true){
                Log.i(TAG,"Thread-id="+getId());
                if(!queue.isEmpty()){
                    Log.i(TAG,"Thread-id2="+getId());
                    Runnable runnable = queue.poll();
                    Log.i(TAG,"Thread-id3="+getId());
                    runnable.run();
                }
            }
        }
    }
}
