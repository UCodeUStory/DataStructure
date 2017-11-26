package com.wangpos.datastructure.java;

import android.os.Handler;
import android.util.Log;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;

import org.jsoup.Connection;


/**
 * Created by qiyue on 2017/11/26.
 */

public class JavaWaitNotifyActivity extends BaseActivity {

    TestInteruptThread testInteruptThread;
    Handler mHandler = new android.os.Handler();


    @Override
    protected void initData() {

        setTitleText("Wait/Notify/NotifyAll详解");


        addItem(new CodeBean("wait和notify使用",wait_notify_code));
        addItem(new CodeBean("使用Interupt停止线程",interuptThreadCode));
//        addItem(new CodeBean("深入理解Sleep含义",sleep_code));
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


        testInteruptThread = new TestInteruptThread(new Object());

        testInteruptThread.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testInteruptThread.interrupt();

            }
        },10000);

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
                "\n" +
                "" +
                "" +
                "\n" +
                "interrupt()打断wait()\n" +
                "\n" +
                "之前有说过，interrupt()方法的作用不是中断线程，而是在线程阻塞的时候给线程一个中断标识，表示该线程中断。wait()就是\"阻塞的一种场景\"，" +
                "看一下用interrupt()打断wait()的例子：" +
                "深入理解：\n" +
                "   如果线程调用了对象的wait（）方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。\n" +
                "   当有线程调用了对象的notifyAll（）方法（唤醒所有wait线程）或notify（）方法（只随机唤醒一个wait线程），被唤醒的的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。\n" +
                "   优先级高的线程竞争到对象锁的概率大，假若某线程没有竞争到该对象锁，它还会留在锁池中，唯有线程再次调用wait（）方法，它才会重新回到等待池中。而竞争到对象锁的线程则继续往下执行，直到执行完了synchronized代码块，它会释放掉该对象锁，这时锁池中的线程会继续竞争该对象锁。" +
                "\n" +
                "" +
                "" +
                "" +
                "深入理解Thead Sleep作用" +
                "" +
                ""+sleep_code;
    }



    private static final String wait_notify_code = "/**\n" +
            "     * 11-26 12:45:36.120  4688  4889 I info    : WaitThread开始------wait time = 1511671536120\n" +
            "     11-26 12:45:39.122  4688  4900 I info    : NotifyThread开始------notify time = 1511671539122\n" +
            "     11-26 12:45:39.122  4688  4900 I info    : NotifyThread结束------notify time = 1511671539122\n" +
            "     11-26 12:45:39.123  4688  4889 I info    : WaitThread结束------wait time = 1511671539123\n" +
            "\n" +
            "     */\n" +
            "    public class WaitThreadTest extends Thread\n" +
            "    {\n" +
            "        private Object lock;\n" +
            "\n" +
            "        public WaitThreadTest(Object lock)\n" +
            "        {\n" +
            "            this.lock = lock;\n" +
            "        }\n" +
            "\n" +
            "        public void run()\n" +
            "        {\n" +
            "            try\n" +
            "            {\n" +
            "                synchronized (lock)\n" +
            "                {\n" +
            "                    Log.i(\"info\",\"WaitThread开始------wait time = \" + System.currentTimeMillis());\n" +
            "                    lock.wait();\n" +
            "\n" +
            "\n" +
            "\n" +
            "                    Log.i(\"info\",\"WaitThread结束------wait time = \" + System.currentTimeMillis());\n" +
            "                }\n" +
            "            }\n" +
            "            catch (InterruptedException e)\n" +
            "            {\n" +
            "                e.printStackTrace();\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    public class NotifyThreadTest extends Thread{\n" +
            "        private Object lock;\n" +
            "\n" +
            "        public NotifyThreadTest(Object lock){\n" +
            "            this.lock = lock;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public void run() {\n" +
            "            super.run();\n" +
            "\n" +
            "            synchronized (lock){\n" +
            "                Log.i(\"info\",\"NotifyThread开始------notify time = \" + System.currentTimeMillis());\n" +
            "                lock.notify();\n" +
            "                Log.i(\"info\",\"NotifyThread结束------notify time = \" + System.currentTimeMillis());\n" +
            "            }\n" +
            "        }\n" +
            "    }\n";

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


    private static final String interuptThreadCode = "/**\n" +
            "     * 使用interupt中断一个线程，线程必须是wait和sleep或者网络IO阻塞，\n" +
            "     * 所以下面是最好的方式停止一个线程,如果紧紧使用isRun线程并没有退出而只是不执行循环体代码\n" +
            "     */\n" +
            "    public class TestInteruptThread extends Thread{\n" +
            "\n" +
            "        public Object lock;\n" +
            "\n" +
            "        public boolean isRun = true;\n" +
            "        public TestInteruptThread(Object lock){\n" +
            "            this.lock = lock;\n" +
            "        }\n" +
            "\n" +
            "        @Override\n" +
            "        public void run() {\n" +
            "            super.run();\n" +
            "\n" +
            "            try {\n" +
            "                synchronized (lock) {\n" +
            "                    while (isRun) {\n" +
            "                        Thread.sleep(1000);\n" +
            "                        Log.i(\"qy\", \"线程正在运行\");\n" +
            "                    }\n" +
            "\n" +
            "                }\n" +
            "            }catch (Exception e){\n" +
            "                Log.i(\"qy\",\"线程安全退出\");\n" +
            "            }\n" +
            "        }\n" +
            "    }";
    /**
     * 使用interupt中断一个线程，线程必须是wait和sleep或者网络IO阻塞，
     * 所以下面是最好的方式停止一个线程,如果紧紧使用isRun线程并没有退出而只是不执行循环体代码
     */
    public class TestInteruptThread extends Thread{

        public Object lock;

        public boolean isRun = true;
        public TestInteruptThread(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            super.run();

            try {
                synchronized (lock) {
                    while (isRun) {
                        Thread.sleep(1000);
                        Log.i("qy", "线程正在运行");
                    }

                }
            }catch (Exception e){
                Log.i("qy","线程安全退出");
            }
        }
    }




    public static final String sleep_code = "" +
            "思考下面这两个问题：\n" +
            "\n" +
            "假设现在是 2008-4-7 12:00:00.000，如果我调用一下 Thread.Sleep(1000) ，在 2008-4-7 12:00:01.000 的时候，这个线程会 不会被唤醒？ \n" +
            "" +
            "不一定因为你只是告诉操作系统：在未来的1000毫秒内我不想再参与到CPU竞争。那么1000毫秒过去之后，这时候也许另外一个线程正在使用CPU(如果是多核心，就是可能运行线程数达到最大)，" +
            "那么这时候操作系统是不会重新分配CPU的，直到那个线程挂起或结束；况且，" +
            "即使这个时候恰巧轮到操作系统进行CPU 分配，那么当前线程也不一定就是总优先级最高的那个，CPU还是可能被其他线程抢占去。 \n" +
            "\n"+
            "某人的代码中用了一句看似莫明其妙的话：Thread.Sleep(0) 。既然是 Sleep 0 毫秒，那么他跟去掉这句代码相比，有啥区别么？ \n" +
            "" +
            "有，而且区别很明显。假设我们刚才的分蛋糕场景里面，有另外一个PPMM 7号，她的优先级也非常非常高（因为非常非常漂亮），所以操作系统总是会叫道她来吃蛋糕。而且，7号也非常喜欢吃蛋糕，而且饭量也很大。不过，7号人品很好，她很善良，她没吃几口就会想：如果现在有别人比我更需要吃蛋糕，那么我就让给他。因此，她可以每吃几口就跟操作系统说：我们来重新计算一下所有人的总优先级吧。不过，操作系统不接受这个建议——因为操作系统不提供这个接口。于是7号mm就换了个说法：“在未来的0毫秒之内不要再叫我上来吃蛋糕了”。这个指令操作系统是接受的，于是此时操作系统就会重新计算大家的总优先级——注意这个时候是连7号一起计算的，因为“0毫秒已经过去了”嘛。因此如果没有比7号更需要吃蛋糕的人出现，那么下一次7号还是会被叫上来吃蛋糕。\n" +
            "\n" +
            "因此，Thread.Sleep(0)的作用，就是“触发操作系统立刻重新进行一次CPU竞争”。竞争的结果也许是当前线程仍然获得CPU控制权，也许会换成别的线程获得CPU控制权。这也是我们在大循环里面经常会写一句Thread.Sleep(0) ，因为这样就给了其他线程比如Paint线程获得CPU控制权的权力，这样界面就不会假死在那里。\n" +
            "\n" +
            "" +
            "" +
            "我们先回顾一下操作系统原理。\n" +
            "\n" +
            "操作系统中，CPU竞争有很多种策略。Unix系统使用的是时间片算法，而Windows则属于抢占式的。\n" +
            "\n" +
            "在时间片算法中，所有的进程排成一个队列。操作系统按照他们的顺序，给每个进程分配一段时间，即该进程允许运行的时间。如果在 时间片结束时进程还在运行，则CPU将被剥夺并分配给另一个进程。如果进程在时间片结束前阻塞或结束，则CPU当即进行切换。调度程 序所要做的就是维护一张就绪进程列表，，当进程用完它的时间片后，它被移到队列的末尾。\n" +
            "\n" +
            "所谓抢占式操作系统，就是说如果一个进程得到了 CPU 时间，除非它自己放弃使用 CPU ，否则将完全霸占 CPU 。因此可以看出，在抢 占式操作系统中，操作系统假设所有的进程都是“人品很好”的，会主动退出 CPU 。\n" +
            "\n" +
            "在抢占式操作系统中，假设有若干进程，操作系统会根据他们的优先级、饥饿时间（已经多长时间没有使用过 CPU 了），给他们算出一 个总的优先级来。操作系统就会把 CPU 交给总优先级最高的这个进程。当进程执行完毕或者自己主动挂起后，操作系统就会重新计算一 次所有进程的总优先级，然后再挑一个优先级最高的把 CPU 控制权交给他。\n" +
            "\n" +
            "我们用分蛋糕的场景来描述这两种算法。假设有源源不断的蛋糕（源源不断的时间），一副刀叉（一个CPU），10个等待吃蛋糕的人（10 个进程）。\n" +
            "\n" +
            "如果是 Unix\u007F操作系统来负责分蛋糕，那么他会这样定规矩：每个人上来吃 1 分钟，时间到了换下一个。最后一个人吃完了就再从头开始。于是，不管这10个人是不是优先级不同、饥饿程度不同、饭量不同，每个人上来的时候都可以吃 1 分钟。当然，如果有人本来不太饿，或者饭量小，吃了30秒钟之后就吃饱了，那么他可以跟操作系统说：我已经吃饱了（挂起）。于是操作系统就会让下一个人接着来。\n" +
            "\n" +
            "如果是 Windows 操作系统来负责分蛋糕的，那么场面就很有意思了。他会这样定规矩：我会根据你们的优先级、饥饿程度去给你们每个人计算一个优先级。优先级最高的那个人，可以上来吃蛋糕——吃到你不想吃为止。等这个人吃完了，我再重新根据优先级、饥饿程度来计算每个人的优先级，然后再分给优先级最高的那个人。\n" +
            "\n" +
            "这样看来，这个场面就有意思了——可能有些人是PPMM，因此具有高优先级，于是她就可以经常来吃蛋糕。可能另外一个人是个丑男，而去很ws，所以优先级特别低，于是好半天了才轮到他一次（因为随着时间的推移，他会越来越饥饿，因此算出来的总优先级就会越来越高，因此总有一天会轮到他的）。而且，如果一不小心让一个大胖子得到了刀叉，因为他饭量大，可能他会霸占着蛋糕连续吃很久很久，导致旁边的人在那里咽口水。。。 \n" +
            "而且，还可能会有这种情况出现：操作系统现在计算出来的结果，5号PPMM总优先级最高，而且高出别人一大截。因此就叫5号来吃蛋糕。5号吃了一小会儿，觉得没那么饿了，于是说“我不吃了”（挂起）。因此操作系统就会重新计算所有人的优先级。因为5号刚刚吃过，因此她的饥饿程度变小了，于是总优先级变小了；而其他人因为多等了一会儿，饥饿程度都变大了，所以总优先级也变大了。不过这时候仍然有可能5号的优先级比别的都高，只不过现在只比其他的高一点点——但她仍然是总优先级最高的啊。因此操作系统就会说：5号mm上来吃蛋糕……（5号mm心里郁闷，这不刚吃过嘛……人家要减肥……谁叫你长那么漂亮，获得了那么高的优先级）。\n" +
            "\n" +
            "那么，Thread.Sleep 函数是干吗的呢？还用刚才的分蛋糕的场景来描述。上面的场景里面，5号MM在吃了一次蛋糕之后，觉得已经有8分饱了，她觉得在未来的半个小时之内都不想再来吃蛋糕了，那么她就会跟操作系统说：在未来的半个小时之内不要再叫我上来吃蛋糕了。这样，操作系统在随后的半个小时里面重新计算所有人总优先级的时候，就会忽略5号mm。Sleep函数就是干这事的，他告诉操作系统“在未来的多少毫秒内我不参与CPU竞争”。";

}
