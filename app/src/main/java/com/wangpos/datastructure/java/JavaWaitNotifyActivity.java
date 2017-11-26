package com.wangpos.datastructure.java;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;

import org.jsoup.Connection;

/**
 * Created by qiyue on 2017/11/26.
 */

public class JavaWaitNotifyActivity extends BaseActivity {

    @Override
    protected void initData() {

//        Object object = new Object();
//        WaitThreadTest waitThreadTest = new WaitThreadTest(object);
//        waitThreadTest.start();
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        NotifyThreadTest notifyThreadTest = new NotifyThreadTest(object);
//        notifyThreadTest.start();
    }

    @Override
    protected String getTextData() {
        return "轮询\n" +
                "\n" +
                "线程本身是操作系统中独立的个体，但是线程与线程之间不是独立的个体，因为它们彼此之间要相互通信和协作。\n" +
                "\n" +
                "想像一个场景，A线程做int型变量i的累加操作，B线程等待i到了10000就打印出i，怎么处理？一个办法就是，B线程while(i == 10000)，这样两个线程之间就有了通信，B线程不断通过轮训来检测i == 10000这个条件。\n" +
                "\n" +
                "这样可以实现我们的需求，但是也带来了问题：CPU把资源浪费了B线程的轮询操作上，因为while操作并不释放CPU资源，导致了CPU会一直在这个线程中做判断操作。如果可以把这些轮询的时间释放出来，给别的线程用，就好了。\n" +
                "\n" +
                " \n" +
                "\n" +
                "wait/notify\n" +
                "\n" +
                "在Object对象中有三个方法wait()、notify()、notifyAll()，既然是Object中的方法，那每个对象自然都是有的。如果不接触多线程的话，这两个方法是不太常见的。下面看一下前两个方法：\n" +
                "\n" +
                "1、wait()\n" +
                "\n" +
                "wait()的作用是使当前执行代码的线程进行等待，将当前线程置入\"预执行队列\"中，并且wait()所在的代码处停止执行，直到接到通知或被中断。在调用wait()之前，线程必须获得该对象的锁，因此只能在同步方法/同步代码块中调用wait()方法。\n" +
                "\n" +
                "2、notify()\n" +
                "\n" +
                "notify()的作用是，如果有多个线程等待，那么线程规划器随机挑选出一个wait的线程，对其发出通知notify()，并使它等待获取该对象的对象锁。注意\"等待获取该对象的对象锁\"，这意味着，即使收到了通知，wait的线程也不会马上获取对象锁，必须等待notify()方法的线程释放锁才可以。和wait()一样，notify()也要在同步方法/同步代码块中调用。\n" +
                "\n" +
                "总结起来就是，wait()使线程停止运行，notify()使停止运行的线程继续运行。";
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
        return "第一行和第二行之间的time减一下很明显就是3s，说明wait()之后代码一直暂停，notify()之后代码才开始运行。\n" +
                "\n" +
                "wait()方法可以使调用该线程的方法释放共享资源的锁，然后从运行状态退出，进入等待队列，直到再次被唤醒。\n" +
                "\n" +
                "notify()方法可以随机唤醒等待队列中等待同一共享资源的一个线程，并使得该线程退出等待状态，进入可运行状态\n" +
                "\n" +
                "notifyAll()方法可以使所有正在等待队列中等待同一共享资源的全部线程从等待状态退出，进入可运行状态 \n" +
                "\n" +
                "最后，如果wait()方法和notify()/notifyAll()方法不在同步方法/同步代码块中被调用，那么虚拟机会抛出java.lang.IllegalMonitorStateException，注意一下。\n" +
                "\n";
    }


    /**
     * 11-26 12:45:36.120  4688  4889 I info    : WaitThread开始------wait time = 1511671536120
     11-26 12:45:39.122  4688  4900 I info    : NotifyThread开始------notify time = 1511671539122
     11-26 12:45:39.122  4688  4900 I info    : NotifyThread结束------notify time = 1511671539122
     11-26 12:45:39.123  4688  4889 I info    : WaitThread结束------wait time = 1511671539123

     */
    public class WaitThreadTest extends Thread
    {
        private Object lock;

        public WaitThreadTest(Object lock)
        {
            this.lock = lock;
        }

        public void run()
        {
            try
            {
                synchronized (lock)
                {
                    Log.i("info","WaitThread开始------wait time = " + System.currentTimeMillis());
                    lock.wait();



                    Log.i("info","WaitThread结束------wait time = " + System.currentTimeMillis());
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


    public class NotifyThreadTest extends Thread{
        private Object lock;

        public NotifyThreadTest(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            super.run();

            synchronized (lock){
                Log.i("info","NotifyThread开始------notify time = " + System.currentTimeMillis());
                lock.notify();
                Log.i("info","NotifyThread结束------notify time = " + System.currentTimeMillis());
            }
        }
    }

}
