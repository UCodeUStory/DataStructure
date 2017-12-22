package com.wangpos.datastructure.java;

import android.os.Handler;
import android.view.View;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import java.util.concurrent.CountDownLatch;

/**
 * Created by qiyue on 2017/12/22.
 */

public class KeepMoreRequest extends BaseActivity {

    Handler mHandler = new Handler();

    int first = 0;

    int second = 0;

    String waitMsg = "";

    @Override
    protected void initData() {
        addItem(new CodeBean("等待多个异步请求的代码",code));
        runBtn.setVisibility(View.VISIBLE);
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * 开发需求，已有的任务如何快速添加
                 *
                 * 1.线程数量
                 * 1.添加各个执行任务{通常使用不同的网络框架怎么办}
                 * 2.添加最后执行任务
                 *
                 */
                //线程数量
                final CountDownLatch latch = new CountDownLatch(2);
                WorkThread workThread1 = new WorkThread("线程1",30,latch);
                WorkThread2 workThread2 = new WorkThread2("线程2",60,latch);

                workThread1.start();
                workThread2.start();

                Thread t = new Thread(){

                    @Override
                    public void run() {
                        super.run();
                        try {
                            waitMsg = "等待执行";
                            latch.await();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText("线程 1="+first+ "\n" + "线程2 ="+second + "\n" + "线程3 ==" + "开始执行");
                                }
                            });


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();



            }
        });

    }

    @Override
    protected String getTextData() {
        return "线程1 =="+first +"\n"+"线程2 =="+second;
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return "线程1 =="+first +"\n"+"线程2 =="+second;
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


    public void start1Thread(){
        CountDownLatch countDownLatch = new CountDownLatch(2);



    }


    class WorkThread2 extends Thread{

        CountDownLatch countDownLatch;
        private String name;

        private int count = 0;

        public WorkThread2(String name,int count ,CountDownLatch latch){
            this.countDownLatch = latch;
            this.name = name;
            this.count = count;
        }

        @Override
        public void run() {
            super.run();

            int i=0;
            while(i<count) {
                final int finalI = i;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        second = finalI;
                        tvResult.setText("线程 1="+first+ "\n" + "线程2 ="+second + "\n" + "线程3 ==" + waitMsg);
                    }
                });
                i++;
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            //
            countDownLatch.countDown();
        }
    }

    class WorkThread extends Thread{

        CountDownLatch countDownLatch;
        private String name;

        private int count = 0;

        public WorkThread(String name,int count ,CountDownLatch latch){
            this.countDownLatch = latch;
            this.name = name;
            this.count = count;
        }

        @Override
        public void run() {
            super.run();

                int i=0;
                while(i<count) {
                    final int finalI = i;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            first = finalI;
                            tvResult.setText("线程 1="+first+ "\n" + "线程2 ="+second + "\n" + "线程3 ==" + waitMsg);
                        }
                    });
                    i++;
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



            //
            countDownLatch.countDown();
        }
    }




    private static String code = "" +
            "                final CountDownLatch latch = new CountDownLatch(2);\n" +
            "                WorkThread workThread1 = new WorkThread(\"线程1\",50,latch);\n" +
            "                WorkThread2 workThread2 = new WorkThread2(\"线程2\",100,latch);\n" +
            "\n" +
            "                workThread1.start();\n" +
            "                workThread2.start();\n" +
            "\n" +
            "                Thread t = new Thread(){\n" +
            "\n" +
            "                    @Override\n" +
            "                    public void run() {\n" +
            "                        super.run();\n" +
            "                        try {\n" +
            "                            waitMsg = \"等待执行\";\n" +
            "                            latch.await();\n" +
            "                            mHandler.post(new Runnable() {\n" +
            "                                @Override\n" +
            "                                public void run() {\n" +
            "                                    tvResult.setText(\"线程 1=\"+first+ \"\\n\" + \"线程2 =\"+second + \"\\n\" + \"线程3 ==\" + \"开始执行\");\n" +
            "                                }\n" +
            "                            });\n" +
            "\n" +
            "\n" +
            "                        } catch (InterruptedException e) {\n" +
            "                            e.printStackTrace();\n" +
            "                        }\n" +
            "                    }\n" +
            "                };\n" +
            "                t.start();\n" +
            "class WorkThread2 extends Thread{\n" +
            "\n" +
            "        CountDownLatch countDownLatch;\n" +
            "        private String name;\n" +
            "\n" +
            "        private int count = 0;\n" +
            "\n" +
            "        public WorkThread2(String name,int count ,CountDownLatch latch){\n" +
            "            this.countDownLatch = latch;\n" +
            "            this.name = name;\n" +
            "            this.count = count;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public void run() {\n" +
            "            super.run();\n" +
            "\n" +
            "            int i=0;\n" +
            "            while(i<count) {\n" +
            "                final int finalI = i;\n" +
            "                mHandler.post(new Runnable() {\n" +
            "                    @Override\n" +
            "                    public void run() {\n" +
            "\n" +
            "                        second = finalI;\n" +
            "                        tvResult.setText(\"线程 1=\"+first+ \"\\n\" + \"线程2 =\"+second + \"\\n\" + \"线程3 ==\" + waitMsg);\n" +
            "                    }\n" +
            "                });\n" +
            "                i++;\n" +
            "                try {\n" +
            "                    sleep(500);\n" +
            "                } catch (InterruptedException e) {\n" +
            "                    e.printStackTrace();\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "\n" +
            "\n" +
            "            //\n" +
            "            countDownLatch.countDown();\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    class WorkThread extends Thread{\n" +
            "\n" +
            "        CountDownLatch countDownLatch;\n" +
            "        private String name;\n" +
            "\n" +
            "        private int count = 0;\n" +
            "\n" +
            "        public WorkThread(String name,int count ,CountDownLatch latch){\n" +
            "            this.countDownLatch = latch;\n" +
            "            this.name = name;\n" +
            "            this.count = count;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public void run() {\n" +
            "            super.run();\n" +
            "\n" +
            "                int i=0;\n" +
            "                while(i<count) {\n" +
            "                    final int finalI = i;\n" +
            "                    mHandler.post(new Runnable() {\n" +
            "                        @Override\n" +
            "                        public void run() {\n" +
            "                            first = finalI;\n" +
            "                            tvResult.setText(\"线程 1=\"+first+ \"\\n\" + \"线程2 =\"+second + \"\\n\" + \"线程3 ==\" + waitMsg);\n" +
            "                        }\n" +
            "                    });\n" +
            "                    i++;\n" +
            "                    try {\n" +
            "                        sleep(500);\n" +
            "                    } catch (InterruptedException e) {\n" +
            "                        e.printStackTrace();\n" +
            "                    }\n" +
            "                }\n" +
            "\n" +
            "\n" +
            "\n" +
            "            //\n" +
            "            countDownLatch.countDown();\n" +
            "        }\n" +
            "    }";
}
