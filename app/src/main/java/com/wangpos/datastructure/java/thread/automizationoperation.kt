package com.wangpos.datastructure.java.thread

import java.util.*


fun main() {
    test()
}

fun test() {

    var list = Vector<String>()
    for (i in 0..9999) {
        list.add("string$i")
    }

    Thread(Runnable {
        while (true) {
            if (list.size > 0) {
                val content = list.get(list.size - 1)
            } else {
                break
            }
        }
    }).start()

    Thread(Runnable {
        while (true) {
            if (list.size <= 0) {
                break
            }
            list.removeAt(0)
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }).start()


    /**
     * Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 9999
    at java.util.Vector.get(Vector.java:748)
    at com.wangpos.datastructure.java.thread.AutomizationoperationKt$test$1.run(automizationoperation.kt:23)
    at java.lang.Thread.run(Thread.java:745)
     */
}