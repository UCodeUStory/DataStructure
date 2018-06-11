### Handler内存泄露原理与解决
    
    
    public class LeakCanaryActivity extends AppCompatActivity
    
        private  Handler mHandler;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
    
                }
            };
    
            Message message = Message.obtain();
            message.what = 1;
            mHandler.sendMessageDelayed(message,10*60*1000);
        }
    
    }

这段代码的逻辑很简单，mHandler延时了10分钟发送消息，类似的代码在我们的项目中也经常出现，但是这样的代码会出现一个问题。

1.问题

    我们在项目中集成 Square 的开源库 LeakCanary,有关这个库的介绍及使用请看：Github.LeakCanary。
    
    我们首先打开 LeakCanaryActivity ，然后按返回键将这个Activity finish 掉。等待几秒屏幕上会弹出提醒和通知，这说明此时发生了内存泄露的现象。

2.原因

    究竟是什么时候发生了内存泄露的问题呢？
    
    我们知道在Java中，非静态内部类会隐性地持有外部类的引用，二静态内部类则不会。在上面的代码中，Message在消息队列中延时了10分钟，然后才处理该消息。而这个消息引用了Handler对象，Handler对象又隐性地持有了Activity的对象，当发生GC是以为 message – handler – acitivity 的引用链导致Activity无法被回收，所以发生了内存泄露的问题。
    
    危害
    
    众所周知，内存泄露在 Android 开发中是一个比较严重的问题，系统给每一个应用分配的内存是固定的，一旦发生了内存泄露，就会导致该应用可用内存越来越小，严重时会发生 OOM 导致 Force Close。

3.这个问题该如何解决呢？

使用弱引用

首先我们需要理解一下相关概念：

强引用：强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。
软应用：如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。软引用可用来实现内存敏感的高速缓存。
弱引用：弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
用更直白的语言描述就是，java对于 强引用 的对象,就绝不收回，对于 软引用 的对象，是能不收回就不收回，这里的能不收回就是指内存足够的情况，对于 弱引用 的对象，是发现就收回，但是一般情况下不会发现。

很显然，出现内存泄露问提的原因，就是 Handler 对 Activity 是强引用，导致 GC 在回收 Activity 时无法回收。为了解决这个问题，我们可以把 Handler 对 Activity 弱引用，这样 GC 就能把 Activity 及时的回收，从而杜绝了内存泄露的问题。

    public class NoLeakActivity extends AppCompatActivity {
    
        private NoLeakHandler mHandler;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            mHandler = new NoLeakHandler(this);
    
            Message message = Message.obtain();
    
            mHandler.sendMessageDelayed(message,10*60*1000);
        }
    
        private static class NoLeakHandler extends Handler{
            private WeakReference<NoLeakActivity> mActivity;
    
            public NoLeakHandler(NoLeakActivity activity){
                mActivity = new WeakReference<>(activity);
            }
    
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        }
    }


2.及时清除消息

    在原因中我们说到，正是因为被延时处理的 message 持有 Handler 的引用，Handler 持有对 Activity 的引用，形成了message – handler – activity 这样一条引用链，导致 Activity 的泄露。因此我们可以尝试在当前界面结束时将消息队列中未被处理的消息清除，从源头上解除了这条引用链，从而使 Activity 能被及时的回收。
    
    public class LeakCanaryActivity extends AppCompatActivity {
    
        private  Handler mHandler;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
    
                }
            };
    
            Message message = Message.obtain();
            message.what = 1;
            mHandler.sendMessageDelayed(message,10*60*1000);
        }
    
        @Override
        protected void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacksAndMessages(null);
        }
    }

