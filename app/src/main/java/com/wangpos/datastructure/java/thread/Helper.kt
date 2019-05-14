package com.wangpos.datastructure.java.thread

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Edison Xu on 2017/3/2.
 */
enum class Helper {

    instance;

    fun run(r: Runnable) {
        tPool.submit(r)
    }

    fun shutdown() {
        tPool.shutdown()
    }

    companion object {

        private val tPool = Executors.newFixedThreadPool(2)

        fun buildNoArr(max: Int): Array<String?> {
            val noArr = arrayOfNulls<String>(max)
            for (i in 0 until max) {
                noArr[i] = Integer.toString(i + 1)
            }
            return noArr
        }

        fun buildCharArr(max: Int): Array<String?> {
            val charArr = arrayOfNulls<String>(max)
            val tmp = 65
            for (i in 0 until max) {
                charArr[i] = (tmp + i).toChar().toString()
            }
            return charArr
        }

        @JvmStatic
        fun printString(input: String) {
            input?.let {
                print(input)
            }
        }

        @JvmStatic
        fun print2String(input: String, input2: String) {
            if (input == null)
                return
            print(input)
            print(input2)
        }
    }

}