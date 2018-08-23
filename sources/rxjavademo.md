### rxjava 


    https://github.com/ReactiveX/RxJava

1. rxjava 2.0 新要求 

RxJava2.X中，Observeable用于订阅Observer，是不支持背压的，而Flowable用于订阅Subscriber，是支持背压(Backpressure)的。


2. 啥叫不支持背压呢？
   
   当被观察者快速发送大量数据时，下游不会做其他处理，即使数据大量堆积，调用链也不会报MissingBackpressureException,消耗内存过大只会OOM
   
   我在测试的时候，快速发送了100000个整形数据，下游延迟接收，结果被观察者的数据全部发送出去了，内存确实明显增加了，遗憾的是没有OOM。
   

3. Observable 、Observer正确使用

    
    Observable mObservable=Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                    e.onNext(1);
                    e.onNext(2);
                    e.onComplete();
                }
            });
            
    Observer mObserver=new Observer<Integer>() {
                //这是新加入的方法，在订阅后发送数据之前，
                //回首先调用这个方法，而Disposable可用于取消订阅
                @Override
                public void onSubscribe(Disposable d) {
    
                }
    
                @Override
                public void onNext(Integer value) {
    
                }
    
                @Override
                public void onError(Throwable e) {
    
                }
    
                @Override
                public void onComplete() {
    
                }
            };
            
    mObservable.subscribe(mObserver);


4. Flowable 处理被压问题


   - 查看 Flowable源码你会发现他内部限制了缓存大小，128
  
      public abstract class Flowable<T> implements Publisher<T> {
          /** The default buffer size. */
          static final int BUFFER_SIZE;
          static {
              BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128));
          }

  - BackpressureStrategy 背压策略主要有四种：
   
   BackpressureStrategy.ERROR，前面已经用到过，会在上下游流量不平衡的时候报出MissingBackpressureException的错误。
   
   BackpressureStrategy.BUFFER，这种背压策略和Observable没有什么区别，上游可以无限发送，水缸足够大，最后还是会抛出OOM；并且可以发现Flowable里无限发送的话，内存增长的比Observable慢，这是因为Flowable采用响应拉取，难免会损耗些性能。
  
   BackpressureStrategy.DROP，水缸里只存储128个事件，剩余的事件舍去，如果下游拉取了事件，则上游当前正在发送的事件在拉取时刻补充进水缸。
   
   BackpressureStrategy.LATEST，下游总能获取到最后最新的事件，因为水缸中最后进来的事件总会被新的事件overwrite，所以可以每次拉取总能获得最后或是最新的事件。

#### 下面代码产生 OOM



    public void demo17() {
            Observable
                    .create(new ObservableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                            int i = 0;
                            while (true) {
                                i++;
                                System.out.println("发射---->" + i);
                                e.onNext(i);
                            }
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
    
                        @Override
                        public void onNext(Integer integer) {
                            try {
                                Thread.sleep(50);
                                System.out.println("接收------>" + integer);
                            } catch (InterruptedException ignore) {
                            }
                        }
    
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
    
                        @Override
                        public void onComplete() {
                            System.out.println("接收------>完成");
                        }
                    });
        }
        
由于下游处理数据的速度（的Thread.sleep（50））赶不上上游发射数据的速度，则会导致背压问题。



#### 最好解决方案   下面，对其通过可流动做些改进，让其既不会产生背压问题，也不会引起异常或者数据丢失。



     public void demo18() {
            Flowable
                    .create(new FlowableOnSubscribe<Integer>() {
                        @Override
                        public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                            int i = 0;
                            while (true) {
                                if (e.requested() == 0) continue;//此处添加代码，让flowable按需发送数据
                                System.out.println("发射---->" + i);
                                i++;
                                e.onNext(i);
                            }
                        }
                    }, BackpressureStrategy.MISSING)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<Integer>() {
                        private Subscription mSubscription;
    
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(1);            //设置初始请求数据量为1
                            mSubscription = s;
                        }
    
                        @Override
                        public void onNext(Integer integer) {
                            try {
                                Thread.sleep(50);
                                System.out.println("接收------>" + integer);
                                mSubscription.request(1);//每接收到一条数据增加一条请求量
                            } catch (InterruptedException ignore) {
                            }
                        }
    
                        @Override
                        public void onError(Throwable t) {
                        }
    
                        @Override
                        public void onComplete() {
                        }
                    });
        }

    下游处理数据的速度Thread.sleep（50）赶不上上游发射数据的速度，
    不同的是，我们在下游onNext（整数整数）方法中，每接收一条数据增加一条请求量，
    
    mSubscription.request(1)
    在上游添加代码
    
    if(e.requested()==0)continue;
    。上游让按需发送数据


4. 其他被观察者/观察者模式

    当然，除了上面这两种观察者，还有一类观察者
    - Single/SingleObserver
    - Completable/CompletableObserver
    - Maybe/MaybeObserver
    
    
    
    Single
    只发射一条单一的数据，或者一条异常通知，不能发射完成通知，其中数据与通知只能发射一个。
    
    Completable
    只发射一条完成通知，或者一条异常通知，不能发射数据，其中完成通知与异常通知只能发射一个
    
    Maybe
    可发射一条单一的数据，以及发射一条完成通知，或者一条异常通知，其中完成通知和异常通知只能发射一个，发射数据只能在发射完成通知或者异常通知之前，否则发射数据无效。
    



   - 1. Single使用
   
   
       Single.create(new SingleOnSubscribe<Integer>() {
                   @Override
                   public void subscribe(SingleEmitter<Integer> e) throws Exception {
                       e.onError(new Exception("自定义异常"));//只会走一个，其他的就会被无效
                       e.onSuccess(12);
                       e.onSuccess(12);
                       e.onError(new Exception("自定义异常"));
                   }
               }).subscribe(new SingleObserver<Integer>() {
                   @Override
                   public void onSubscribe(Disposable d) {
       
                   }
       
                   @Override
                   public void onSuccess(Integer integer) {
       
                       Log.i(tag,"onSuccess integer="+integer);
                   }
       
                   @Override
                   public void onError(Throwable e) {
                       Log.i(tag,"onError");
                       e.printStackTrace();
                   }
               });
           }
       
   - 2. Completable 使用
   
         
         Completable.create(new CompletableOnSubscribe() {
                     @Override
                     public void subscribe(CompletableEmitter e) throws Exception {
                         e.onComplete();//后面的都不会发送
                         e.onError(new Exception("自定义异常"));
         
         
                     }
         
                 }).subscribe(new CompletableObserver() {
                     @Override
                     public void onSubscribe(Disposable d) {
         
                     }
         
                     @Override
                     public void onComplete() {
                         Log.i(tag,"onComplete");
                     }
         
                     @Override
                     public void onError(Throwable e) {
                         Log.i(tag,"onError");
                     }
                 });
     
    方法onComplete与onError只可调用一个，若先调用onError则会导致onComplete无效
    
   - 3. MayBe 使用
   
   示例1：Maybe发射单一数据和完成通知
   
   示例2：Maybe发射单一数据和异常通知
   
   总之和上面一样只不过可以发送两个
    
4. Maybe/MaybeObserver 具体使用 给句上面秒速 发送数据，只能一个
 
 
 
          //判断是否登陆

        
           Maybe.just(isLogin())
               //可能涉及到IO操作，放在子线程
               .subscribeOn(Schedulers.newThread())
               //取回结果传到主线程
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new MaybeObserver<Boolean>() {
                       @Override
                       public void onSubscribe(Disposable d) {
           
                       }
           
                       @Override
                       public void onSuccess(Boolean value) {
                           if(value){
                               ...
                           }else{
                               ...
                           }
                       }
           
                       @Override
                       public void onError(Throwable e) {
                           
                       }
           
                       @Override
                       public void onComplete() {
           
                       }
                   });
           

 
 ### 操作符
 
  
 
 1. unsafeCreate(create)
 
  创建一个Observable（被观察者），当被观察者（Observer）/订阅者（Subscriber）
  subscribe(订阅)的时候就会依次发出三条字符串数据（"Hello","RxJava","Nice to meet you"）
  最终onComplete
  
  
      Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
                  @Override
                  public void call(Subscriber<? super String> subscriber) {
                      subscriber.onNext("Hello");
                      subscriber.onNext("RxJava");
                      subscriber.onNext("Nice to meet you");
                      subscriber.onCompleted();
                  }
              });
          
          
 2. just
  
  作用同上，订阅时依次发出三条数据，不过此方法参数可以有1-9条
  
    Observable.just("Hello", "RxJava", "Nice to meet you")
    
 3. from
 
 
  作用同just不过是把参数封装成数组或者可迭代的集合在依次发送出来，突破了just9个参数的限制
  
      String[] strings = {"Hello", "RxJava", "Nice to meet you"};
      Observable.from(strings)
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
          
 4. defer
 
 
  顾名思义，延迟创建
  
  
  
      private String[] strings1 = {"Hello", "World"};
          private String[] strings2 = {"Hello", "RxJava"};
      
          private void test() {
              Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
                  @Override
                  public Observable<String> call() {
                      return Observable.from(strings1);
                  }
              });
      
              strings1 = strings2; //订阅前把strings给改了
              observable.subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
          }
  我们发现数据结果变成这样了
  
  onNext--> Hello
  onNext--> RxJava
  onComplete
  由此可以证明defer操作符起到的不过是一个“预创建”的作用，真正创建是发生在订阅的时候
  
5. empty
  创建一个空的，不会发射任何事件（数据）的Observable
  Observable.empty()
          .subscribe(new Action1<Object>() {
              @Override
              public void call(Object o) {
                  System.out.println("onNext--> " + o);
              }
          }, new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                  System.out.println("onError--> " + throwable.getMessage());
              }
          }, new Action0() {
              @Override
              public void call() {
                  System.out.println("onComplete");
              }
          });
  onComplete
  
  
 6. never
  创建一个不会发出任何事件也不会结束的Observable
  
      
      Observable.never()
              .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object o) {
                      System.out.println("onNext--> " + o);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      ······
      
 7. error
 
  创建一个会发出一个error事件的Observable
      
      Observable.error(new RunftimeException("fuck!"))
              .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object o) {
                      System.out.println("onNext--> " + o);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
  onError--> fuck!
  
 8. range
 
  创建一个发射一组整数序列的Observable
  
  
      Observable.range(3, 8)
              .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object o) {
                      System.out.println("onNext--> " + o);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
              
  onNext--> 3
  
  onNext--> 4
  
  onNext--> 5
  
  onNext--> 6
  
  onNext--> 7
  
  onNext--> 8
  
  onNext--> 9
  
  onNext--> 10
  
  onComplete
  
9. interval

  创建一个无限的计时序列，每隔一段时间发射一个数字（从0开始）的Observable
  
  
     Observable.interval(1, TimeUnit.SECONDS)
          .subscribe(new Action1<Object>() {
              @Override
              public void call(Object o) {
                  System.out.println("onNext--> " + o);
              }
          }, new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                  System.out.println("onError--> " + throwable.getMessage());
              }
          }, new Action0() {
              @Override
              public void call() {
                  System.out.println("onComplete");
              }
          });
  
     System.in.read();//阻塞当前线程，防止JVM结束程序
     
  onNext--> 0
  
  onNext--> 1
  
  onNext--> 2
  
  onNext--> 3
  
  onNext--> 4
  
  onNext--> 5
  
  onNext--> 6
  
  ...
  
10. buffer(int count)

  将原发射出来的数据已count为单元打包之后在分别发射出来
  
      
      Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
              .buffer(3)
              .subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object o) {
                      System.out.println("onNext--> " + o);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> [1, 2, 3]
      onNext--> [4, 5, 6]
      onNext--> [7, 8, 9]
      onNext--> [10]
      onComplete
      
      
               
11. map

  将原Observable发射出来的数据转换为另外一种类型的数据
  
  
             1. map处理类型转换 
             
                   传递一个Function<原本类型，新类型>(){
                   
                     新类型  apply(原本类型数据){
                           // 处理
                           return 新类型数据
                       }
                   }
                   
             2. flatMap 处理多对多类型数据,因为他支持新类型可以还是一个被观察者
              
                      Function<原本类型，Observerable<新类型>>(){
                     
                       Observerable<新类型>  apply(原本类型数据){
                             // 处理
                             return Observerable.fromXXX(新类型数据)s
                         }
                     }
                 
                 
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .map(new Func1<String, Integer>() { //泛型第一个类型是原数据类型，第二个类型是想要变换的数据类型
                  @Override
                  public Integer call(String s) {
                      // 这是转换成了Student类型
                      // Student student = new Student();
                      // student.setName(s);
                      // return student;
                      return s.hashCode();        //将数据转换为了int（取得其hashCode值）
                  }
              })
              .subscribe(new Action1<Integer>() {
                  @Override
                  public void call(Integer o) {
                      System.out.println("onNext--> " + o);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> 69609650
      onNext--> -1834252888
      onNext--> -1230747480
      onComplete
  
  
12. flatMap

  作用类似于map又比map强大，map是单纯的数据类型的转换，而flapMap可以将原数据新的Observables，再将这些Observables的数据顺序缓存到一个新的队列中，在统一发射出来
   
      
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .flatMap(new Func1<String, Observable<Integer>>() {
                  @Override
                  public Observable<Integer> call(String s) {
                      return Observable.just(s.hashCode());
                  }
              })
              .subscribe(new Action1<Integer>() {
                  @Override
                  public void call(Integer o) {
                      System.out.println("onNext--> " + o);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
          }
      onNext--> 69609650
      onNext--> -1834252888
      onNext--> -1230747480
      onComplete
  
  这里虽然结果和map是相同的，但是过程却是不同的。flatMap是先将原来的三个字符串（"Hello","RxJava","Nice to meet you"）依次取其hashCode，在利用Observable.just将转换之后的int类型的值在发射出来。map只是单穿的转换了数据类型，而flapMap是转换成了新的Observable了，这在开发过程中遇到嵌套网络请求的时候十分方便。
  
  
 13. 过滤filter
 
  对发射的数据做一个限制，只有满足条件的数据才会被发射
  
      
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .filter(new Func1<String, Boolean>() {
                  @Override
                  public Boolean call(String s) {
                      //这里的显示条件是s的长度大于5，而Hello的长度刚好是5
                      //所以不能满足条件
                      return s.length() > 5;
                  }
              })
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> RxJava
      onNext--> Nice to meet you
      onComplete
 14. take 只发射前N项的数据（takeLast与take想反，只取最后N项数据）
 
     
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .take(2)
              //.taktLast(2)
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
          }
      onNext--> Hello
      onNext--> RxJava
      onComplete
      
15. skip  发射数据时忽略前N项数据（skpiLast忽略后N项数据）
      
      
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .skip(2)
              //.skipLast(2)
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
             }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
          }
      onNext--> Nice to meet you
      onComplete
      
      
 16. elementAt
 
  获取原数据的第N项数据作为唯一的数据发射出去（elementAtOrDefault会在index超出范围时，给出一个默认值发射出来）
      
      
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .elementAtOrDefault(1, "Great")
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> RxJava
      onComplete
      
 17. distinct  过滤掉重复项
 
 
      Observable.just("Hello", "Hello", "Hello", "RxJava", "Nice to meet you")
              .distinct()
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> Hello
      onNext--> RxJava
      onNext--> Nice to meet you
      onComplete
      
 18.组合 startWith 在发射一组数据之前先发射另一组数据
 
 
      Observable.just("Hello", "RxJava", "Nice to meet you")
              .startWith("One", "Two", "Three")
              .subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> One
      onNext--> Two
      onNext--> Three
      onNext--> Hello
      onNext--> RxJava
      onNext--> Nice to meet you
      onComplete
      
 19.merge  将多个Observables发射的数据合并后在发射
 
 
      Observable.merge(Observable.just(1, 2, 3), Observable.just(4, 5),
              Observable.just(6, 7), Observable.just(8, 9, 10))
              .subscribe(new Action1<Integer>() {
                  @Override
                  public void call(Integer s) {
                      System.out.println("onNext--> " + s);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      System.out.println("onError--> " + throwable.getMessage());
                  }
              }, new Action0() {
                  @Override
                  public void call() {
                      System.out.println("onComplete");
                  }
              });
      onNext--> 1
      onNext--> 2
      onNext--> 3
      onNext--> 4
      onNext--> 5
      onNext--> 6
      onNext--> 7
      onNext--> 8
      onNext--> 9
      onNext--> 10
      onComplete
      
 20. zip  按照自己的规则发射与发射数据项最少的相同的数据
 
 
          Observable.zip(Observable.just(1, 8, 7), Observable.just(2, 5),
                  Observable.just(3, 6), Observable.just(4, 9, 0), new Func4<Integer, Integer, Integer, Integer, Integer>() {
                      @Override
                      public Integer call(Integer integer, Integer integer2, Integer integer3, Integer integer4) {
                          return integer < integer2 ? integer3 : integer4;
                      }
                  })
                  .subscribe(new Action1<Integer>() {
                      @Override
                      public void call(Integer s) {
                          System.out.println("onNext--> " + s);
                      }
                  }, new Action1<Throwable>() {
                      @Override
                      public void call(Throwable throwable) {
                          System.out.println("onError--> " + throwable.getMessage());
                      }
                  }, new Action0() {
                      @Override
                      public void call() {
                          System.out.println("onComplete");
                      }
                  });
          onNext--> 3
          onNext--> 9
          onComplete
  通过观察以上例子可以发现我们的发射规则是如果发射的第一个数据小于第二个数则发射第三个数据，否则发射第四个数据（我们来验证一下，1确实是小于2的，随意发射的是3；8并不小于5，所以发射的是9，又因为四个发射箱，最少的之后两项，所以最后只发射了两项数据
 
21. doOnNext()的执行在订阅者调用onNext()之前执行，做一些缓存方法


#### map filter doOnNext等方法 执行顺序和设置的顺序一样，他们的操作都是基于上一个结果
