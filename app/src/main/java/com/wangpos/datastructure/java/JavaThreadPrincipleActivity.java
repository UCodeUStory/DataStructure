package com.wangpos.datastructure.java;

import android.util.Log;
import android.view.View;

import com.wangpos.datastructure.core.BaseActivity;
import com.wangpos.datastructure.core.CodeBean;
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
    CJThreadPool cjThreadPool;

    @Override
    protected void initData() {

         addItem(new CodeBean("自己实现简单的线程池原理" ,threadcode));

         cjThreadPool = new CJThreadPool();


        runBtn.setVisibility(View.VISIBLE);
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 10; i++) {
                    final int finalI = i;
                    cjThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("qy","I am "+"("+ System.currentTimeMillis() +")");
                        }
                    });
                }
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
//        threadExcutor.shutdown();

        cjThreadPool.shutdown();

    }

    @Override
    protected String getTextData() {
        return "开启10个测试线程";
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



    public static String threadcode = "package com.wangpos.datastructure.java;\n" +
            "\n" +
            "import android.util.Log;\n" +
            "\n" +
            "import com.wangpos.datastructure.java.thread.ThreadExcutor;\n" +
            "\n" +
            "import java.util.HashSet;\n" +
            "import java.util.concurrent.ArrayBlockingQueue;\n" +
            "import java.util.concurrent.BlockingQueue;\n" +
            "import java.util.concurrent.LinkedBlockingQueue;\n" +
            "\n" +
            "/**\n" +
            " * Created by qiyue on 2018/6/25.\n" +
            " */\n" +
            "\n" +
            "public class CJThreadPool {\n" +
            "\n" +
            "    //创建\n" +
            "    private volatile boolean RUNNING = true;\n" +
            "\n" +
            "    LinkedBlockingQueue<Runnable> blockingQueues;\n" +
            "\n" +
            "    private final static int DEFAULT_CORESIZE = 3;\n" +
            "\n" +
            "    private final static int DEFAULT_QUEUESIZE = 10;\n" +
            "\n" +
            "\n" +
            "    HashSet<WorkThread> works = new HashSet<>();\n" +
            "\n" +
            "    private int coreSize = DEFAULT_CORESIZE;\n" +
            "\n" +
            "    private int queueSize = DEFAULT_QUEUESIZE;\n" +
            "\n" +
            "    private int currentStartThreadSize = 0;\n" +
            "    boolean shutdown = false;\n" +
            "\n" +
            "\n" +
            "    public CJThreadPool() {\n" +
            "        this(DEFAULT_CORESIZE, DEFAULT_QUEUESIZE);\n" +
            "    }\n" +
            "\n" +
            "    public CJThreadPool(int a_coreSize, int a_queueSize) {\n" +
            "        this.coreSize = a_coreSize;\n" +
            "        this.blockingQueues = new LinkedBlockingQueue<Runnable>(a_queueSize);\n" +
            "        Log.i(\"qy\",\"create\");\n" +
            "    }\n" +
            "\n" +
            "    public void execute(Runnable a_runnable) {\n" +
            "        if (currentStartThreadSize < coreSize) {\n" +
            "            currentStartThreadSize++;\n" +
            "//            a_runnable.run();\n" +
            "//            Log.i(\"qy\", \"add\" + currentStartThreadSize);\n" +
            "            WorkThread workThread = new WorkThread();\n" +
            "            workThread.start();\n" +
            "            works.add(workThread);\n" +
            "        }\n" +
            "\n" +
            "        try {\n" +
            "//            Log.i(\"qy\",\"put-1===\"+blockingQueues.size());\n" +
            "            blockingQueues.put(a_runnable);\n" +
            "//            Log.i(\"qy\",\"put-2====\"+blockingQueues.size());\n" +
            "        } catch (InterruptedException e) {\n" +
            "            e.printStackTrace();\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    class WorkThread extends Thread {\n" +
            "        @Override\n" +
            "        public void run() {\n" +
            "            super.run();\n" +
            "\n" +
            "            while (true && RUNNING) {\n" +
            "                try {\n" +
            "\n" +
            "                    Runnable current = blockingQueues.take();\n" +
            "                    current.run();\n" +
            "                    Log.i(\"qy\", \"我消费了一个\" + Thread.currentThread().getName() + \"====\" + current + \" size = \" + blockingQueues.size());\n" +
            "                    Thread.sleep(1000);\n" +
            "                } catch (InterruptedException e) {\n" +
            "                    e.printStackTrace();\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    public void shutdown() {\n" +
            "        RUNNING = false;\n" +
            "\n" +
            "        works.clear();\n" +
            "        blockingQueues.clear();\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "}\n";
}
