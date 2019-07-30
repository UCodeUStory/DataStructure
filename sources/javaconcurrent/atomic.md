#### Atomic原理分析

Atomic类 在JDK5.0之前，想要实现无锁无等待的算法是不可能的，除非用本地库，自从有了Atomic变量类后，这成为可能。下面这张图是java.util.concurrent.atomic包下的类结构。

    
    传统锁的问题
    我们先来看一个例子：计数器（Counter），采用Java里比较方便的锁机制synchronized关键字，初步的代码如下：
    
    class Counter {
            
        private int value;
     
        public synchronized int getValue() {
            return value;
        }
     
        public synchronized int increment() {
            return ++value;
        }
     
        public synchronized int decrement() {
            return --value;
        }
    } 
    
    其实像这样的锁机制，满足基本的需求是没有问题的了，但是有的时候我们的需求并非这么简单，我们需要更有效，更加灵活的机制，synchronized关键字是基于阻塞的锁机制，也就是说当一个线程拥有锁的时候，访问同一资源的其它线程需要等待，直到该线程释放锁，这里会有些问题：首先，如果被阻塞的线程优先级很高很重要怎么办？其次，如果获得锁的线程一直不释放锁怎么办？（这种情况是非常糟糕的）。还有一种情况，如果有大量的线程来竞争资源，那CPU将会花费大量的时间和资源来处理这些竞争（事实上CPU的主要工作并非这些），同时，还有可能出现一些例如死锁之类的情况，最后，其实锁机制是一种比较粗糙，粒度比较大的机制，相对于像计数器这样的需求有点儿过于笨重，因此，对于这种需求我们期待一种更合适、更高效的线程安全机制。
    
    
    硬件同步策略
    现在的处理器都支持多重处理，当然也包含多个处理器共享外围设备和内存，同时，加强了指令集以支持一些多处理的特殊需求。特别是几乎所有的处理器都可以将其他处理器阻塞以便更新共享变量。
    
    
    
    Compare and swap(CAS)
    当前的处理器基本都支持CAS，只不过每个厂家所实现的算法并不一样罢了，每一个CAS操作过程都包含三个运算符：一个内存地址V，一个期望的值A和一个新值B，操作的时候如果这个地址上存放的值等于这个期望的值A，则将地址上的值赋为新值B，否则不做任何操作。CAS的基本思路就是，如果这个地址上的值和期望的值相等，则给其赋予新值，否则不做任何事儿，但是要返回原值是多少。我们来看一个例子，解释CAS的实现过程（并非真实的CAS实现）：
    
    以下模拟实现CAS,真实不是这样的，这里只是为了解释
    
    class SimulatedCAS {
        private int value;
     
        public synchronized int getValue() {
            return value;
        }
        public synchronized int compareAndSwap(int expectedValue, int newValue) {
            int oldValue = value;
            if (value == expectedValue)
                value = newValue;
            return oldValue;
        }
    }
    
    下面是一个用CAS实现的Counter
    public class CasCounter {
        private SimulatedCAS value;
     
        public int getValue() {
            return value.getValue();
        }
     
        public int increment() {
            int oldValue = value.getValue();
            while (value.compareAndSwap(oldValue, oldValue + 1) != oldValue)
                oldValue = value.getValue();
            return oldValue + 1;
        }



标量类：AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference
数组类：AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
更新器类：AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater
复合变量类：AtomicMarkableReference，AtomicStampedReference
第一组AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference这四种基本类型用来处理布尔，整数，长整数，对象四种数据，其内部实现不是简单的使用synchronized，而是一个更为高效的方式CAS (compare and swap) + volatile和native方法，从而避免了synchronized的高开销，执行效率大为提升。我们来看个例子，与我们平时i++所对应的原子操作为：getAndIncrement()


注意：volatile 可以保证可见性，但不能保证原子性。所以不能保证线程安全

CAS线程安全
说了半天，我们要回归到最原始的问题了：这样怎么实现线程安全呢？请大家自己先考虑一下这个问题，其实我们在语言层面是没有做任何同步的操作的，大家也可以看到源码没有任何锁加在上面，可它为什么是线程安全的呢？这就是Atomic包下这些类的奥秘：语言层面不做处理，我们将其交给硬件—CPU和内存，利用CPU的多处理能力，实现硬件层面的阻塞，再加上volatile变量的特性即可实现基于原子操作的线程安全。所以说，CAS并不是无阻塞，只是阻塞并非在语言、线程方面，而是在硬件层面，所以无疑这样的操作会更快更高效


AtomicBoolean类 getAndSet 方法和 compareAndSet方法的区别，这两个方法的区别在java的文档中记录的很明确了

compareAndSet：如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。这里需要注意的是这个方法的返回值实际上是是否成功修改，而与之前的值无关。

getAndSet ：以原子方式设置为给定值，并返回以前的值。


#### AtomicReference使用？

 AtomicReference和AtomicInteger非常类似，不同之处就在于AtomicInteger是对整数的封装，而AtomicReference则对应普通的对象引用。也就是它可以保证你在修改对象引用时的线程安全性。在介绍AtomicReference的同时，我希望同时提出一个有关原子操作的逻辑上的不足。

   之前我们说过，线程判断被修改对象是否可以正确写入的条件是对象的当前值和期望是否一致。这个逻辑从一般意义上来说是正确的。但有可能出现一个小小的例外，就是当你获得对象当前数据后，在准备修改为新值前，对象的值被其他线程连续修改了2次，而经过这2次修改后，对象的值又恢复为旧值。这样，当前线程就无法正确判断这个对象究竟是否被修改过。如图4.2所示，显示了这种情况。


对象值被反复修改回原数据 一般来说，发生这种情况的概率很小。而且即使发生了，可能也不是什么大问题。比如，我们只是简单得要做一个数值加法，即使在我取得期望值后，这个数字被不断的修改，只要它最终改回了我的期望值，我的加法计算就不会出错。也就是说，当你修改的对象没有过程的状态信息，所有的信息都只保存于对象的数值本身。

    但是，在现实中，还可能存在另外一种场景。就是我们是否能修改对象的值，不仅取决于当前值，还和对象的过程变化有关，这时，AtomicReference就无能为力了。

打一个比方，如果有一家蛋糕店，为了挽留客户，绝对为贵宾卡里余额小于20元的客户一次性赠送20元，刺激消费者充值和消费。但条件是，每一位客户只能被赠送一次。

现在，我们就来模拟这个场景，为了演示AtomicReference，我在这里使用AtomicReference实现这个功能。首先，我们模拟用户账户余额。

定义用户账户余额： 


static AtomicReference<Integer> money=newAtomicReference<Integer>();
// 设置账户初始值小于20，显然这是一个需要被充值的账户
money.set(19);
　　

接着，我们需要若干个后台线程，它们不断扫描数据，并为满足条件的客户充值。


 //模拟多个线程同时更新后台数据库，为用户充值
 for(int i = 0 ; i < 3 ; i++) {            
     new Thread(){
         publicvoid run() {
            while(true){
                while(true){
                   Integer m=money.get();
                   if(m<20){
                        if(money.compareAndSet(m, m+20)){
                  System.out.println("余额小于20元，充值成功，余额:"+money.get()+"元");
                             break;
                        }
                    }else{
                        //System.out.println("余额大于20元，无需充值");
                         break ;
                    }
                 }
             }
         }
     }.start();
 }
　　

上述代码第8行，判断用户余额并给予赠予金额。如果已经被其他用户处理，那么当前线程就会失败。因此，可以确保用户只会被充值一次。

 此时，如果很不幸的，用户正好正在进行消费，就在赠予金额到账的同时，他进行了一次消费，使得总金额又小于20元，并且正好累计消费了20元。使得消费、赠予后的金额等于消费前、赠予前的金额。这时，后台的赠予进程就会误以为这个账户还没有赠予，所以，存在被多次赠予的可能。下面，模拟了这个消费线程：

 

 //用户消费线程，模拟消费行为
 new Thread() {
     public voidrun() {
         for(inti=0;i<100;i++){
            while(true){
                Integer m=money.get();
                 if(m>10){
                    System.out.println("大于10元");
                    if(money.compareAndSet(m, m-10)){
                        System.out.println("成功消费10元，余额:"+money.get());
                        break;
                    }
               }else{
                    System.out.println("没有足够的金额");
                    break;
                 }
             }
             try{Thread.sleep(100);} catch (InterruptedException e) {}
         }
     }
 }.start();
　　上述代码中，消费者只要贵宾卡里的钱大于10元，就会立即进行一次10元的消费。执行上述程序，得到的输出如下：

  

余额小于20元，充值成功，余额:39元
大于10元
成功消费10元，余额:29
大于10元
成功消费10元，余额:19
余额小于20元，充值成功，余额:39元
大于10元
成功消费10元，余额:29
大于10元
成功消费10元，余额:39
余额小于20元，充值成功，余额:39元

   从这一段输出中，可以看到，这个账户被先后反复多次充值。其原因正是因为账户余额被反复修改，修改后的值等于原有的数值。使得CAS操作无法正确判断当前数据状态




#### 用AtomicStampedReference解决ABA问题

线程1准备用CAS将变量的值由A替换为B，在此之前，线程2将变量的值由A替换为C，又由C替换为A，然后线程1执行CAS时发现变量的值仍然为A，所以CAS成功。但实际上这时的现场已经和最初不同了，尽管CAS成功，但可能存在潜藏的问题，例如下面的例子：



现有一个用单向链表实现的堆栈，栈顶为A，这时线程T1已经知道A.next为B，然后希望用CAS将栈顶替换为B：

head.compareAndSet(A,B);

在T1执行上面这条指令之前，线程T2介入，将A、B出栈，再pushD、C、A，此时堆栈结构如下图，而对象B此时处于游离状态：



此时轮到线程T1执行CAS操作，检测发现栈顶仍为A，所以CAS成功，栈顶变为B，但实际上B.next为null，所以此时的情况变为：



其中堆栈中只有B一个元素，C和D组成的链表不再存在于堆栈中，平白无故就把C、D丢掉了。

以上就是由于ABA问题带来的隐患，各种乐观锁的实现中通常都会用版本戳version来对记录或对象标记，避免并发操作带来的问题，在Java中，AtomicStampedReference<E>也实现了这个作用，它通过包装[E,Integer]的元组来对对象标记版本戳stamp，从而避免ABA问题，例如下面的代码分别用AtomicInteger和AtomicStampedReference来对初始值为100的原子整型变量进行更新，AtomicInteger会成功执行CAS操作，而加上版本戳的AtomicStampedReference对于ABA问题会执行CAS失败：
    
    import java.util.concurrent.TimeUnit;
    import java.util.concurrent.atomic.AtomicInteger;
    import java.util.concurrent.atomic.AtomicStampedReference;
    
    public class ABA {
            private static AtomicInteger atomicInt = new AtomicInteger(100);
            private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);
    
            public static void main(String[] args) throws InterruptedException {
                    Thread intT1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                    atomicInt.compareAndSet(100, 101);
                                    atomicInt.compareAndSet(101, 100);
                            }
                    });
    
                    Thread intT2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                    try {
                                            TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                    }
                                    boolean c3 = atomicInt.compareAndSet(100, 101);
                                    System.out.println(c3); // true
                            }
                    });
    
                    intT1.start();
                    intT2.start();
                    intT1.join();
                    intT2.join();
    
                    Thread refT1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                    try {
                                            TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                    }
                                    atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                                    atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                            }
                    });
    
                    Thread refT2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                    int stamp = atomicStampedRef.getStamp();
                                    try {
                                            TimeUnit.SECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                    }
                                    boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                                    System.out.println(c3); // false
                            }
                    });
    
                    refT1.start();
                    refT2.start();
            }
    }