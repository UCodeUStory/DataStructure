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

        如果一个对象只具有弱引用，那么在垃圾回收器线程扫描的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。
        不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象，因此可以用于缓存
        弱引用也可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。

        ***** WeakReference：防止内存泄漏，要保证内存被虚拟机回收


2. 应用
   1. 解决Handler内存泄露
   2. 实现缓存，Android 官方建议使用WeakReference 而不是softReference
   
#### 下面Java和Android环境下SoftReference 和WeakReference使用
       
       
       public static void main(String[] args) throws InterruptedException {
       
               initsoftReference();
               initweakReference();
               
               Thread.sleep(2000);
               System.gc();
               
               if (softReference.get() == null) {
                   System.out.println("SoftReference : " + "null");
               }else{
                   System.out.println("SoftReference : " + softReference.get());
               }
       
       
               if (weakReference.get() == null) {
                   System.out.println("WeakReference : " + "null");
               }else{
                   System.out.println("WeakReference : " + weakReference.get());
               }
               
           }
       
           private static void initsoftReference() {
               softReference = new SoftReference(value_soft);
               value_soft = null;
           }
       
           private static void initweakReference() {
               weakReference = new WeakReference(value_weak);
               value_weak = null;
           }
      
#### 从上面的情况，我们还让你容易可以观察Android环境下与纯Java环境下两者直接的输出结果不同！
在Android环境下WeakReference 与SoftReference 两者输出结果一样。其实对于手机系统存在多应用，又对于内存是比较敏感的，自然对于内存释放会更加严格。
试想一下，如果众多对象使用 SoftReference引用，大部分都是这也是为什么google不建议SoftReference 的原因之一。

