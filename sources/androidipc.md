### Android 进程通信方式


一、使用 Intent (startActivity startService sendBroadcast)
二、使用文件共享 (SharePreference)
三、使用 Messenger 
四、使用 AIDL
五、使用 ContentProvider
六、使用 Socket



Messenger

1. Messenger本质也是AIDL，只是进行了封装，开发的时候不用再写.aidl文件。
结合我自身的使用，因为不用去写.aidl文件，相比起来，Messenger使用起来十分简单。但前面也说了，Messenger本质上也是AIDL，故在底层进程间通信这一块，两者的效率应该是一样的。

2. 在service端，Messenger处理client端的请求是单线程的，而AIDL是多线程的。
使用AIDL的时候，service端每收到一个client端的请求时，就会启动一个线程（非主线程）去执行相应的操作。而Messenger，service收到的请求是放在Handler的MessageQueue里面，Handler大家都用过，它需要绑定一个Thread，然后不断poll message执行相关操作，这个过程是同步执行的。
