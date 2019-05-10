
### 多个安全的原子化操作组合将不是一个线程安全的，很多时候你错误用到了这一点

1. 比如


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
     
     
     
  结果会出现线程安全问题，list.size 、 list.removeAt(0) 、list.get() 里面都有线程控，单独都可以看做安全的原子化操作，
  
  但是合并一起后就必须保证合并后的是一个原子化操作，必须保证线程安全，否者就会报错，所以上面合并后的操作要加锁来保证原子化操作