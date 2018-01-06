## 最好的单例模式

### 具备下列性质

    1.线程安全
    2.效率不能太低
    3.支持lazy
   

### 例子1，线程不安全

    public class UnsafeLazyInitialization {
        private static Instance instance;
        public static Instance getInstance() {
        if (instance == null) //1：A线程执行
            instance = new Instance(); //2：B线程执行
            return instance;
        }
    }
### 例子2，线程安全，多频调用效率极低

    public class SafeLazyInitialization {
    
        private static Instance instance;
    
        public synchronized static Instance getInstance() {
            if (instance == null)
                instance = new Instance();
            return instance;
        }
    }
    在早期的JVM中，synchronized（甚至是无竞争的synchronized）
    存在这巨大的性能开销。因此，人们想出了一个“聪明”的技巧：双重检查锁定（double-checked locking）

### 例子3，双重检查锁定 (还是存在问题)

    如果第一次检查instance不为null，那么就不需要执行下面的加锁和初始化操作。
    因此可以大幅降低synchronized带来的性能开销
    
    public class DoubleCheckedLocking {                      //1
        private static Instance instance;                    //2

        public static Instance getInstance() {               //3
            if (instance == null) {                          //4:第一次检查
                synchronized (DoubleCheckedLocking.class) {  //5:加锁
                    if (instance == null)                    //6:第二次检查
                        instance = new Instance();           //7:问题的根源出在这里
                }                                            //8
            }                                                //9
            return instance;                                 //10
        }                                                    //11
    }                                                        //12
    
    但这是一个错误的优化！在线程执行到第4行代码读取到instance不为null时，
    instance引用的对象有可能还没有完成初始化。
    
    问题的根源
    
    前面的双重检查锁定示例代码的第7行（instance = new Singleton();）创建一个对象。这一行代码可以分解为如下的三行伪代码：
    memory = allocate();   //1：分配对象的内存空间
    ctorInstance(memory);  //2：初始化对象
    instance = memory;     //3：设置instance指向刚分配的内存地址
    上面三行伪代码中的2和3之间，可能会被重排序（在一些JIT编译器上，这种重排序是真实发生的，详情见参考文献1的“Out-of-order writes”部分）。2和3之间重排序之后的执行时序如下：
    memory = allocate();   //1：分配对象的内存空间
    instance = memory;     //3：设置instance指向刚分配的内存地址
                           //注意，此时对象还没有被初始化！
    ctorInstance(memory);  //2：初始化对象
    根据《The Java Language Specification, Java SE 7 Edition》（后文简称为java语言规范），所有线程在执行java程序时必须要遵守intra-thread 
    semantics。intra-thread semantics保证重排序不会改变单线程内的程序执行结果。换句话来说，intra-thread semantics允许那些在单线程内，不会改变单线程程序执行结果的重排序。
    上面三行伪代码的2和3之间虽然被重排序了，但这个重排序并不会违反intra-thread semantics。
    这个重排序在没有改变单线程程序的执行结果的前提下，可以提高程序的执行性能。
    
### 例子4，基于volatile的双重检查锁定的解决方案，禁止重排序

    public class SafeDoubleCheckedLocking {
        private volatile static Instance instance;
    
        public static Instance getInstance() {
            if (instance == null) {
                synchronized (SafeDoubleCheckedLocking.class) {
                    if (instance == null)
                        instance = new Instance();//instance为volatile，现在没问题了
                }
            }
            return instance;
        }
    }
        
注意，这个解决方案需要JDK5或更高版本（因为从JDK5开始使用新的JSR-133内存模型规范，这个规范增强了volatile的语义）。
当声明对象的引用为volatile后，“问题的根源”的三行伪代码中的2和3之间的重排序，在多线程环境中将会被禁止。
这个方案本质上是通过禁止上图中的2和3之间的重排序，来保证线程安全的延迟初始化。


### 例子5，基于类初始化的解决方案

    public class InstanceFactory {
        private static class InstanceHolder {
            public static Instance instance = new Instance();
        }
    
        public static Instance getInstance() {
            return InstanceHolder.instance ;  //这里将导致InstanceHolder类被初始化
        }
    }
   JVM在类的初始化阶段（即在Class被加载后，且被线程使用之前），会执行类的初始化。在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化。
   基于这个特性，可以实现另一种线程安全的延迟初始化方案（这个方案被称之为Initialization On Demand Holder idiom）

### 总结

延迟初始化降低了初始化类或创建实例的开销，但增加了访问被延迟初始化的字段的开销。在大多数时候，
正常的初始化要优于延迟初始化。如果确实需要对实例字段使用线程安全的延迟初始化，请使用上面介绍
的基于volatile的延迟初始化的方案；如果确实需要对静态字段使用线程安全的延迟初始化，请使用上面介绍的基于类初始化的方案。