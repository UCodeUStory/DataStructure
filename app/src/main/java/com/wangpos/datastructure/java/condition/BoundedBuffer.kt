package com.wangpos.datastructure.java.condition

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock

internal class BoundedBuffer {
    val lock: Lock = ReentrantLock()
    val notFull = lock.newCondition()
    val notEmpty = lock.newCondition()

    val items = arrayOfNulls<Any>(5)
    var putptr: Int = 0
    var takeptr: Int = 0
    var count: Int = 0

    @Throws(InterruptedException::class)
    fun put(x: Any) {
        lock.lock()    //获取锁
        try {
            // 如果“缓冲已满”，则等待；直到“缓冲”不是满的，才将x添加到缓冲中。
            while (count == items.size)
                notFull.await()
            // 将x添加到缓冲中
            items[putptr] = x
            // 将“put统计数putptr+1”；如果“缓冲已满”，则设putptr为0。
            if (++putptr == items.size) putptr = 0
            // 将“缓冲”数量+1
            ++count
            // 唤醒take线程，因为take线程通过notEmpty.await()等待
            notEmpty.signal()

            // 打印写入的数据
            println(Thread.currentThread().name + " put  " + x as Int)
        } finally {
            lock.unlock()    // 释放锁
        }
    }

    @Throws(InterruptedException::class)
    fun take(): Any {
        lock.lock()    //获取锁
        try {
            // 如果“缓冲为空”，则等待；直到“缓冲”不为空，才将x从缓冲中取出。
            while (count == 0)
                notEmpty.await()
            // 将x从缓冲中取出
            val x = items[takeptr]
            // 将“take统计数takeptr+1”；如果“缓冲为空”，则设takeptr为0。
            if (++takeptr == items.size) takeptr = 0
            // 将“缓冲”数量-1
            --count
            // 唤醒put线程，因为put线程通过notFull.await()等待
            notFull.signal()

            // 打印取出的数据
            println(Thread.currentThread().name + " take " + x as Int)
            return x
        } finally {
            lock.unlock()    // 释放锁
        }
    }
}
