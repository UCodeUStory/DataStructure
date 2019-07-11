### RxJava 源码分析


#### Rxjava中的设计模式

1. 适配器模式

    将一个接口转换成客户希望的另一个接口，使接口不兼容的那些类可以一起工作，其别名为包装器(Wrapper)。
    
    
    
    例子 Observable.create(new ObservableOnSubscribe<String>() {
                   @Override
                   public void subscribe(ObservableEmitter<String> emitter) throws Exception {
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
                  source.subscribe(parent);
              } catch (Throwable ex) {
                  Exceptions.throwIfFatal(ex);
                  parent.onError(ex);
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
