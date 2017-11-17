package com.wangpos.datastructure.java;

/**
 * Created by qiyue on 2017/11/17.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangpos.datastructure.R;

import java.util.Arrays;

import thereisnospon.codeview.CodeView;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangpos.datastructure.R;

import java.util.Arrays;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class JavaThreadActivity extends AppCompatActivity implements View.OnClickListener {

    CodeView codeView;
    private Button btnRun;
    private TextView tvData;
    private TextView tvResult;
    private TextView tvSummary;
    private TextView tvTime;

    int arr[] = {23, 12, 13, 44, 65, 26, 17, 38, 59};
    private TextView tvStorage;

    private com.wangpos.datastructure.sort.DirectInsertSortActivity.DataBean[] dataBeans;
    private TextView tvWeidingXing;
    private static TextView tvPrintField;

    static Handler mHandler = new Handler();
    private CodeView codeView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.java_thread_activity);
        codeView = (CodeView) findViewById(R.id.codeView);

        tvData = (TextView) findViewById(R.id.data);
        tvResult = (TextView) findViewById(R.id.result);
        codeView.setTheme(CodeViewTheme.DARK);

        codeView2 = (CodeView) findViewById(R.id.codeView2);
        codeView2.setTheme(CodeViewTheme.DARK);
        tvPrintField = (TextView) findViewById(R.id.printText);


        tvData.setText("" +
                "1.单例对象的同步方法，原理就是使用同一把锁\n" +
                "2.轮训条件发\n" +
                "3.wait/notify机制\n" +
                "4.是利用CyclicBarrierAPI\n" +
                "5.管道通信就是使用java.io.PipedInputStream\n" +
                "6.利用Lock和Condition\n" +
                "7.利用volatile\n" +
                "8.利用AtomicInteger\n" +
                "分布式系统中说的两种通信机制：共享内存机制和消息通信机制");

        tvResult.setText("类锁，对象锁\n" +
                "\n" +
                "1.对象锁：java的所有对象都含有1个互斥锁，这个锁由JVM自动获取和释放。线程进入synchronized方法的时候获取该对象的锁，" +
                "当然如果已经有线程获取了这个对象的锁，那么当前线程会等待；synchronized方法正常返回或者抛异常而终止，" +
                "JVM会自动释放对象锁。这里也体现了用synchronized来加锁的1个好处，方法抛异常的时候，锁仍然可以由JVM来自动释放。\n" +
                "\n\n" +
                "2.类锁：对象锁是用来控制实例方法之间的同步，类锁是用来控制静态方法（或静态变量互斥体）之间的同步。其实类锁只是一个概念上的东西，" +
                "并不是真实存在的，它只是用来帮助我们理解锁定实例方法和静态方法的区别的。我们都知道，java类可能会有很多个对象，但是只有1个Class对象，" +
                "也就是说类的不同实例之间共享该类的Class对象。Class对象其实也仅仅是1个java对象，只不过有点特殊而已。由于每个java对象都有1个互斥锁" +
                "，而类的静态方法是需要Class对象。所以所谓的类锁，不过是Class对象的锁而已。获取类的Class对象有好几种，最简单的就是MyClass.class的方式。\n" +
                "\n" +
                "\n" +
                "\n");


        codeView.showCode(" /**\n" +
                "         * 类锁实现一：使用 static synchronized\n" +
                "         * 创建两个线程，每个线程从1打印到10000 ，运行结果，t1先打印完后，t2 再打印\n" +
                "         * 使用类锁，不管创建多少个线程，他们都使用的是同一把锁，因为Java不管创建多少个对象，Class对象始终一个\n" +
                "         *\n" +
                "         */\n" +
                "        TestThread t1 = new TestThread(1);\n" +
                "        TestThread t2 = new TestThread(2);\n" +
                "        t1.start();\n" +
                "        t2.start();" +
                "\n" +
                "\n" +
                " public static class TestThread extends Thread{\n" +
                "\n" +
                "        private int number;\n" +
                "\n" +
                "        public TestThread(int number){\n" +
                "            this.number = number;\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public  void run() {\n" +
                "            super.run();\n" +
                "            test(number);\n" +
                "\n" +
                "        }\n" +
                "        //类锁实现之一\n" +
                "        private static synchronized void test(final int number) {\n" +
                "            for (int i=0;i<10000;i++) {\n" +
                "\n" +
                "                Log.i(\"info\", \"number=\" + number +\"say=\"+ i);\n" +
                "            }\n" +
                "        }\n" +
                "    }" +
                "" +
                "\n" +
                "\n" +

                "" +
                "   /**\n" +
                "         * 类锁实现二：使用 synchronized(TestThread.class){}\n" +
                "         */\n" +
                "        TestThread t1 = new TestThread(1);\n" +
                "        TestThread t2 = new TestThread(2);\n" +
                "        t1.start();\n" +
                "        t2.start();" +

                "\n" +
                "\n" +
                "\n" +
                "    public static class TestThread extends Thread {\n" +
                "\n" +
                "        private int number;\n" +
                "\n" +
                "        public TestThread(int number) {\n" +
                "            this.number = number;\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public void run() {\n" +
                "            super.run();\n" +
                "            test(number);\n" +
                "\n" +
                "        }\n" +
                "//        //类锁实现之一\n" +
                "//        private static synchronized void test(final int number) {\n" +
                "//            for (int i=0;i<10000;i++) {\n" +
                "//\n" +
                "//                Log.i(\"info\", \"number=\" + number +\"say=\"+ i);\n" +
                "//            }\n" +
                "//        }\n" +
                "\n" +
                "        private void test(int number) {\n" +
                "            synchronized (TestThread.class) {\n" +
                "                for (int i = 0; i < 10000; i++) {\n" +
                "                    Log.i(\"info\", \"number=\" + number + \"say=\" + i);\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }");


        /**
         * 类锁实现一：使用 static synchronized
         * 创建两个线程，每个线程从1打印到10000 ，运行结果，t1先打印完后，t2 再打印
         * 使用类锁，不管创建多少个线程，他们都使用的是同一把锁，因为Java不管创建多少个对象，Class对象始终一个
         *
         */
//        TestThread t1 = new TestThread(1);
//        TestThread t2 = new TestThread(2);
//        t1.start();
//        t2.start();
        /**
         * 类锁实现二：使用 synchronized(TestThread.class){}
         */
//        TestThread t1 = new TestThread(1);
//        TestThread t2 = new TestThread(2);
//        t1.start();
//        t2.start();


        codeView2.showCode("        Person person = new Person(\"AAAAA\");\n" +
                "        Person person2 = new Person(\"YYYYYY\");\n" +
                "        TestObjectLockThread t3 = new TestObjectLockThread(person);\n" +
                "        TestObjectLockThread t4 = new TestObjectLockThread(person2);\n" +
                "        t3.start();\n" +
                "        t4.start();\n" +
                "\n" +
                "\n" +
                "" +
                "    /**\n" +
                "     * synchronized 修饰非静态，方法，默认获取自身对象的锁，所以在多线程的情况下\n" +
                "     *\n" +
                "     * 只有单例模式才能保证同步\n" +
                "     *\n" +
                "     */\n" +
                "\n" +
                "    public synchronized void say()\n" +
                "    {\n" +
                "        for (int i=0;i<10000;i++) {\n" +
                "\n" +
                "            Log.i(\"info\",name +\"说话内容=\"+ i);\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "  运行发现同步失效，使用同一个Person 就会没问题"+
                "\n");


    }



    @Override
    public void onClick(View view) {

//        tvResult.setText(Arrays.toString(arrays));


    }


    public class TestObjectLockThread extends Thread{

        private Person person;
        public TestObjectLockThread(Person person){
            this.person = person;
        }
        @Override
        public void run() {
            super.run();
            person.say();
        }
    }

    public static class TestThread extends Thread {

        private int number;

        public TestThread(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            super.run();
            test(number);

        }
//        //类锁实现之一
//        private static synchronized void test(final int number) {
//            for (int i=0;i<10000;i++) {
//
//                Log.i("info", "number=" + number +"say="+ i);
//            }
//        }

        private void test(int number) {
            synchronized (TestThread.class) {
                for (int i = 0; i < 10000; i++) {
                    Log.i("info", "number=" + number + "say=" + i);
                }
            }
        }
    }


}
