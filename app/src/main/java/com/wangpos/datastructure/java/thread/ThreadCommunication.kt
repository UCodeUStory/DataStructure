package com.wangpos.datastructure.java.thread

import kotlin.concurrent.thread

fun main() {

    val messageBridge = MessageBridge()

    thread(true) {
        println(messageBridge.doSomeThing())
    }
    thread(true) {
        println(messageBridge.doSomeThing2())

    }

}


class MessageBridge {

    var message: Int = 0

    @Synchronized
    fun doSomeThing(): Int {
        message++
        return message
    }

    @Synchronized
    fun doSomeThing2(): Int {
        message++
        return message
    }
}

