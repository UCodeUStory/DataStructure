package com.wangpos.datastructure.java.thread

import java.util.*


fun main() {
    val one = MethodOne()
    Helper.instance.run(one.newThreadOne())
    Helper.instance.run(one.newThreadTwo())
    Helper.instance.shutdown()
}

/*8
1. 第一种解法，包含多种小的不同实现方式，但一个共同点就是靠一个共享变量来做控制；
a. 利用最基本的synchronized、notify、wait：
 */



class MethodOne {
    private val threadToGo = ThreadToGo()

    fun newThreadOne(): Runnable {
        val inputArr = Helper.buildNoArr(52)
        println(Arrays.toString(inputArr))
        return Runnable {
            try {
                var i = 0
                while (i < inputArr.size) {
                    synchronized(threadToGo) {
                        while (threadToGo.value == 2)
                            threadToGo.wait()
                        Helper.print2String(inputArr[i]!!, inputArr[i + 1]!!)
                        threadToGo.value = 2
                        threadToGo.notify()
                    }
                    i += 2
                }
            } catch (e: InterruptedException) {
                println("Oops...")
            }
        }
    }

    fun newThreadTwo(): Runnable {
        val inputArr = Helper.buildCharArr(26)
        println(Arrays.toString(inputArr))
        return Runnable {
            try {
                for (i in inputArr.indices) {
                    synchronized(threadToGo) {
                        while (threadToGo.value == 1)
                            threadToGo.wait()
                        Helper.printString(inputArr[i]!!)
                        threadToGo.value = 1
                        threadToGo.notify()
                    }
                }
            } catch (e: InterruptedException) {
                println("Oops...")
            }
        }
    }

    internal inner class ThreadToGo:java.lang.Object() {
        var value = 1
    }


}