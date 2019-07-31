### RxJava 源码分析


#### Rxjava中的设计模式

1. 适配器模式

    将一个接口转换成客户希望的另一个接口，使接口不兼容的那些类可以一起工作，其别名为包装器(Wrapper)。
    
    
    
    例子 Observable.create(new ObservableOnSubscribe<String>() {
                   @Override
                   public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                       //源数据的分法逻辑
                       emitter.onNext("Android");
                       emitter.onNext("ios");
                       emitter.onNext("Other");
                       emitter.onComplete();
                   }
               })
                       .map(new Function<String, String>() {
                           @Override
                           public String apply(String s) {
                               return s+s;
                           }
                       })
                       .subscribe(new Observer<String>() {
                           @Override
                           public void onSubscribe(Disposable d) {
                               // 提供取消钩子
                           }
       
                           @Override
                           public void onNext(String s) {
                               Log.d(TAG, "onNext: "+s);
                           }
       
                           @Override
                           public void onError(Throwable e) {
       
                           }
       
                           @Override
                           public void onComplete() {
       
                           }
                       });
                       
                       
       
  Observable 实现了 ObservableSource接口
  
  Observable.create 方法 我们期望返回一个 Observable接口对象(因为我们想链式调用)，但输入的却是一个实现 ObservableOnSubscribe 接口的对象
  
  我们通过增加一个新的适配器类来解决接口不兼容的问题，使得原本没有任何关系的类可以协同工作。
  
  所以通过ObservableCreate进行包装适配，先让ObservableCreate继承Observable，再关联ObservableOnSubscribe，然后返回ObservableCreate，
  
  当观察者调用subscribe 方法 会调用 subscribeActual，同时绑定观察者和被观察者，并执行ObservableOnSubscribe重写subscribe方法
  
  source（ObservableEmitter）.subscribe(parent)，这里就是调我们create传入的接口
  
  
    1. public final void subscribe(Observer<? super T> observer) {
    2. subscribeActual
    3. protected void subscribeActual(Observer<? super T> observer) {
               CreateEmitter<T> parent = new CreateEmitter<T>(observer);
               observer.onSubscribe(parent);
       
               try {
                   //source就是ObservableOnSubscribe
                   source.subscribe(parent);
               } catch (Throwable ex) {
                   Exceptions.throwIfFatal(ex);
                   parent.onError(ex);
               }
       }
    4. 观察者，调用OnSubscrible实际上就是想获得观察者代理，这样就能通过代理做取消操作
    5. 调用数据源的subscribe
    6. 数据源通过观察者适配器传递给观察者
    
    总结：数据源（是一个接口的实现，用来发射数据，以及怎么发射，单发射的实际是由别人决定的，这里就相当于实现了一个onsubscribe方法直接）
    观察者：想要获取数据源的数据，但是却不能直接去获取，需要一个中间人可能是像CreateEmitter这种发射器；而为什么不直接产生订阅关系，
    因为我们需要加一些取消的逻辑，这样可以减少用户的编写逻辑
  
      //ObservableOnSubscribe 作为上层使用
      public interface ObservableOnSubscribe<T> {
           void subscribe(@NonNull ObservableEmitter<T> e) throws Exception;
      }
  
      // ObservableOnSubscribe作为入参
      public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
          ObjectHelper.requireNonNull(source, "source is null");
          return RxJavaPlugins.onAssembly(new ObservableCreate<T>(source));
      }
      
      //ObservableCreate源码 作为适配器
      public final class ObservableCreate<T> extends Observable<T> {
          final ObservableOnSubscribe<T> source;
      
          public ObservableCreate(ObservableOnSubscribe<T> source) {
              this.source = source;
          }
      
          @Override
          protected void subscribeActual(Observer<? super T> observer) {
              CreateEmitter<T> parent = new CreateEmitter<T>(observer);
              observer.onSubscribe(parent);
      
              try {
                  //产生订阅后开始调用源数据的处理
                  source.subscribe(parent);
              } catch (Throwable ex) {
                  Exceptions.throwIfFatal(ex);
                  parent.onError(ex);
              }
          }
   
 
      
      public final void subscribe(Observer<? super T> observer) {
             ObjectHelper.requireNonNull(observer, "observer is null");
             try {
                 observer = RxJavaPlugins.onSubscribe(this, observer);
     
                 ObjectHelper.requireNonNull(observer, "Plugin returned null Observer");
     
                 subscribeActual(observer);
             } catch (NullPointerException e) { // NOPMD
                 throw e;
             } catch (Throwable e) {
                 Exceptions.throwIfFatal(e);
                 // can't call onError because no way to know if a Disposable has been set or not
                 // can't call onSubscribe because the call might have set a Subscription already
                 RxJavaPlugins.onError(e);
     
                 NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
                 npe.initCause(e);
                 throw npe;
             }
       }
     
     
2. 同时我们适配方法调用，重写subscribeActual方法，当调用Obserable.subscribe时，再传递给subscribeActual，将Observer 传递ObservableOnSubscribe

  但ObservableOnSubscribe 的OnSubscribe 需要ObserverEmitter(因为我们要，使用onNext,OnError,OnComplete方法，去传递数据，并且我们要在每一种操作判断
  dispose状态)，所以使用CreateEmitter完成适配，所以这里也是适配器模式
  
 ** 有人会疑惑这里是不是代理模式，CreateEmitter是不是代理？
 
    答案No,首先如果CreateEmitter是代理，那么CreateEmitter和Observer应该有实现相同的接口
 
 ** 代理提供的接口和原本的要实现统一接口，代理模式的作用是不把实现直接暴露给client，而是通过代理这个层，代理能够做一些处理，判断。**
 
 ** 适配器模式体现的是适配，比如Client 需要A类提供的行为，此时我们有B类提供了一些方法可以实现，但是方法名字不一样，需要改造一下变成A,
 此时创建一个类C，实现A接口，并注入B类,这样相当于C就通过协调用B的方法，来补充到A接口方法中(所以这里C属于适配器，来协调Client 和 B)
 对适配器模式的功能很好理解，就是把一个类的接口变换成客户端所能接受的另一种接口
 
  
      
  
      //Emitter
      public interface Emitter<T> {
      
          void onNext(@NonNull T value);
          
          void onError(@NonNull Throwable error);
      
          void onComplete();
      }
      
      //ObservableEmitter
      public interface ObservableEmitter<T> extends Emitter<T> {
      
      
      //CreateEmitter
      static final class CreateEmitter<T> extends AtomicReference<Disposable>
          implements ObservableEmitter<T>, Disposable {
  
  
          private static final long serialVersionUID = -3434801548987643227L;
  
          final Observer<? super T> observer;
  
          CreateEmitter(Observer<? super T> observer) {
              this.observer = observer;
          }
  
          @Override
          public void onNext(T t) {
              if (t == null) {
                  onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                  return;
              }
              if (!isDisposed()) {
                  observer.onNext(t);
              }
          }
  
          @Override
          public void onError(Throwable t) {
              if (!tryOnError(t)) {
                  RxJavaPlugins.onError(t);
              }
          }
  
          @Override
          public boolean tryOnError(Throwable t) {
              if (t == null) {
                  t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
              }
              if (!isDisposed()) {
                  try {
                      observer.onError(t);
                  } finally {
                      dispose();
                  }
                  return true;
              }
              return false;
          }
  
          @Override
          public void onComplete() {
              if (!isDisposed()) {
                  try {
                      observer.onComplete();
                  } finally {
                      dispose();
                  }
              }
          }
  
          @Override
          public void setDisposable(Disposable d) {
              DisposableHelper.set(this, d);
          }
  
          @Override
          public void setCancellable(Cancellable c) {
              setDisposable(new CancellableDisposable(c));
          }
  
          @Override
          public ObservableEmitter<T> serialize() {
              return new SerializedEmitter<T>(this);
          }
  
          @Override
          public void dispose() {
              DisposableHelper.dispose(this);
          }
  
          @Override
          public boolean isDisposed() {
              return DisposableHelper.isDisposed(get());
          }
      }

  AtomicReference和AtomicInteger非常类似，不同之处就在于AtomicInteger是对整数的封装，底层采用的是compareAndSwapInt实现CAS，比较的是数值是否相等，而AtomicReference则对应普通的对象引用，底层使用的是compareAndSwapObject实现CAS，比较的是两个对象的地址是否相等。也就是它可以保证你在修改对象引用时的线程安全性。
  ------
  
      //线程安全的改变引用
      public static boolean dispose(AtomicReference<Disposable> field) {
          Disposable current = field.get();
          Disposable d = DISPOSED;
          if (current != d) {
              // 设置新的值，返回旧值
              current = field.getAndSet(d);
              if (current != d) {
                  // 如果旧值和新值不一样，旧值就dispose 
                  if (current != null) {
                      current.dispose();
                  }
                  return true;
              }
          }
          return false;
      }
      
      
3. 观察者模式



        经常提到观察者与被观察者，这不就是JAVA的观察者模式的运用么？是的，但是跟传统意义的上观察者模式还不太一样，所以Rxjava实际上是一种扩展的观察者模式，所以有必要对这个扩展的观察者模式做进一步的了解。
        
        
        这看起来很像设计模式的观察者模式，但是有个重要的区别之一在于在没有Subscriber之前，Observable不会产生事件。
        
        对于普通的观察者模式这里不多说，简单概念它就是：观察者(Observer)需要在被观察者(Observable)变化的一顺间做出反应。而两者通过注册(Register)或者订阅(Subscrible)的方式进行绑定
        
        
        Observer与Observable是通过subscrible()来达成订阅关系。
        Rxjava中的事件回调有三种：onNext()、onCompleted()、onError()。 
        如果一个Observable没有任何的Observer，那么这个Observable是不会发出任何事件的。

4. 装饰器模式  
     
     
         //<? super T, ? extends R> 表示包括T在内的任何T的父类，包括R在内任何子类
         public final <R> Observable<R> map(Function<? super T, ? extends R> mapper) {
                 ObjectHelper.requireNonNull(mapper, "mapper is null");
                 return RxJavaPlugins.onAssembly(new ObservableMap<T, R>(this, mapper));
         }
         
         // map 后会重新返回一个Observable<R> 这个观察的数据是新的map装换后的数据R
         
         public final class ObservableMap<T, U> extends AbstractObservableWithUpstream<T, U> {
             final Function<? super T, ? extends U> function;
         
             public ObservableMap(ObservableSource<T> source, Function<? super T, ? extends U> function) {
                 super(source);
                 this.function = function;
             }
         
             @Override
             public void subscribeActual(Observer<? super U> t) {
                 source.subscribe(new MapObserver<T, U>(t, function));
             }
         
         
             static final class MapObserver<T, U> extends BasicFuseableObserver<T, U> {
                 final Function<? super T, ? extends U> mapper;
         
                 MapObserver(Observer<? super U> actual, Function<? super T, ? extends U> mapper) {
                     super(actual);
                     this.mapper = mapper;
                 }
         
                 @Override
                 public void onNext(T t) {
                     if (done) {
                         return;
                     }
         
                     if (sourceMode != NONE) {
                         actual.onNext(null);
                         return;
                     }
         
                     U v;
         
                     try {
                         v = ObjectHelper.requireNonNull(mapper.apply(t), "The mapper function returned a null value.");
                     } catch (Throwable ex) {
                         fail(ex);
                         return;
                     }
                     actual.onNext(v);
                 }
         
                 @Override
                 public int requestFusion(int mode) {
                     return transitiveBoundaryFusion(mode);
                 }
         
                 @Nullable
                 @Override
                 public U poll() throws Exception {
                     T t = qs.poll();
                     return t != null ? ObjectHelper.<U>requireNonNull(mapper.apply(t), "The mapper function returned a null value.") : null;
                 }
             }
         }



#### 自定义产生数据源可以使用Observable.create,RxJava内部提供了一些默认的方法

1. Observable.fromArray

        
        public static <T> Observable<T> fromArray(T... items) {
                ObjectHelper.requireNonNull(items, "items is null");
                if (items.length == 0) {
                    return empty();
                } else
                if (items.length == 1) {
                    return just(items[0]);
                }
                return RxJavaPlugins.onAssembly(new ObservableFromArray<T>(items));
            }
            
        ObservableFromArray
        
        public final class ObservableFromArray<T> extends Observable<T> {
            final T[] array;
            public ObservableFromArray(T[] array) {
                this.array = array;
            }
            @Override
            public void subscribeActual(Observer<? super T> s) {
                FromArrayDisposable<T> d = new FromArrayDisposable<T>(s, array);
        
                s.onSubscribe(d);
        
                if (d.fusionMode) {
                    return;
                }
        
                d.run();
            }
        
            static final class FromArrayDisposable<T> extends BasicQueueDisposable<T> {
        
                final Observer<? super T> actual;
        
                final T[] array;
        
                int index;
        
                boolean fusionMode;
        
                volatile boolean disposed;
        
                FromArrayDisposable(Observer<? super T> actual, T[] array) {
                    this.actual = actual;
                    this.array = array;
                }
                 /
                 .
                 .
                 .
                 省略部分代码
                 .
                 /
        
                void run() {
                    T[] a = array;
                    int n = a.length;
        
                    for (int i = 0; i < n && !isDisposed(); i++) {
                        T value = a[i];
                        if (value == null) {
                            actual.onError(new NullPointerException("The " + i + "th element is null"));
                            return;
                        }
                        actual.onNext(value);
                    }
                    if (!isDisposed()) {
                        actual.onComplete();
                    }
                }
            }
        } 
        
        
      // 核心还是通过  ObservableFromArray 实现一个适配器，将数组数据包装起来，
      // 调用subscribe 会调用subscribeActual ，先将传递的Observer进行包装适配，然后订阅建立关联，同时调用FromArrayDisposable 的run方法
      
      // 还是两个适配器，一个是ObservableFromArray适配传递过来数据，一个是FromArrayDisposable适配接收的Observer，同时封装分发逻辑对应run实现
      
      
     
- doOnNext doOnError 是增强实现，在onNext前面执行 ；通常做一些输出，打印日志，数据存储，备份    


     doOnNext  
     
     private Observable<T> doOnEach(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Action onAfterTerminate) {
             ObjectHelper.requireNonNull(onNext, "onNext is null");
             ObjectHelper.requireNonNull(onError, "onError is null");
             ObjectHelper.requireNonNull(onComplete, "onComplete is null");
             ObjectHelper.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
             return RxJavaPlugins.onAssembly(new ObservableDoOnEach<T>(this, onNext, onError, onComplete, onAfterTerminate));
         }
         
     public final class ObservableDoOnEach<T> extends AbstractObservableWithUpstream<T, T> { 
     
     source.subscribe(new DoOnEachObserver<T>(t, onNext, onError, onComplete, onAfterTerminate))
     
     @Override
     public void onNext(T t) {
         if (done) {
             return;
         }
         try {
             //这里相当于AOP思想，起到一个拦截，增强扩展功能,执行时机就是在本次Observable的onNext方法后执行
             onNext.accept(t);
         } catch (Throwable e) {
             Exceptions.throwIfFatal(e);
             s.dispose();
             onError(e);
             return;
         }

         actual.onNext(t);
     }
     
 
### RxJava 设计理念（套娃的设计和组装）

       操作组合设计是从上到下的，即所谓的套娃制作设计,从里到外（这种装饰者模式一层一层就是套娃的设计思想）
     
       这种设计把第一个Observable想象成第一个最小的套娃，把第二个Observable比第一个大一点套娃，第三个Observable比第二个还要大一点，这就是套娃的设计思想
       想象每个套娃都那么几个洞；每层组装都会顺着洞放下一个钩子滑轮（钩子在下，绕滑轮一圈）一层勾着一层，当第一个层被第二层嵌套是，就将准备好的东西放到勾着上，所以每层之间就有了钩子滑轮着作为桥梁；
       
       当最外层产生订阅的时候我们相当，上面的人通知下一层，下一次同样通知下一次，最终底层接到通知，的钩子被拉动，东西顺着钩子到达上面一层，然后一层一层往上，
       
       钩子相当于Observer, 通知者相当于 subscribe 调用Observer onNext, （subscribe相当于另一个绳子东西，可以通知下一层）
       
       通过另一个根绳子subscribe通知下一次，直到底层的Observable，接到命令后，调用Observer OnNext，相当于拉动滑轮一层往上一层传递
      
       //每一层调用这样的方法将Observer传递给subscribe方法，再传递给subscribeActual,再调用下一层的 source.subscribe(parent)，同时parent,相当于有封装了一层
       source.subscribe(parent);
  
  
### 订阅后产生的执行，还是很复杂的一件事，我们继续探讨

   先看一个简单的例子：模拟延迟5秒发送一个"HelloWorold",在5秒内点击按钮取消订阅
   
       fun testSimple() {
               val observable = Observable.create<String> {
                   Log.i("rxjava", "oncreate")
                   Handler().postDelayed({
                       it.onNext("HelloWorld")
                   }, 5000)
       
               }
               var disposeObserver = observable.subscribeWith(DisposableObserverImpl())
               btn_cancel.setOnClickListener {
                   disposeObserver.dispose()
               }
       }
       
       public static boolean dispose(AtomicReference<Disposable> field) {
              Disposable current = field.get();
              Disposable d = DISPOSED;
              if (current != d) {
                  current = field.getAndSet(d);
                  if (current != d) {
                      if (current != null) {
                          current.dispose();
                      }
                      return true;
                  }
              }
              return false;
       }
                  
                  
  - 执行流程
  1. subscribeWith 
  2. ObservableCreate.subscribe  --> subscribeActual  -->创建一个Disposable(CreateEmitter) -->observer.onSubscribe(CreateEmitter);
  3. DisposableObserver onSubscribe 收到ObservableCreate传递过来的Disposable，更新自己的value(因为自己本身也是一个Disposable)
  4. ObservableCreate.source.subscribe(CreateEmitter);产生实际订阅，开始按照要就发射数据，这里做了5秒延迟
  5. 此时延迟时间内调用 DisposableObserver.dispose()
  6. DisposableHelper.dispose(s); 这个s 就是DisposableObserver内部的AtomicReference<Disposable> s = new AtomicReference<Disposable>();
  7. 提醒一句，之前这个s 通过onSubscribe已经被赋值成CreateEmitter，因为CreateEmitter本身也实现了Disposable
  8. Disposable current = field.get();  此时current 值 CreateEmitter
  9. current!=DISPOSED 开始更新  field.getAndSet(d)  更新成功返回旧值
  10. 判断一下旧值不等于null，而是CreateEmitter,所以继续调用CreateEmitter.dispose()
  11. CreateEmitter.dispose() -->  DisposableHelper.dispose(this)
  12. field.get() 当前的的value默认是null的，然后阐释更新成DISPOSED
  13. CreateEmitter在OnNext过来的时候就会验证这个值，已经DISPOSED就不再传递
  
  所以多层嵌套也是如此，通过每个外层套娃Disposable 的 AtomicReference<Disposable>储存下一层套娃的Disposable；
  当外层出发dispose就一层一层的更新AtomicReference<Disposable>为DESTORY,并且返回下一层的Disposable,如果不为空就证明没有到达最底层，
  所以继续调用disposable，直到返回的数据为null为止，从而控制了整个流程的取消订阅
  
  //核心方法 subscribe 用来向底层套娃通知，通知到最后底层时，底层套娃通过 OnNext 等方法开始通知上一层观察者，上一层再通过OnNext通知上一层
  //onSubscribe 在通知底层过程中，每一层告知上一层一个Dispose对象
  
  - 套娃的设计其实就是装饰者模式，Observable一层嵌套一层，同时在订阅观察者也会被一层一层嵌套发送到底部
      
  
- 简单回顾一下前面的源码间设计

     - Observable.create - ObservableOnSubscribe接口 -> ObservableCreate适配器(继承Observable) -> 外部调用subscribe ->真实调用subscribeActual 
       ->CreateEmitter(适配) -> Observer
       
     - Observable.fromArray -> ObservableFromArray适配器(继承Observable) ->  外部调用subscribe ->真实调用subscribeActual 
       ->FromArrayDisposable() -> Observer
       
     - Observable.map -> ObservableMap适配器(继承Observable)-> 外部调用subscribe ->真实调用subscribeActual ->MapObserver
     
     - Observable.filter ->ObservableFilter -> 外部调用subscribe ->真实调用subscribeActual ->FilterObserver 
     
     - Observable 的所有操作方法都是通过包装自己来实现的，所以设计就像是套娃的设计，当我们执行的时候通过subscrible
     
     - 看一个例子
     
     
      val observable = Observable.create<String> {
                 Log.i("rxjava", "oncreate")
                 it.onNext("HelloWorld")
             }.doOnNext {
     
             }.filter { it == "HelloWorld" }.map { "AAA$it" }
             
      // 产生订阅后执行逻辑比较复杂，
      
      从最外层调用subscrible开始 会调用 Observer开始调用
      ObservableObserveOn.subscribe(DisposableObserver)
      
      ObservableObserveOn.subscribeActual  source.subscribe()
      
      ObservableSubscribeOn.subscribeActual  s.onSubscribe(SubscribeOnObserver);
      
      ObservableObserveOn.ObserveOnObserver.onSubscribe() actual.onSubscribe(ObserveOnObserver)
      
      DisposableObserver.onSubscribe  AtomicReference<Disposable> s 得到 Disposable(ObserveOnObserver)
      
      //开始提交任务，此时的任务就可能是异步的了，之前都是同步任务
      ObservableSubscribeOn -> 在任务中执行   source.subscribe(parent);
      
      ObservableCreate.subscribeActual   observer.onSubscribe(CreateEmitter);
      
      SubscribeOnObserver.onSubscribe  

- Observable.cache()方法，当多个观察者订阅后，被订阅的观察者只会执行一次，然后缓存到内存中       

      cache() -> ObservableCache.from(this)-> from(source, 16) ->CacheState<T>(source, capacityHint) ->ObservableCache ->
      
      CacheState(CacheState<T> extends LinkedArrayList implements Observer<T>)
       
      这里这么实现，比平时创建多了一层，Observer，当订阅者产生订阅的时候，调用subscrible时，会想通过CacheState这个Observer做一层代理，
      
      其中 CacheState 中 有一个集合  final AtomicReference<ReplayDisposable<T>[]> observers; 在addChild添加进去；这里
      ReplayDisposable装饰了cacheState,和最终的Observer， 当调用replay 时，从state中取到元素o,然后通过NotificationLite.accept(o, child)
      回调onNext等方法
  
      每一个观察者订阅时，都会先包装成ReplayDisposable ，并且addChild保存到CacheState中，接着调用ReplayDisposable的replay处理数据回调
      
      
      
      
      - 这里会有个问题，ObservableCache里的subscribeActual方法为什么不是用的CreateEmitter实现的，而是通过state.connect ,
        这里this 其实就是一个Observer； 因为我们ObservableCache是通过包装扩展来的，所以前一个Observable，
        产生订阅的地方一定是需要一个Observer，而不是需要一个Emitter(如果是原始对象，那是Emitter,但现在不是），但这里我们同样需要一个实现Disposable
        接口的一个对象，这样可以做取消操作。
        
     
     
     - Code
     
          public void connect() {
                  source.subscribe(this);
                  isConnected = true;
          }
    
          @Override
          protected void subscribeActual(Observer<? super T> t) {
              // we can connect first because we replay everything anyway
              ReplayDisposable<T> rp = new ReplayDisposable<T>(t, state);
              t.onSubscribe(rp);
      
              state.addChild(rp);
      
              // we ensure a single connection here to save an instance field of AtomicBoolean in state.
              if (!once.get() && once.compareAndSet(false, true)) {
                  state.connect();
              }
      
              rp.replay();
          }
                  
          
          
- Observer 设计详解


      public interface Observer<T> {
          //产生订阅后回传一个Disposable
          void onSubscribe(@NonNull Disposable d);
      
          void onNext(@NonNull T t);
       
          void onComplete();
      
      }
     
      
      
- Disposable 设计详解

   - 目的：
      
      
          
           主要功能是管理一个状态值。下面再来回顾一下Observable的作用，即其发布数据并由Observer消费。作为消费者，
           我很可能不需要每次都对接收的数据进行处理，也就是消费者应该有一个可以放弃操作资源的可选项，
           而我们在Observer接口定义中并没有找到相关的方法。在这里可以拓展一个设计理念：一切为了解耦。我们在做表设计的时候，尽量保证表符合三范式，
           以达到表的单一性，用生活中的话说就是请专注于自己的领域。
          
      
    
    
          // 一次性的 用完即可丢弃的，每个Observer适配器都会实现一个Disposable
          public interface Disposable {
              //处理，用来解除订阅，防止内存泄露
              void dispose();
              //判断是否已经被处理了
              boolean isDisposed();
          }
          
          
          // 上面讲到的 CreateEmitter 在调用真实Observer 前都做了isDispose判断
          
          static final class CreateEmitter<T> extends AtomicReference<Disposable>
                             implements ObservableEmitter<T>, Disposable {
                             
                             
- 线程切换


- ConnectableObservable

 

- Subject模式  

   Subject实际上还是Observable，只不过它继承了Observer接口，可以通过onNext、onComplete、onError方法发射和终止发射数据。  
   
   
  1. PublishSubject 这个是主动的推送的 ,之前作为被观察者只能在订阅的时候出发，现在自己本身实现了Observer，所以可以主动去发送数据给订阅者
  
     PublishSubject 是最直接的一个 Subject。当一个数据发射到 PublishSubject 中时，PublishSubject 将立刻把这个数据发射到订阅到该 subject 上的所有 subscriber 中。    
     
     同时：PublishSubject特性，先发射的数据，后来的观者者是不会收到任何数据的
     
     
     public final class PublishSubject<T> extends Subject<T> {
     
     PublishSubject() {
             //内部保存一个观察者列表
             subscribers = new AtomicReference<PublishDisposable<T>[]>(EMPTY);
     }
     
     当有新的订阅者订阅的时候
     @Override
         public void subscribeActual(Observer<? super T> t) {
             PublishDisposable<T> ps = new PublishDisposable<T>(t, this);
             t.onSubscribe(ps);
             //保存订阅者到列表中
             if (add(ps)) {
                 // if cancellation happened while a successful add, the remove() didn't work
                 // so we need to do it again
                 //添加成功后，因为添加过程也是一个自旋的过程，在这个过程中ps可能会被更改，被取消了就会移除
                 if (ps.isDisposed()) {
                     remove(ps);
                 }
             } else {
                 Throwable ex = error;
                 if (ex != null) {
                     t.onError(ex);
                 } else {
                     t.onComplete();
                 }
             }
         }
         
          void remove(PublishDisposable<T> ps) {
                 for (;;) {
                     PublishDisposable<T>[] a = subscribers.get();
                     if (a == TERMINATED || a == EMPTY) {
                         return;
                     }
         
                     int n = a.length;
                     int j = -1;
                     for (int i = 0; i < n; i++) {
                         if (a[i] == ps) {
                             j = i;
                             break;
                         }
                     }
         
                     if (j < 0) {
                         return;
                     }
         
                     PublishDisposable<T>[] b;
         
                     if (n == 1) {
                         b = EMPTY;
                     } else {
                         b = new PublishDisposable[n - 1];
                         //a旧数组，b新数组
                         //src表示源数组，srcPos表示源数组要复制的起始位置，desc表示目标数组，destPos表示目标数组放入的起始位置，
                         //length表示要复制的长度。
                         //当前j表示找到了需要移除的位置
                         System.arraycopy(a, 0, b, 0, j);
                         //丢弃j位置袁术，所以从j+1开始复制剩余元素
                         System.arraycopy(a, j + 1, b, j, n - j - 1);
                     }
                     //原子化操作，判断这个操作的集合还是不是之前的集合，这里比较的是地址，证明没人操作过，
                     //如果多线程，有人操作过就会返回false，就会重新循环获取被改动的集合，继续操作
                     if (subscribers.compareAndSet(a, b)) {
                         return;
                     }
                 }
             }
             
             
           boolean add(PublishDisposable<T> ps) {
                  for (;;) {
                      PublishDisposable<T>[] a = subscribers.get();
                      if (a == TERMINATED) {
                          return false;
                      }
          
                      int n = a.length;
                      @SuppressWarnings("unchecked")
                      PublishDisposable<T>[] b = new PublishDisposable[n + 1];
                      System.arraycopy(a, 0, b, 0, n);
                      b[n] = ps;
                      //同样判断当前集合有没有更改过，如果没有就赋值，有就修改
                      if (subscribers.compareAndSet(a, b)) {
                          return true;
                      }
                  }
              }
     
     
        总结：PublishSubject很好的使用了CAS自旋来保证线程安全，因为数组不会很大所以使用arraycopy这种内存抖动也不会很大，实现原理类似CopyonWriteArray
        
  2. ReplaySubject 可以缓存所有发射给他的数据。当一个新的订阅者订阅的时候，缓存的所有数据都会发射给这个订阅者。 由于使用了缓存，所以每个订阅者都会收到所以的数据：     
     
     
      public static <T> ReplaySubject<T> create() {
              return new ReplaySubject<T>(new UnboundedReplayBuffer<T>(16));
      }
      
      @Override
          protected void subscribeActual(Observer<? super T> observer) {
              ReplayDisposable<T> rs = new ReplayDisposable<T>(observer, this);
              observer.onSubscribe(rs);
      
              if (!rs.cancelled) {
              //存储观察者
                  if (add(rs)) {
                      if (rs.cancelled) {
                          remove(rs);
                          return;
                      }
                  }
                  //开始将存储数据发送
                  buffer.replay(rs);
              }
          }
          
          
        @Override
            public void onNext(T t) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                if (done) {
                    return;
                }
        
                ReplayBuffer<T> b = buffer;
                //存储发送数据
                b.add(t);
        
                for (ReplayDisposable<T> rs : observers.get()) {
                    //发送
                    b.replay(rs);
                }
            }
      //其中每次订阅和发送数据，都会把之前的发给观察者，观察者自身有个index,默认是0,存储的是数据的位置，如果已经收到过的数据index也跟着1+，
      public void replay(ReplayDisposable<T> rs) {
                 // 多个线程进入这个值让后续的线程等待
                 if (rs.getAndIncrement() != 0) {
                     return;
                 }
     
                 int missed = 1;
                 final List<Object> b = buffer;
                 final Observer<? super T> a = rs.actual;
     
                 Integer indexObject = (Integer)rs.index;
                 int index;
                 if (indexObject != null) {
                     index = indexObject;
                 } else {
                     index = 0;
                     rs.index = 0;
                 }
     
                 for (;;) {
     
                     if (rs.cancelled) {
                         rs.index = null;
                         return;
                     }
     
                     int s = size;
     
                     while (s != index) {
     
                         if (rs.cancelled) {
                             rs.index = null;
                             return;
                         }
     
                         Object o = b.get(index);
     
                         if (done) {
                             if (index + 1 == s) {
                                 s = size;
                                 if (index + 1 == s) {
                                     if (NotificationLite.isComplete(o)) {
                                         a.onComplete();
                                     } else {
                                         a.onError(NotificationLite.getError(o));
                                     }
                                     rs.index = null;
                                     rs.cancelled = true;
                                     return;
                                 }
                             }
                         }
     
                         a.onNext((T)o);
                         index++;
                     }
     
                     if (index != size) {
                         continue;
                     }
     
                     rs.index = index;
                     //如果还有新的线程没有执行，继续执行
                     missed = rs.addAndGet(-missed);
                     if (missed == 0) {
                         break;
                     }
                 }
             }
             
        总结：非常巧妙的使用CAS实现了线程安全的操作
        
  3. BehaviorSubject 只保留最后一个值。 等同于限制 ReplaySubject 的个数为 1 的情况。在创建的时候可以指定一个初始值，这样可以确保党订阅者订阅的时候可以立刻收到一个值        
  
  4. AsyncSubject 
     AsyncSubject 也缓存最后一个数据。区别是 AsyncSubject 只有当数据发送完成时（onCompleted 调用的时候）才发射这个缓存的最后一个数据。可以使用 AsyncSubject 发射一个数据并立刻结束。
     
     AsyncSubject<Integer> s = AsyncSubject.create();
     s.subscribe(v -> System.out.println(v));
     s.onNext(0);
     s.onCompleted();
     s.onNext(1);
     s.onNext(2);
  
   
     结果：
     
     0
#### 异步任务切换
     
    subscribeOn 将subscribe方法切换到指定线程
    observeOn  将OnNext调用的数据切换线程，也就是观察者回调方法                             