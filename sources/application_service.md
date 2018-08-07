### application 开线程可以替换Service处理后台任务吗


#### 可以，但是有缺点


1. Application中初始化太多东西，会导致app启动速度变慢（开启一个线程没什么，开启多个就会有问题）

2. Application生命周期过长，Application生命周期和应用的什么周期一样，如果后台任务不需要这么长的生命周期，用IntentService可以实现完成后自动关闭，自动同步，远比Application好

3. Application生命周期无法延续，在被强杀后，后台任务会关闭；而服务可以指定新进程进行保活。考虑音乐播放器的功能，有些音乐播放器，即使应用退出后依然可以进行前台服务的保留以便于随时恢复

总结

1.会降低性能

2.Application提供的生命周期自由度不足

3.Application中开启子线程其实是很不错的，因为Service开销太大

#### Application 生命周期

1. Application 生命周期和应用的生命周期一样，应用的声明周期是怎么样的呢？？

   你是否还天真的以为我们在按返回键退出最后一个activity应用程序就退出了？答案是否定的
   
   当我们退出最后一个Activity时，应用程序还没有退出，通过重写Application可以看出，再次进入应用，Application 并没有重新初始化。
   
2. 如果真正退出一个应用程序？

   在最后一个Activity的onDestory中 System.exit(0),可以正常退出应用，再次进入应用时Application的onCreate会重新执行
   
3. System.exit(0) 注意事项

   - 1. System.exit(0) 传入非0表示正常退出
   
   - 2. System.exit(0) 只能退出当前进程，如果进程中启动了一个服务，在新的进程，新的服务不会被杀死，此服务只能通过StopService停止
   
   - 3. System.exit(0) 时当程序中有startService启动一个服务，服务没有开启新的进程，此时，服务会被重启，重新走onCreate等生命周期方法,并且应用进程也会被重启，Application也会重新onCreate初始化
   
 

