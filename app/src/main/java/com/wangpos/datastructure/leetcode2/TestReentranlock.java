package com.wangpos.datastructure.leetcode2;

import com.wangpos.datastructure.java.condition.BoundedBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentranlock {

    public static void main(String args[]) {
        final BoundedBuffer bb = new BoundedBuffer();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        System.out.println("准备写入数据");
                        bb.put(new Object());
                        System.out.println("写入数据完成");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();


        Thread t3 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        System.out.println("准备写入数据3");
                        bb.put(new Object());
                        System.out.println("写入数据完成3");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t3.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    System.out.println("准备读出数据......");
                    bb.take();
                    System.out.println("读出数据完成.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
    }

    /**
     * 准备写入数据
     * 队列未满
     * 准备唤醒读线程
     * 写入数据完成
     * 准备写入数据
     * 队列满了
     * 准备读出数据......
     * 准备唤醒写线程
     * 读出数据完成.....
     * 队列满了1
     * 队列未满
     * 准备唤醒读线程
     * 写入数据完成
     * 准备写入数据
     * 队列满了
     */
    static class BoundedBuffer {
        final Lock lock = new ReentrantLock();//锁对象
        final Condition notFull = lock.newCondition();//写线程条件
        final Condition notEmpty = lock.newCondition();//读线程条件

        final Object[] items = new Object[1];//缓存队列
        int putptr/*写索引*/, takeptr/*读索引*/, count/*队列中存在的数据个数*/;

        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length) {//如果队列满了
                    System.out.println("队列满了");
                    notFull.await();//阻塞写线程
                    //被唤醒后执行，及时多个限制再阻塞，拿到锁的也只有一个，所以只会唤醒一个
                    System.out.println("队列满了1》》》》》》》》》》》》》》》》》》》");
                }
                System.out.println("队列未满");
                items[putptr] = x;//赋值
                if (++putptr == items.length) putptr = 0;//如果写索引写到队列的最后一个位置了，那么置为0
                ++count;//个数++
                System.out.println("准备唤醒读线程");
                notEmpty.signal();//唤醒读线程
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {//如果队列为空
                    System.out.println("队列为空");
                    notEmpty.await();//阻塞读线程
                }
                Object x = items[takeptr];//取值
                if (++takeptr == items.length) takeptr = 0;//如果读索引读到队列的最后一个位置了，那么置为0
                --count;//个数--
                System.out.println("准备唤醒写线程");
                notFull.signal();//唤醒写线程
                return x;
            } finally {
                lock.unlock();
            }
        }
    }
}
