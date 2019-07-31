### CAS自旋锁实现以及应用

- CAS 比较交换 compareAndSet，而底层使用了Unsafe调用的
  
       public final boolean compareAndSet(int expect, int update) {
              return U.compareAndSwapInt(this, VALUE, expect, update);
       }
  
    类似AtomicInteger里面都是用了compareAndSet
    
    
    我们想使用CAS实现复杂对象引用可以使用atomicReference
    
    并通过compareAndSet实现CAS,如果想实现一个线程安全的操作可以使用



- 原生的内部自旋实现原理


    private final static AtomicBoolean flag = new AtomicBoolean();
        if(flag.compareAndSet(false,true)){
            init();
        }
    比如 AtomicInteger 可以用在计数器中，多线程环境中，保证计数准确。
    

    // 自旋实现，线程是安全的 返回的是旧值
    public final int getAndIncrement() {
        for (;;) {
            int current = get();
            int next = current + 1;
            if (compareAndSet(current, next))
                return current;
        }
    }
    // 自旋实现，线程是安全的  返回的是新值
    public final int incrementAndGet() {
        for (;;) {
            int current = get();
            int next = current + 1;
            if (compareAndSet(current, next))
                return next;
        }
##### 实际应用


- 1通过自旋方式实现线程安全可以应用到实际业务中

    
    //多线程无参数可以提高效率，减少线程因为同步机制阻塞，等待占用资源， 而使用CAS,其他线程相当于就可以干别的任务去了
        //如果是有参数的，可以使用final AtomicReference<Params<T>[]> params;将参数存储起来，
        //然后参数的存储

    /**
     * 通过这个方法可以比较地址是否被更改过，因为我们用的是数组，并且每次更改都会有个新的数组代替，所以地址会变的
     * internal fun add(rs: Param<T>): Boolean {
            while (true) {
            val a = params.get()

            val len = a.size
            val b = arrayOfNulls<Param<*>>(len + 1)
            System.arraycopy(a, 0, b, 0, len)
            b[len] = rs
            if (params.compareAndSet(a, b)) {
            return true
            }
        }
        }
     */
    private fun testThread() {
        // 旧值不等于0,证明已经发送过, 来一个线程count值就会被+1，相当于计数器+1，同时达到排队模式
        if (count.getAndIncrement() != 0) {
            return
        }
        var missed = 1
        while (true) { //这里使用while循环，当missed 有很多是，让其再次执行
            Thread.sleep(3000)
            Log.i("rxjava", "id=" + Thread.currentThread().id)
            missed = count.addAndGet(-missed)
            if (missed == 0) {
                break
            }
        }
    }
    
- 2 当我们想根据之前的对象某些值，重新计算一个新的值，然后保存到新的对象中    
    
    
    /**
      这样下面的代码就是线程安全的，但是可能出现ABA情况，解决这个情况可以使用AtomicStampReference
    **/
    
        //自旋的一种实现方式
        fun updateObject(factory(oldValue)->newValue){
            
            for(;;){
            
              val oldValue = field.get()
              
              val newValue = factory()  //可以是一个复杂逻辑
              
              if(field.compareAndSet(oldValue,newValue)){
                return true
              }
              
            }
        
        }