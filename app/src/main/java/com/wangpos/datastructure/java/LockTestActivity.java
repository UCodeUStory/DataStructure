package com.wangpos.datastructure.java;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qiyue on 2018/7/18.
 */

public class LockTestActivity extends BaseActivity {

    public static String TAG = LockTestActivity.class.getSimpleName();

    @Override
    protected void initData() {

        LockTestThread t1 = new LockTestThread();
        LockTestThread t2 = new LockTestThread();
        t1.start();
        t2.start();

    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return null;
    }

    @Override
    protected String getTimeData() {
        return null;
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return null;
    }

    @Override
    protected String getSummaryData() {
        return null;
    }



    private Lock lock = new ReentrantLock();


    class LockTestThread extends Thread{


        @Override
        public void run() {
            super.run();


            if (lock.tryLock()) {
                try {
                    lock.lock();

                    Log.i(TAG, "线程名" + this.getName() + "获得了锁");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    Log.i(TAG, "线程名" + this.getName() + "释放了锁");
                }
            }else{
                Log.i(TAG, "线程名" + this.getName() + "有人占用锁");

            }




        }
    }
}
