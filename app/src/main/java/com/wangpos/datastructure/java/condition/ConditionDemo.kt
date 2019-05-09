package com.wangpos.datastructure.java.condition

import java.util.concurrent.locks.ReentrantLock



val lock = ReentrantLock()
val condition = lock.newCondition()


private val bb = BoundedBuffer()

fun main(args:Array<String>){
//    var ta = ThreadA("A")
//
//    lock.lock() // 获取锁
//    try {
//        println(Thread.currentThread().name + " start ta")
//        ta.start()
//
//        println(Thread.currentThread().name + " block")
//        condition.await()    // 等待 释放锁
//
//        println(Thread.currentThread().name + " continue")
//    } catch (e: InterruptedException) {
//        e.printStackTrace()
//    } finally {
//        lock.unlock()    // 释放锁
//    }


        // 启动10个“写线程”，向BoundedBuffer中不断的写数据(写入0-9)；
        // 启动10个“读线程”，从BoundedBuffer中不断的读数据。
        for (i in 0..9) {
            PutThread("p$i", i).start()
            TakeThread("t$i").start()
        }


}


internal class PutThread(name: String, private val num: Int) : Thread(name) {
    override fun run() {
        try {
            Thread.sleep(1)    // 线程休眠1ms
            bb.put(num)        // 向BoundedBuffer中写入数据
        } catch (e: InterruptedException) {
        }

    }
}

internal class TakeThread(name: String) : Thread(name) {
    override fun run() {
        try {
            Thread.sleep(10)                    // 线程休眠1ms
            val num = bb.take() as Int    // 从BoundedBuffer中取出数据
        } catch (e: InterruptedException) {
        }

    }
}

internal class ThreadA(name: String) : Thread(name) {

    override fun run() {
        lock.lock()    // 获取锁
        try {
            println(Thread.currentThread().name + " wakup others")
            condition.signal()    // 唤醒“condition所在锁上的其它线程”
        } finally {
            lock.unlock()    // 释放锁
        }
    }
}