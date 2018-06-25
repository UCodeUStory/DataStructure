package com.wangpos.datastructure.java;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.java.thread.ThreadExcutor;
import com.wangpos.datastructure.java.thread.USThreadPool;

import org.jsoup.Connection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by qiyue on 2018/6/14.
 */

public class JavaThreadPrincipleActivity extends BaseActivity {

    private ThreadExcutor threadExcutor;

    @Override
    protected void initData() {

//        USThreadPool usThreadPool = USThreadPool.getInstance();
//
//        for(int i=0;i<30;i++){
//            final int finalI = i;
//            usThreadPool.submit(new Runnable() {
//                @Override
//                public void run() {
//                    Log.i("USThreadPool",""+ finalI+"任务已经被执行" +
//                            "");
//                }
//            });
//        }


//        threadExcutor = new ThreadExcutor(3);
//        for (int i = 0; i < 10; i++) {
//            final int finalI = i;
//            threadExcutor.exec(new Runnable() {
//                @Override
//                public void run() {
////                    System.out.println("线程 " + Thread.currentThread().getName() + " 在帮我干活");
//                    Log.i("qy","线程 " + Thread.currentThread().getName() + " 在帮我干活"+ 1);
//                }
//            });
//        }


//        final BlockingQueue<String> bq = new ArrayBlockingQueue<String>(10);
//        Runnable producerRunnable = new Runnable() {
//            int i = 0;
//
//            public void run() {
//                while (true) {
//                    try {
//                        Log.i("qy", "我生产了一个" + i++);
//                        if (i == 20) {
//                            break;
//                        }
//                        bq.put(i + "");
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        Runnable customerRunnable = new Runnable() {
//            public void run() {
//                while (true) {
//                    try {
//                        Log.i("qy", "我消费了一个" + Thread.currentThread().getName()+"====" + bq.take());
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        Thread producerThread = new Thread(producerRunnable);
//        Thread customerThread = new Thread(customerRunnable);
//        Thread customerThread2 = new Thread(customerRunnable);
//        Thread customerThread3 = new Thread(customerRunnable);
//        Thread customerThread4 = new Thread(customerRunnable);
//        producerThread.start();
//        customerThread.start();
//        customerThread2.start();
//        customerThread3.start();
//        customerThread4.start();
//        excutor.shutdown();

        CJThreadPool cjThreadPool = new CJThreadPool();
        for (int i = 0; i < 10; i++) {
              Log.i("qy","for>>>>>>>>>>"+i);
              cjThreadPool.execute(new Runnable() {
                  @Override
                  public void run() {
                      Log.i("qy","I am "+System.currentTimeMillis());
                  }
              });
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
//        threadExcutor.shutdown();
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
}
