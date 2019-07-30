### CAS自旋锁实现


  CAS 比较交换 compareAndSet，而地城使用了Unsafe调用的
  
       public final boolean compareAndSet(int expect, int update) {
              return U.compareAndSwapInt(this, VALUE, expect, update);
       }
  
类似AtomicInteger里面都是用了compareAndSet


我们想使用CAS实现复杂对象引用可以使用atomicReference

并通过compareAndSet实现CAS,如果想实现一个线程安全的操作可以使用

//当我们想根据之前的对象某些值，重新计算一个新的值，然后保存到新的对象中
/**
  这样下面的代码就是线程安全的，但是可能出现ABA情况，解决这个情况可以使用AtomicStampReference
**/
fun updateObject(factory(oldValue)->newValue){
    
    for(;;){
    
      val oldValue = field.get()
      
      val newValue = factory()  //可以是一个复杂逻辑
      
      if(field.compareAndSet(oldValue,newValue)){
        return true
      }
      
    }

}

    
    使用场景
    CAS 适合简单对象的操作，比如布尔值、整型值等；
    CAS 适合冲突较少的情况，如果太多线程在同时自旋，那么长时间循环会导致 CPU 开销很大；
    比如 AtomicBoolean 可以用在这样一个场景下，系统需要根据一个布尔变量的状态属性来判断是否需要执行一些初始化操作，如果是多线程的环境下，避免多次重复执行，可以使用 AtomicBoolean 来实现，伪代码如下：
    
    private final static AtomicBoolean flag = new AtomicBoolean();
        if(flag.compareAndSet(false,true)){
            init();
        }
    比如 AtomicInteger 可以用在计数器中，多线程环境中，保证计数准确。