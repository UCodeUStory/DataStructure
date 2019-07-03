#### 享元模式

1. 介绍

    享元模式属于结构型模式。
    
    享元模式是池技术的重要实现方式，它可以减少重复对象的创建，使用缓存来共享对象，从而降低内存的使用。
    
    细粒度的对象其状态可以分为两种：内部状态和外部状态。 
    
    内部状态:对象可共享出来的信息，存储在享元对象内部并且不会随环境的改变而改变。 
    
    外部状态:对象依赖的一个标记是随环境改变而改变的,并且不可共享。

2. Java 中享元模式，我们接触到最多的还是Java中的String。如果字符串常量池中有此字符则直接返回，否则先在字符串常量池中创建字符串。 


        String例子
        String s0 = "abc";
        String s1 = "abc";

        System.out.println("s0 == s1 " + s0 == s1);
        
3.Message类本身就组织了一个栈结构的缓冲池。并使用obtain()方法和recycler()方法来取出和放入


    /*package*/ Message next;

    private static final Object sPoolSync = new Object();
    private static Message sPool;
    private static int sPoolSize = 0;

    private static final int MAX_POOL_SIZE = 50;

    private static boolean gCheckRecycle = true;
    在解释这段代码前，需要先明确两点：sPool声明为private static Message sPool;
    
    next声明为/*package*/ Message next;。即前者为该类所有示例共享，后者则每个实例都有。 
    
    public static Message obtain() {
        synchronized (sPoolSync) {
              /**请注意！我们可以看到Message中有一个next字段指向下一个Message，这里就明白了，Message消息池中
                 没有使用Map这样的容器，而是使用的链表！
              */
              if (sPool != null) {
                  Message m = sPool;
                  sPool = m.next;
                  m.next = null;
                  m.flags = 0; // clear in-use flag
                  sPoolSize--;
                  return m;
              }
          }
          return new Message();
    }


    void recycleUnchecked() {
            // Mark the message as in use while it remains in the recycled object pool.
            // Clear out all other details.
            flags = FLAG_IN_USE;
            what = 0;
            arg1 = 0;
            arg2 = 0;
            obj = null;
            replyTo = null;
            sendingUid = -1;
            when = 0;
            target = null;
            callback = null;
            data = null;
    
            synchronized (sPoolSync) {
                if (sPoolSize < MAX_POOL_SIZE) {
                    //将自身链入头部,所以next 指向之前的链表头
                    next = sPool;
                    //链表的头指向自身
                    sPool = this;
                    //sPoolSize 多了一个
                    sPoolSize++;
                }
            }
        }
        
        spool 和 next 在执行 recycleUnchecked 被赋值  也就是当前对象执行完 我们去回收他，
        
        Message3   next=Message2   spool=Message3     Message2  next = Message  spool = Message2     Message  next= null spool = Message 
        
        
4. 开发时 多使用  Message.obtain().sendToMessage()形式发送消息可以提高性能,不要再使用Message()

