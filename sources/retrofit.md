### Retrofit2.0 源码分析

    

##### retrofit 一共就是17个类，24个注解
#### 使用步骤
1. 第一步根据网络API定义一个接口

        public interface UserApi {
            @GET("/api/columns/{user} ")
            Call<User> getAuthor(@Path("user") String user)
        }


2. 第二步创建一个Retroft对象，并设置域名地址

        public static final String API_URL = "https://zhuanlan.zhihu.com";

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
            
3. 第三步再用这个retrofit对象创建一个UserApi对象：

        UserApi api = retrofit.create(UserApi.class);
            
4. 第四步调用这个API

        Call<User> call = api.getAuthor("ustory");
        //异步使用enqueue /[enk'ju:]/
        call.enqueue(new Callback<ZhuanLanAuthor>() {
          @Override
          public void onResponse(Response<ZhuanLanAuthor> author) {
              System.out.println("name： " + author.getName());
          }
          @Override
          public void onFailure(Throwable t) {
          }
        });
        //同步使用 [ˈeksɪkju:t]

#### Retrofit 原理分析

1. Retrofit 实例化



      public static final class Builder {
        private final Platform platform;
        private @Nullable okhttp3.Call.Factory callFactory;
        private HttpUrl baseUrl;
        private final List<Converter.Factory> converterFactories = new ArrayList<>();
        private final List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        private @Nullable Executor callbackExecutor;
        private boolean validateEagerly;
    
        Builder(Platform platform) {
          this.platform = platform;
          // Add the built-in converter factory first. This prevents overriding its behavior but also
          // ensures correct behavior when using converters that consume all types.
          converterFactories.add(new BuiltInConverters());
        }
    
        public Builder() {
          this(Platform.get());
        }
        ....
        ....
       public Retrofit build() {
          if (baseUrl == null) {
            throw new IllegalStateException("Base URL required.");
          }
    
          okhttp3.Call.Factory callFactory = this.callFactory;
          if (callFactory == null) {
            callFactory = new OkHttpClient();
          }
    
          Executor callbackExecutor = this.callbackExecutor;
          if (callbackExecutor == null) {
            callbackExecutor = platform.defaultCallbackExecutor();
          }
    
          // Make a defensive copy of the adapters and add the default Call adapter.
          List<CallAdapter.Factory> adapterFactories = new ArrayList<>(this.adapterFactories);
          adapterFactories.add(platform.defaultCallAdapterFactory(callbackExecutor));
    
          // Make a defensive copy of the converters.
          List<Converter.Factory> converterFactories = new ArrayList<>(this.converterFactories);
    
          return new Retrofit(callFactory, baseUrl, converterFactories, adapterFactories,
              callbackExecutor, validateEagerly);
        }
        

    // 封装handler
    static class MainThreadExecutor implements Executor {
      private final Handler handler = new Handler(Looper.getMainLooper());

      @Override public void execute(Runnable r) {
        handler.post(r);
      }
    }
    
    //Platform.java
     CallAdapter.Factory defaultCallAdapterFactory(@Nullable Executor callbackExecutor) {
        if (callbackExecutor != null) {
          return new ExecutorCallAdapterFactory(callbackExecutor);
        }
        return DefaultCallAdapterFactory.INSTANCE;
      }


    总结：采用构造模式，通过Builder来实例化，包含有Platform用来获取平台信息，
      callFactory是用来生成http请求对象的工厂，HttpUrl是对url的参数处理，List<Converter.Factory> converterFactories用来存储数据转化工厂(比如返回json数据和转化成对象，发送的数据如何由对象转化为json)，通过这个工厂用户和自己实现解析部分
      ，List<CallAdapter.Factory>adapterFactories 适配Call类型，默认用的ExecutorCallAdapterFactory类型返回一个Call<T>去执行请求，也可以通过配置RxJava2CallAdapterFactory()返回一个 Observable<WXNewsResult> 去执行
      Executor callbackExecutor 回调调度器（通过平台进行判断，如果是Android平台使用的是MainThreadExecutor其实就是封装的handler）;然后配置完以上参数就调用build,初始化callFactory为OKHttpClient，初始化callbackExecutor为MainExecutor
      
  
2. retrofit.create实例化Api接口对象


        // Retrofit.java
        private final Map<Method, ServiceMethod<?, ?>> serviceMethodCache = new ConcurrentHashMap<>();

        public <T> T create(final Class<T> service) {
            Utils.validateServiceInterface(service);
            if (validateEagerly) {
              eagerlyValidateMethods(service);
            }
            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {
                  private final Platform platform = Platform.get();
        
              @Override public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                  throws Throwable {
                // If the method is a method from Object then defer to normal invocation.
                if (method.getDeclaringClass() == Object.class) {
                  return method.invoke(this, args);
                }
                if (platform.isDefaultMethod(method)) {
                  return platform.invokeDefaultMethod(method, service, proxy, args);
                }
                ServiceMethod<Object, Object> serviceMethod =
                    (ServiceMethod<Object, Object>) loadServiceMethod(method);
                OkHttpCall<Object> okHttpCall = new OkHttpCall<>(serviceMethod, args);
                return serviceMethod.callAdapter.adapt(okHttpCall);
              }
            });
        }
        
      
      ServiceMethod<?, ?> loadServiceMethod(Method method) {
        ServiceMethod<?, ?> result = serviceMethodCache.get(method);
        if (result != null) return result;
    
        synchronized (serviceMethodCache) {
          result = serviceMethodCache.get(method);
          if (result == null) {
            result = new ServiceMethod.Builder<>(this, method).build();
            serviceMethodCache.put(method, result);
          }
        }
        return result;
      }
     
     
     

     
     总结：通过传过来的接口类型参数，使用Proxy.newProxyInstance穿件一个动态代理对象并返回，
     而所有的方法都将走invoke方法；方法内部先判断此方法如果是Object类的方法就直接调用，这个设计很精妙；然判断方法是否是平台方法，Android平台默认是false，
     然后loadServiceMethod，内部先查找缓存（private final Map<Method, ServiceMethod<?, ?>> serviceMethodCache），没有就创建一个并加入缓存,并且创建一个OkHttpCall对象,用交给默认的CallAdapterFactory
     返回一个ExecutorCallbackCall对象，而ExecutorCallbackCall代理了okHttpCall对象，所以通过ExecutorCallbackCall调用enqueue和execute来调用OKHttpClient的Call对象enqueue和execute
     
     
     
3. ServiceMethod


       //ServiceMethod.java
       static final class Builder<T, R> {
         ....
         ....
     
         Builder(Retrofit retrofit, Method method) {
           this.retrofit = retrofit;
           this.method = method;
           this.methodAnnotations = method.getAnnotations();
           this.parameterTypes = method.getGenericParameterTypes();
           this.parameterAnnotationsArray = method.getParameterAnnotations();
         }
     
         public ServiceMethod build() {
           callAdapter = createCallAdapter();
           responseType = callAdapter.responseType();
           if (responseType == Response.class || responseType == okhttp3.Response.class) {
             throw methodError("'"
                 + Utils.getRawType(responseType).getName()
                 + "' is not a valid response body type. Did you mean ResponseBody?");
           }
           responseConverter = createResponseConverter();
     
           for (Annotation annotation : methodAnnotations) {
             parseMethodAnnotation(annotation);
           }
     
           if (httpMethod == null) {
             throw methodError("HTTP method annotation is required (e.g., @GET, @POST, etc.).");
           }
     
           if (!hasBody) {
             if (isMultipart) {
               throw methodError(
                   "Multipart can only be specified on HTTP methods with request body (e.g., @POST).");
             }
             if (isFormEncoded) {
               throw methodError("FormUrlEncoded can only be specified on HTTP methods with "
                   + "request body (e.g., @POST).");
             }
           }
     
           int parameterCount = parameterAnnotationsArray.length;
           parameterHandlers = new ParameterHandler<?>[parameterCount];
           for (int p = 0; p < parameterCount; p++) {
             Type parameterType = parameterTypes[p];
             if (Utils.hasUnresolvableType(parameterType)) {
               throw parameterError(p, "Parameter type must not include a type variable or wildcard: %s",
                   parameterType);
             }
     
             Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
             if (parameterAnnotations == null) {
               throw parameterError(p, "No Retrofit annotation found.");
             }
     
             parameterHandlers[p] = parseParameter(p, parameterType, parameterAnnotations);
           }
     
           ....
           ....
     
           return new ServiceMethod<>(this);
         }

    总结：ServiceMethod封装了调用方法，内部通过Build去构造自己，Build的构造法方法内部获取 注解信息和参数信息，在build时解析配置的注解信息；
    

4. OkHttpCall


       
       final class OkHttpCall<T> implements Call<T> {
            private final ServiceMethod<T, ?> serviceMethod;
            @GuardedBy("this")
            private @Nullable okhttp3.Call rawCall;
            
            ....
            ....
            @Override 
            public synchronized Request request() {
                okhttp3.Call call = rawCall;
                if (call != null) {
                  return call.request();
                }
                if (creationFailure != null) {
                  if (creationFailure instanceof IOException) {
                    throw new RuntimeException("Unable to create request.", creationFailure);
                  } else {
                    throw (RuntimeException) creationFailure;
                  }
                }
                try {
                  return (rawCall = createRawCall()).request();
                } catch (RuntimeException e) {
                  creationFailure = e;
                  throw e;
                } catch (IOException e) {
                  creationFailure = e;
                  throw new RuntimeException("Unable to create request.", e);
                }
              }
            
              @Override public void enqueue(final Callback<T> callback) {
                checkNotNull(callback, "callback == null");
            
                okhttp3.Call call;
                Throwable failure;
            
                synchronized (this) {
                  if (executed) throw new IllegalStateException("Already executed.");
                  executed = true;
            
                  call = rawCall;
                  failure = creationFailure;
                  if (call == null && failure == null) {
                    try {
                      call = rawCall = createRawCall();
                    } catch (Throwable t) {
                      failure = creationFailure = t;
                    }
                  }
                }
            
                if (failure != null) {
                  callback.onFailure(this, failure);
                  return;
                }
            
                if (canceled) {
                  call.cancel();
                }
            
                call.enqueue(new okhttp3.Callback() {
                  @Override public void onResponse(okhttp3.Call call, okhttp3.Response rawResponse)
                      throws IOException {
                    Response<T> response;
                    try {
                      response = parseResponse(rawResponse);
                    } catch (Throwable e) {
                      callFailure(e);
                      return;
                    }
                    callSuccess(response);
                  }
            
                  @Override public void onFailure(okhttp3.Call call, IOException e) {
                    try {
                      callback.onFailure(OkHttpCall.this, e);
                    } catch (Throwable t) {
                      t.printStackTrace();
                    }
                  }
            
                  private void callFailure(Throwable e) {
                    try {
                      callback.onFailure(OkHttpCall.this, e);
                    } catch (Throwable t) {
                      t.printStackTrace();
                    }
                  }
            
                  private void callSuccess(Response<T> response) {
                    try {
                      callback.onResponse(OkHttpCall.this, response);
                    } catch (Throwable t) {
                      t.printStackTrace();
                    }
                  }
                });
              }
              
              
              private okhttp3.Call createRawCall() throws IOException {
              
                  Request request = serviceMethod.toRequest(args);
                  okhttp3.Call call = serviceMethod.callFactory.newCall(request);
                  if (call == null) {
                    throw new NullPointerException("Call.Factory returned null.");
                  }
                  return call;
                }
       }
       
       // Call.java
       public interface Call<T> extends Cloneable {
        
         Response<T> execute() throws IOException;//同步调度器
       
         void enqueue(Callback<T> callback);//异步调度器
       
         boolean isExecuted();//是否是执行
    
         void cancel();
       
         boolean isCanceled();
     
         Call<T> clone();
       
         Request request();//创建请求
       }


   总结：OkHttpCall 实现了Retrofit自己的Call；并实现request,enqueue,execute方法
   
   1. request方法：内部又调用createRawCall方法
   内部创建一个Okhttp3的Call(先通过serviceMethod构建一个Okhttp3的request对象，然后在用通过配置的callFactory也就是OkHttpClient创建一个Call)
   2. enqueue方法：通过OKhttp的call对象正常调用请求
     
5. serviceMethod.callAdapter.adapt(okHttpCall)；这句中callAdapter默认对应是


      //ExecutorCallAdapterFactory.java
      new CallAdapter<Object, Call<?>>() {
              @Override public Type responseType() {
                return responseType;
              }

      @Override public Call<Object> adapt(Call<Object> call) {
        return new ExecutorCallbackCall<>(callbackExecutor, call);
      }
      
    
 总结 adapt方法最终返回一个ExecutorCallbackCall对象
 
6. ExecutorCallbackCall 

        static final class ExecutorCallbackCall<T> implements Call<T> {
               final Executor callbackExecutor;
               final Call<T> delegate;
               ....
               ....
        }
  
  总结：ExecutorCallbackCall 实现了Retrofit的Call接口，代理了OKHttpCall，进一步代理了OKHttp
  
7.最终通过  ExecutorCallbackCall去调用enqueue和execute去执行请求啦



####总结

通过retrofit 初始化一个ExecutorCallAdapterFactory对象，和配置一个Converter.Factory对象用转化数据，然后调用create方法创建一个API代理对象，API代理对象调用接口
，查找有没有对应ServiceMethod如果没有，就通过method参数创建一个，同时解析method注解和参数封装到ServiceMethod，然后在创建一个OKHttpCall,再调用ServiceMethod保存的callAdapter引用
（通过ExecutorCallAdapterFactory创建）再调用adapt 返回一个ExecutorCallbackCall ，因为返回一个ExecutorCallbackCall代理了call，然后返回一个ExecutorCallbackCall去执行请求