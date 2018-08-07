### android.os.killprocess 和System.exit()区别

1. android.os.killprocess()可以杀掉任何第三方进程传入pid就可以,System.exit()只能杀掉本身


2. System.exit它的意思是退出JVM（java虚拟机），在android中一样可以用，我们可以想像一下虚拟机都退出了当然执行System.exit的程序会完全退出，内存被释放。

在android手机中查看当前正在运行的进程时可以发现还可以查看"后台缓存的进程"，你会发现很多退出了的程序还在后台缓存的进程中，如果不要让程序在后台缓存那么就可以用System.exit(0);来退出程序了，可以清除后台缓存的本进程。

System.exit(0),System.exit(1)的区别：

参数0和1代表退出的状态，0表示正常退出，1表示异常退出(只要是非0的都为异常退出)，即使不传0来执行也可以退出，该参数只是通知操作系统该程序是否是正常退出。