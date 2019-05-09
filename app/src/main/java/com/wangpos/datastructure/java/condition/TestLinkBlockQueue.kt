package com.wangpos.datastructure.java.condition

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread


fun main() {


    var blockingQueue = LinkedBlockingQueue<String>(3)

    var atomicoInteger = AtomicInteger();


    var lock = ReentrantLock()
    var condition = lock.newCondition()

    var oneThread = thread {
        //            blockingQueue.put("test-$i")
        var count = atomicoInteger.get()

        lock.lock()
        while (atomicoInteger.get() == 0) {
            println("----------oneThread---------- ")
            condition.await()
                        println("---------222-oneThread---------- ")
        }
        lock.unlock()
    }

    var threeThread = thread {
        for (i in 1..12) {
            println(">>>>>>>>threeThread>>>>>>>>> $i")

            atomicoInteger.getAndIncrement()
        }
        condition.signal()
    }

/*    var twoThread = thread {

        //            println("read = "+blockingQueue.take())
        var count = atomicoInteger.get()
        while (count > 10) {
            for (i in 0..10) {
                println(">>>>>>>>>>>>>>>>> $i")
                atomicoInteger.getAndDecrement()
            }
        }
    }*/
    oneThread.start()
    threeThread.start()
}