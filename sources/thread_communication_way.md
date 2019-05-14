### 线程通信方式



1. 使用synchronized同步方法的


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
    
由于线程A和线程B持有同一个MyObject类的对象object，尽管这两个线程需要调用不同的方法，但是它们是同步执行的，比如：线程B需要等待线程A执行完了methodA()方法之后，它才能执行methodB()方法。这样，线程A和线程B就实现了 通信。

这种方式，本质上就是“共享内存”式的通信。多个线程需要访问同一个共享变量，谁拿到了锁（获得了访问权限），谁就可以执行。

2. wait/notify机制


3. 管道通信


4. 静态变量

5. volatile修饰的变量值直接存在main memory里面



####  比如 “编写两个线程，一个线程打印1~25，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z，要求使用线程间的通信。”
        这是一道非常好的面试题，非常能彰显被面者关于多线程的功力，一下子就勾起了我的兴趣。这里抛砖引玉，给出7种想到的解法。
        
