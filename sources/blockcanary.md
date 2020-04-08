BlockCanary原理分析

BlockCanary是国内开发者MarkZhai开发的一套性能监控组件，它对主线程操作进行了完全透明的监控，并能输出有效的信息，帮助开发分析、定位到问题所在，迅速优化应用。

1.基本使用
使用非常方便,引入

dependencies {
   
    compile 'com.github.markzhai:blockcanary-android:1.5.0'

    // 仅在debug包启用BlockCanary进行卡顿监控和提示的话，可以这么用
    debugCompile 'com.github.markzhai:blockcanary-android:1.5.0'
    releaseCompile 'com.github.markzhai:blockcanary-no-op:1.5.0'
}


在应用的application中完成初始化

public class DemoApplication extends Application {
 
    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this, new AppContext()).start();
    }
}
  
//参数设置
public class AppContext extends BlockCanaryContext {
    private static final String TAG = "AppContext";
 
    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = DemoApplication.getAppContext().getPackageManager()
                    .getPackageInfo(DemoApplication.getAppContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }
 
    @Override
    public int provideBlockThreshold() {
        return 500;
    }
 
    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }
 
    @Override
    public boolean stopWhenDebugging() {
        return false;
    }
}




2、基本原理
我们都知道Android应用程序只有一个主线程ActivityThread，这个主线程会创建一个Looper(Looper.prepare)，而Looper又会关联一个MessageQueue，主线程Looper会在应用的生命周期内不断轮询(Looper.loop)，从MessageQueue取出Message 更新UI。
我们来看一个代码片段

public static void loop() {
    ...
    for (;;) {
        ...
        // This must be in a local variable, in case a UI event sets the logger
        Printer logging = me.mLogging;
        if (logging != null) {
            logging.println(">>>>> Dispatching to " + msg.target + " " +
                    msg.callback + ": " + msg.what);
        }
        msg.target.dispatchMessage(msg);
        if (logging != null) {
            logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
        }
        ...
    }
}



msg.target其实就是Handler，看一下dispatchMessage的逻辑

/**
 * Handle system messages here.
 */ 
public void dispatchMessage(Message msg) { 
    if (msg.callback != null) { 
        handleCallback(msg); 
    } else { 
        if (mCallback != null) { 
            if (mCallback.handleMessage(msg)) { 
                return; 
            } 
        } 
        handleMessage(msg); 
    } 
}


即调用BlockCanary的start方法

public void start() {
    if (!mMonitorStarted) {
        mMonitorStarted = true;
        Looper.getMainLooper().setMessageLogging(mBlockCanaryCore.monitor);
    }
}

* 如果消息是通过Handler.post(runnable)方式投递到MQ中的，那么就回调runnable#run方法；
* 如果消息是通过Handler.sendMessage的方式投递到MQ中，那么回调handleMessage方法；
不管是哪种回调方式，回调一定发生在UI线程。因此如果应用发生卡顿，一定是在dispatchMessage中执行了耗时操作。我们通过给主线程的Looper设置一个Printer，打点统计dispatchMessage方法执行的时间，如果超出阀值，表示发生卡顿，则dump出各种信息，提供开发者分析性能瓶颈。

@Override
public void println(String x) {
    if (!mStartedPrinting) {
        mStartTimeMillis = System.currentTimeMillis();
        mStartThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        mStartedPrinting = true;
        startDump();
    } else {
        final long endTime = System.currentTimeMillis();
        mStartedPrinting = false;
        if (isBlock(endTime)) {
            notifyBlockEvent(endTime);
        }
        stopDump();
    }
}

private boolean isBlock(long endTime) {
    return endTime - mStartTimeMillis > mBlockThresholdMillis;
}


private void startDump() {
//收集栈信息
    if (null != BlockCanaryCore.get().threadStackSampler) {
        BlockCanaryCore.get().threadStackSampler.start();
    }

    if (null != BlockCanaryCore.get().cpuSampler) {
        BlockCanaryCore.get().cpuSampler.start();
    }
}

private void stopDump() {
    if (null != BlockCanaryCore.get().threadStackSampler) {
        BlockCanaryCore.get().threadStackSampler.stop();
    }

    if (null != BlockCanaryCore.get().cpuSampler) {
        BlockCanaryCore.get().cpuSampler.stop();
    }
}
//具体收集算法

 protected void doSample() {
//        Log.d("BlockCanary", "sample thread stack: [" + mThreadStackEntries.size() + ", " + mMaxEntryCount + "]");
        StringBuilder stringBuilder = new StringBuilder();

        // Fetch thread stack info
        for (StackTraceElement stackTraceElement : mThread.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString())
                    .append(Block.SEPARATOR);
        }

        // Eliminate obsolete entry
        synchronized (mThreadStackEntries) {
            if (mThreadStackEntries.size() == mMaxEntryCount && mMaxEntryCount > 0) {
                mThreadStackEntries.remove(mThreadStackEntries.keySet().iterator().next());
            }
            mThreadStackEntries.put(System.currentTimeMillis(), stringBuilder.toString());
        }
    }



