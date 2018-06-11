### 强、软、弱、虚、引用

一、java.lang.ref包中提供了几个类：SoftReference类、WeakReference类和PhantomReference类，它们分别代表软引用、弱引用和虚引用。ReferenceQueue类表示引用队列，它可以和这三种引用类联合使用，以便跟踪Java虚拟机回收所引用的对象的活动。
#### 强引用
1. 关于强引用引用的场景

        直接new出来的对象
        String str = new String(“yc”);

2. 强引用介绍

        强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。
        
        通过引用，可以对堆中的对象进行操作。在某个函数中，当创建了一个对象，该对象被分配在堆中，通过这个对象的引用才能对这个对象进行操作。

3. 强引用的特点
    
        强引用可以直接访问目标对象。
        强引用所指向的对象在任何时候都不会被系统回收。JVM宁愿抛出OOM异常，也不会回收强引用所指向的对象。
        强引用可能导致内存泄露。
        
#### 软引用

1. 关于SoftReference软引用

            SoftReference：软引用–>当虚拟机内存不足时，将会回收它指向的对象；需要获取对象时，可以调用get方法。
            
            可以通过java.lang.ref.SoftReference使用软引用。一个持有软引用的对象，不会被JVM很快回收，JVM会根据当前堆的使用情况来判断何时回收。当堆的使用率临近阈值时，才会回收软引用的对象。

2. 软引用应用场景

        例如从网络上获取图片，然后将获取的图片显示的同时，通过软引用缓存起来。当下次再去网络上获取图片时，首先会检查要获取的图片缓存中是否存在，若存在，直接取出来，不需要再去网络上获取。
        
        View view = findViewById(R.id.button);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        Drawable drawable = new BitmapDrawable(bitmap);
        SoftReference<Drawable> drawableSoftReference = new SoftReference<Drawable>(drawable);
        if(drawableSoftReference != null) {
            view.setBackground(drawableSoftReference.get());
        }
        
3. 这样使用软引用好处，正常是用来处理图片这种占用内存大的情况
        
        通过软引用的get()方法，取得drawable对象实例的强引用，发现对象被未回收。在GC在内存充足的情况下，不会回收软引用对象。此时view的背景显示.
        
        实际情况中,我们会获取很多图片.然后可能给很多个view展示, 这种情况下很容易内存吃紧导致oom,内存吃紧，系统开始会GC。这次GC后，drawables.get()不再返回Drawable对象，而是返回null，这时屏幕上背景图不显示，说明在系统内存紧张的情况下，软引用被回收。
        
        使用软引用以后，在OutOfMemory异常发生之前，这些缓存的图片资源的内存空间可以被释放掉的，从而避免内存达到上限，避免Crash发生。
        
4. 注意避免软引用获取对象为null
        
        在垃圾回收器对这个Java对象回收前，SoftReference类所提供的get方法会返回Java对象的强引用，一旦垃圾线程回收该Java对象之后，get方法将返回null。所以在获取软引用对象的代码中，一定要判断是否为null，以免出现NullPointerException异常导致应用崩溃
        
        
#### 弱引用WeakReference


弱引用–>随时可能会被垃圾回收器回收，不一定要等到虚拟机内存不足时才强制回收。要获取对象时，同样可以调用get方法。

1. 特点

        如果一个对象只具有弱引用，那么在垃圾回收器线程扫描的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
        
        弱引用也可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。

        ***** WeakReference：防止内存泄漏，要保证内存被虚拟机回收


2. handler为什么这样会造成内存泄漏

        这种情况就是由于android的特殊机制造成的：当一个android主线程被创建的时候，同时会有一个Looper对象被创建，而这个Looper对象会实现一个MessageQueue(消息队列)，当我们创建一个handler对象时，而handler的作用就是放入和取出消息从这个消息队列中，每当我们通过handler将一个msg放入消息队列时，这个msg就会持有一个handler对象的引用。
        
        因此当Activity被结束后，这个msg在被取出来之前，这msg会继续存活，但是这个msg持有handler的引用，而handler在Activity中创建，会持有Activity的引用，因而当Activity结束后，Activity对象并不能够被gc回收，因而出现内存泄漏。

3. 根本原因

        Activity在被结束之后，MessageQueue并不会随之被结束，如果这个消息队列中存在msg，则导致持有handler的引用，但是又由于Activity被结束了，msg无法被处理，从而导致永久持有handler对象，handler永久持有Activity对象，于是发生内存泄漏。但是为什么为static类型就会解决这个问题呢？
        
        因为在java中所有非静态的对象都会持有当前类的强引用，而静态对象则只会持有当前类的弱引用。
        
        声明为静态后，handler将会持有一个Activity的弱引用，而弱引用会很容易被gc回收，这样就能解决Activity结束后，gc却无法回收的情况。

4. 弱引用解决办法

        代码如下所示
        private MyHandler handler = new MyHandler(this);
        private static class MyHandler extends Handler{
            WeakReference<FirstActivity> weakReference;
            MyHandler(FirstActivity activity) {
                weakReference = new WeakReference<>(activity);
            }
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                }
            }
        }
