package com.wangpos.datastructure.java;

import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import org.jsoup.Connection;

/**
 * Created by qiyue on 2017/11/26.
 */

public class JavaThreadSummary extends BaseActivity{
    @Override
    protected void initData() {

        /**
         * A a 不分先后
         */
//        TestJoin();

        /**
         * A 先 a 后
         */
//        TestJoin2();

        addItem(new CodeBean("join解释",joincode));
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
        return "1 A 和 a不分先后，Thread1线程很简单启动耗时非常小，Thread1线程和主线程几乎是同步的，所以打印A a不分前后根据CPU当时运行环境决定。\n" +
                "2 先打印A 再打印 a ，因为Thread1执行了Join()，相当于在主线程中添加了Thread1线程，就是说只有当Thread1执行结束之后主线程才能继续执行。" +
                "换句话说在主线程中嵌了一段代码，只有这段代码执行结束（这里不要以为调用了Join()线程Thread1会从头开始执行，不明白这句话可以运行下第3端程序），" +
                "主线程才可以继续执行后面的代码。\n" +
                " \n" +
                "join其实就是使异步执行线程转为同步执行。" +
                "" +
                "因为Thread1执行了Join()，相当于在主线程中添加了Thread1线程，这样Thread1执行结束之后主线程才能继续执行。" +
                "换句话说在主线程中嵌了一段代码，只有这段代码执行结束" +
                "主线程才可以继续执行后面的代码。\n" +
                "\n " +
                "\n " +
                "\n " +
                "\n " +
                "java 线程的几种状态\n" +
                " \n" +
                "java thread的运行周期中, 有几种状态, 在 java.lang.Thread.State 中有详细定义和说明:\n" +
                "\n" +
                "NEW 状态是指线程刚创建, 尚未启动\n" +
                "\n" +
                "RUNNABLE 状态是线程正在正常运行中, 当然可能会有某种耗时计算/IO等待的操作/CPU时间片切换等, 这个状态下发生的等待一般是其他系统资源, 而不是锁, Sleep等\n" +
                "\n" +
                "BLOCKED  这个状态下, 是在多个线程有同步操作的场景, 比如正在等待另一个线程的synchronized 块的执行释放, 或者可重入的 synchronized块里别人调用wait() 方法, 也就是这里是线程在等待进入临界区\n" +
                "WAITING  这个状态下是指线程拥有了某个锁之后, 调用了他的wait方法, 等待其他线程/锁拥有者调用 notify / notifyAll 一遍该线程可以继续下一步操作," +
                " 这里要区分 BLOCKED 和 WATING 的区别, 一个是在临界点外面等待进入, 一个是在临界点里面wait等待别人notify, 线程调用了join方法 join了另外的线程的时候, 也会进入WAITING状态, 等待被他join的线程执行结束\n" +
                "\n" +
                "TIMED_WAITING  这个状态就是有限的(时间限制)的WAITING, 一般出现在调用wait(long), join(long)等情况下, 另外一个线程sleep后, 也会进入TIMED_WAITING状态\n" +
                "\n" +
                "TERMINATED 这个状态下表示 该线程的run方法已经执行完毕了, 基本上就等于死亡了(当时如果线程被持久持有, 可能不会被回收)" +
                "" +
                "" +
                "" +
                "\n" +
                "" +
                "阻塞：当一个线程试图获取一个内部的对象锁（非java.util.concurrent库中的锁），而该锁被其他线程持有，则该线程进入阻塞状态。\n" +
                "等待：当一个线程等待另一个线程通知调度器一个条件时，该线程进入等待状态。例如调用：Object.wait()、Thread.join()以及等待Lock或Condition。";
    }



    private static final String joincode = "" +
            "join 就是将代码嵌入调用他的线程中，这样就保证了顺序执行" +
            "" +
            "\n"+
            "    public static int TestJoin()\n"+
            "    {\n"+
            "\n"+
            "        Thread Thread1 = new Thread() {\n"+
            "            @Override\n"+
            "            public void run() {\n"+
            "                super.run();\n"+
            "                Log.i(\"info\",\"A\");\n"+
            "            }\n"+
            "        };\n"+
            "        Thread1.start();\n"+
            "        Log.i(\"info\",\"a\");\n"+
            "        return 0;\n"+
            "    }\n"+
            "    \n"+
            "    \n"+
            "    //结果是先打印 A 还先打印 a ？\n"+
            "    public static int TestJoin2 ()\n"+
            "    {\n"+
            "\n"+
            "        Thread Thread1 = new Thread() {\n"+
            "            @Override\n"+
            "            public void run() {\n"+
            "                Log.i(\"info\",\"A\");\n"+
            "                super.run();\n"+
            "            }\n"+
            "            \n"+
            "        };\n"+
            "        Thread1.start();\n"+
            "        try {\n"+
            "            Thread1.join();//注意这里\n"+
            "        } catch (InterruptedException e) {\n"+
            "            e.printStackTrace();\n"+
            "        }\n"+
            "        Log.i(\"info\",\"a\");\n"+
            "        return 0;\n"+
            "    }\n"+
            "   // 结果是先打印 A 还先打印 a ？\n"+
            "    static void testJoin3()\n"+
            "    {\n"+
            "        //线程A\n"+
            "        final Thread ThreadA = new Thread()\n"+
            "        {\n"+
            "            @Override\n"+
            "            public void run() {\n"+
            "                super.run();\n"+
            "                for (int i = 0; i < 10; i++)\n"+
            "                {\n"+
            "                    Log.i(\"info\",\"A : \" );\n"+
            "                    if (i == 9)\n"+
            "                    {\n"+
            "                        break;\n"+
            "                    }\n"+
            "                    try {\n"+
            "                        Thread.sleep(1000);\n"+
            "                    } catch (InterruptedException e) {\n"+
            "                        e.printStackTrace();\n"+
            "                    }\n"+
            "                }\n"+
            "            }\n"+
            "\n"+
            "        };\n"+
            "        //线程B\n"+
            "        Thread ThreadB = new Thread()\n"+
            "        {\n"+
            "            @Override\n"+
            "            public void run() {\n"+
            "                super.run();\n"+
            "                for (int i = 0; i < 5; i++)\n"+
            "                {\n"+
            "                    Log.i(\"info\",\"B : \");\n"+
            "                    if (i == 4)\n"+
            "                    {\n"+
            "                        break;\n"+
            "                    }\n"+
            "                    try {\n"+
            "                        Thread.sleep(1000);\n"+
            "                    } catch (InterruptedException e) {\n"+
            "                        e.printStackTrace();\n"+
            "                    }\n"+
            "                }\n"+
            "\n"+
            "                try {\n"+
            "                    ThreadA.join();//在这里插入线程A\n"+
            "                } catch (InterruptedException e) {\n"+
            "                    e.printStackTrace();\n"+
            "                }\n"+
            "\n"+
            "                for (int i = 0; i < 5; i++)\n"+
            "                {\n"+
            "                    Log.i(\"info\",\"C : \" );\n"+
            "                    if (i == 4)\n"+
            "                    {\n"+
            "                        break;\n"+
            "                    }\n"+
            "                    try {\n"+
            "                        Thread.sleep(1000);\n"+
            "                    } catch (InterruptedException e) {\n"+
            "                        e.printStackTrace();\n"+
            "                    }\n"+
            "                }\n"+
            "            }\n"+
            "        };\n"+
            "        ThreadA.start();\n"+
            "        ThreadB.start();\n"+
            "    }";


    public static int TestJoin()
    {

        Thread Thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i("info","A");
            }
        };
        Thread1.start();
        Log.i("info","a");
        return 0;
    }
    
    
    //结果是先打印 A 还先打印 a ？
    public static int TestJoin2 ()
    {

        Thread Thread1 = new Thread() {
            @Override
            public void run() {
                Log.i("info","A");
                super.run();
            }
            
        };
        Thread1.start();
        try {
            Thread1.join();//注意这里
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("info","a");
        return 0;
    }
   // 结果是先打印 A 还先打印 a ？
    static void testJoin3()
    {
        //线程A
        final Thread ThreadA = new Thread()
        {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 10; i++)
                {
                    Log.i("info","A : " );
                    if (i == 9)
                    {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        //线程B
        Thread ThreadB = new Thread()
        {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 5; i++)
                {
                    Log.i("info","B : ");
                    if (i == 4)
                    {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    ThreadA.join();//在这里插入线程A
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 5; i++)
                {
                    Log.i("info","C : " );
                    if (i == 4)
                    {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadA.start();
        ThreadB.start();
    }

}
