### OKHttp源码分析

#### 1. 使用


    OkHttpClient client = new OkHttpClient();
    
    String run(String url) throws IOException {
      Request request = new Request.Builder()
          .url(url)
          .build();
    
      Response response = client.newCall(request).execute();
      return response.body().string();
    }
    
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");
    
    OkHttpClient client = new OkHttpClient();
    
    String post(String url, String json) throws IOException {
      RequestBody body = RequestBody.create(JSON, json);
      Request request = new Request.Builder()
          .url(url)
          .post(body)
          .build();
      Response response = client.newCall(request).execute();
      return response.body().string();
    }

#### 2. 源码分析

  ##### Request、Response、Call 基本概念
  
  ##### 源码分析用到的设计模式有，单例模式、工厂模式、构造者模式、责任链模式
  
  Request:每一个HTTP请求包含一个URL、一个方法（GET或POST或其他）、一些HTTP头。请求还可能包含一个特定内容类型的数据类的主体部分。
  
  Response:响应是对请求的回复，包含状态码、HTTP头和主体部分。
  
  Call:OkHttp使用Call抽象出一个满足请求的模型，尽管中间可能会有多个请求或响应。执行Call有两种方式，同步或异步
  
  
  1. 第一步创建OKHttpClient
  
  
      // OKhttpClient.java
      public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
      
        final Dispatcher dispatcher;//处理异步请求分发策略
        final @Nullable Proxy proxy;
        final List<Protocol> protocols;
        final List<ConnectionSpec> connectionSpecs;//连接机构
        final List<Interceptor> interceptors;//应用拦截器
        final List<Interceptor> networkInterceptors;//网络拦截器
        final EventListener.Factory eventListenerFactory;
        final ProxySelector proxySelector;
        final CookieJar cookieJar;
        final @Nullable Cache cache;//缓存
        final @Nullable InternalCache internalCache;
        final SocketFactory socketFactory;
        final @Nullable SSLSocketFactory sslSocketFactory;
        final @Nullable CertificateChainCleaner certificateChainCleaner;
        final HostnameVerifier hostnameVerifier;
        final CertificatePinner certificatePinner;
        final Authenticator proxyAuthenticator;
        final Authenticator authenticator;
        final ConnectionPool connectionPool;
        final Dns dns;
        final boolean followSslRedirects;
        final boolean followRedirects;
        final boolean retryOnConnectionFailure;
        final int connectTimeout;
        final int readTimeout;
        final int writeTimeout;
        final int pingInterval;
      
        public OkHttpClient() {
          this(new Builder());
        }
      
        OkHttpClient(Builder builder) {
          this.dispatcher = builder.dispatcher;
          this.proxy = builder.proxy;
          this.protocols = builder.protocols;
          this.connectionSpecs = builder.connectionSpecs;
          this.interceptors = Util.immutableList(builder.interceptors);
          this.networkInterceptors = Util.immutableList(builder.networkInterceptors);
          this.eventListenerFactory = builder.eventListenerFactory;
          this.proxySelector = builder.proxySelector;
          this.cookieJar = builder.cookieJar;
          this.cache = builder.cache;
          this.internalCache = builder.internalCache;
          this.socketFactory = builder.socketFactory;
      
          boolean isTLS = false;
          for (ConnectionSpec spec : connectionSpecs) {
            isTLS = isTLS || spec.isTls();
          }
      
          if (builder.sslSocketFactory != null || !isTLS) {
            this.sslSocketFactory = builder.sslSocketFactory;
            this.certificateChainCleaner = builder.certificateChainCleaner;
          } else {
            X509TrustManager trustManager = Util.platformTrustManager();
            this.sslSocketFactory = newSslSocketFactory(trustManager);
            this.certificateChainCleaner = CertificateChainCleaner.get(trustManager);
          }
      
          if (sslSocketFactory != null) {
            Platform.get().configureSslSocketFactory(sslSocketFactory);
          }
      
          this.hostnameVerifier = builder.hostnameVerifier;
          this.certificatePinner = builder.certificatePinner.withCertificateChainCleaner(
              certificateChainCleaner);
          this.proxyAuthenticator = builder.proxyAuthenticator;
          this.authenticator = builder.authenticator;
          this.connectionPool = builder.connectionPool;
          this.dns = builder.dns;
          this.followSslRedirects = builder.followSslRedirects;
          this.followRedirects = builder.followRedirects;
          this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
          this.connectTimeout = builder.connectTimeout;
          this.readTimeout = builder.readTimeout;
          this.writeTimeout = builder.writeTimeout;
          this.pingInterval = builder.pingInterval;
      
          if (interceptors.contains(null)) {
            throw new IllegalStateException("Null interceptor: " + interceptors);
          }
          if (networkInterceptors.contains(null)) {
            throw new IllegalStateException("Null network interceptor: " + networkInterceptors);
          }
        }
      ....
      ....
      }
      
      //Call.java
      public interface Call extends Cloneable {
      
        Request request();
      
        Response execute() throws IOException;
      
        void enqueue(Callback responseCallback);
      
        void cancel();
  
        boolean isExecuted();
      
        boolean isCanceled();
      
        Call clone();
      
        interface Factory {
          Call newCall(Request request);
        }
      }
      
      //WebSocket.java
      public interface WebSocket {
      
          Request request();
        
          long queueSize();
        
          boolean send(String text);
        
          boolean send(ByteString bytes);
        
          boolean close(int code, @Nullable String reason);
        
          void cancel();
        
          interface Factory {
     
            WebSocket newWebSocket(Request request, WebSocketListener listener);
          }
        }
        
  
  
  总结：通过Build创建一个OKHttpClient对象，并根据配置，初始化一些缓存策略，创建一个Dispatcher对象
  
2. 第二步发送请求调用 OkHttpClient newCall


        //OKHttpClient.java
        public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
           @Override 
           public Call newCall(Request request) {
            return new RealCall(this, request, false /* for web socket */);
           }
        
        ....
        ....
        
        }
        
 总结 创建一个RealCall对象，这个对象实现了Call接口，Call接口主要包含request、execute、enqueue
3. 第三部执行异步请求
       final class RealCall implements Call {
         final OkHttpClient client;
         final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;
         private EventListener eventListener;
       
        ....
        ....
        ....
       
         @Override public void enqueue(Callback responseCallback) {
           synchronized (this) {
             if (executed) throw new IllegalStateException("Already Executed");
             executed = true;
           }
           captureCallStackTrace();
           eventListener.callStart(this);
           client.dispatcher().enqueue(new AsyncCall(responseCallback));
         }
         
         ....
         ....

        }

  
  
  总结：调用RealCall的enqueue方法，判断是否正在执行，如果否添加到dispatcher中一个AsyncCall
  
  
4. 第四部 添加到Dispatcher中


     
     // Dispatcher.java
     synchronized void enqueue(AsyncCall call) {
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
          runningAsyncCalls.add(call);
          executorService().execute(call);
        } else {
          readyAsyncCalls.add(call);
        }
      }


总结：首先判断正在运行的异步请求数量是否小于最大值，并且同一主机数量小于最大主机数量值，然后添加到运行队列，再去用线程池去执行这个call，
很明显线程池只运行Runnable，所以看一下AsyncCall代码实际上也是一个Runnable


5. 第五部 AsyncCall


       //AsyncCall.java
       final class AsyncCall extends NamedRunnable {
           private final Callback responseCallback;//回调函数
       
           AsyncCall(Callback responseCallback) {
             super("OkHttp %s", redactedUrl());
             this.responseCallback = responseCallback;
           }
       
           ....
           ....
       
           @Override protected void execute() {
             boolean signalledCallback = false;
             try {
               Response response = getResponseWithInterceptorChain();
               if (retryAndFollowUpInterceptor.isCanceled()) {
                 signalledCallback = true;
                 responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
               } else {
                 signalledCallback = true;
                 responseCallback.onResponse(RealCall.this, response);
               }
             } catch (IOException e) {
               if (signalledCallback) {
                 // Do not signal the callback twice!
                 Platform.get().log(INFO, "Callback failure for " + toLoggableString(), e);
               } else {
                 eventListener.callFailed(RealCall.this, e);
                 responseCallback.onFailure(RealCall.this, e);
               }
             } finally {
               client.dispatcher().finished(this);
             }
           }
         }
  
      //NamedRunnable.Java
      public abstract class NamedRunnable implements Runnable {
        protected final String name;
      
        public NamedRunnable(String format, Object... args) {
          this.name = Util.format(format, args);
        }
      
        @Override public final void run() {
          String oldName = Thread.currentThread().getName();
          Thread.currentThread().setName(name);
          try {
            execute();
          } finally {
            Thread.currentThread().setName(oldName);
          }
        }
      
        protected abstract void execute();
      }
  
  
  所以：实际执行了个Runnable的run方法，run方法执行了execute方法，在execute里面调用getResponseWithInterceptorChain获得响应值
  
  
  
6. 第六部getResponseWithInterceptorChain实现



    //RealCall方法
        ....
        ....
        Response getResponseWithInterceptorChain() throws IOException {
               // Build a full stack of interceptors.
               List<Interceptor> interceptors = new ArrayList<>();
               interceptors.addAll(client.interceptors());
               interceptors.add(retryAndFollowUpInterceptor);
               interceptors.add(new BridgeInterceptor(client.cookieJar()));
               interceptors.add(new CacheInterceptor(client.internalCache()));
               interceptors.add(new ConnectInterceptor(client));
               if (!forWebSocket) {
                 interceptors.addAll(client.networkInterceptors());
               }
               interceptors.add(new CallServerInterceptor(forWebSocket));
           
               Interceptor.Chain chain = new RealInterceptorChain(interceptors, null, null, null, 0,
                   originalRequest, this, eventListener, client.connectTimeoutMillis(),
                   client.readTimeoutMillis(), client.writeTimeoutMillis());
           
               return chain.proceed(originalRequest);
             }
        }
  
  
    // RealInterceptorChain.java 
    
      public Response proceed(Request request, StreamAllocation streamAllocation, HttpCodec httpCodec,
          RealConnection connection) throws IOException {
        if (index >= interceptors.size()) throw new AssertionError();
    
        calls++;
    
        // If we already have a stream, confirm that the incoming request will use it.
        if (this.httpCodec != null && !this.connection.supportsUrl(request.url())) {
          throw new IllegalStateException("network interceptor " + interceptors.get(index - 1)
              + " must retain the same host and port");
        }
    
        // If we already have a stream, confirm that this is the only call to chain.proceed().
        if (this.httpCodec != null && calls > 1) {
          throw new IllegalStateException("network interceptor " + interceptors.get(index - 1)
              + " must call proceed() exactly once");
        }
    
        // Call the next interceptor in the chain.
        RealInterceptorChain next = new RealInterceptorChain(interceptors, streamAllocation, httpCodec,
            connection, index + 1, request, call, eventListener, connectTimeout, readTimeout,
            writeTimeout);
        Interceptor interceptor = interceptors.get(index);
        Response response = interceptor.intercept(next);
    
        // Confirm that the next interceptor made its required call to chain.proceed().
        if (httpCodec != null && index + 1 < interceptors.size() && next.calls != 1) {
          throw new IllegalStateException("network interceptor " + interceptor
              + " must call proceed() exactly once");
        }
    
        // Confirm that the intercepted response isn't null.
        if (response == null) {
          throw new NullPointerException("interceptor " + interceptor + " returned null");
        }
    
        if (response.body() == null) {
          throw new IllegalStateException(
              "interceptor " + interceptor + " returned a response with no body");
        }
    
        return response;
      }
    
    
总结：getResponseWithInterceptorChain 方法内部拿到所有自定义拦截器和系统内置拦截器，然后

   创建一个RealInterceptorChain对象递归执行proceed方法，直到返回一个response
   
7. 拦截器执行说明


    1）在配置 OkHttpClient 时设置的 interceptors； 
    2）负责失败重试以及重定向的 RetryAndFollowUpInterceptor； 
    3）负责把用户构造的请求转换为发送到服务器的请求、把服务器返回的响应转换为用户友好的响应的 BridgeInterceptor； 
    4）负责读取缓存直接返回、更新缓存的 CacheInterceptor； 
    5）负责和服务器建立连接的 ConnectInterceptor； 
    6）配置 OkHttpClient 时设置的 networkInterceptors； 
    7）负责向服务器发送请求数据、从服务器读取响应数据的 CallServerInterceptor。
    
    OkHttp的这种拦截器链采用的是责任链模式，这样的好处是将请求的发送和处理分开，并且可以动态添加中间的处理方实现对请求的处理、短路等操作
      
  
总结最终是由CallServerInterceptor去发送给服务器

8. 看一下 CallServerInterceptor
  
  
      
      public final class CallServerInterceptor implements Interceptor {
        private final boolean forWebSocket;
      
        public CallServerInterceptor(boolean forWebSocket) {
          this.forWebSocket = forWebSocket;
        }
      
        @Override public Response intercept(Chain chain) throws IOException {
          RealInterceptorChain realChain = (RealInterceptorChain) chain;
          HttpCodec httpCodec = realChain.httpStream();
          StreamAllocation streamAllocation = realChain.streamAllocation();
          RealConnection connection = (RealConnection) realChain.connection();
          Request request = realChain.request();
      
          long sentRequestMillis = System.currentTimeMillis();
      
          realChain.eventListener().requestHeadersStart(realChain.call());
          httpCodec.writeRequestHeaders(request);
          realChain.eventListener().requestHeadersEnd(realChain.call(), request);
      
          Response.Builder responseBuilder = null;
          if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
            // If there's a "Expect: 100-continue" header on the request, wait for a "HTTP/1.1 100
            // Continue" response before transmitting the request body. If we don't get that, return
            // what we did get (such as a 4xx response) without ever transmitting the request body.
            if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
              httpCodec.flushRequest();
              realChain.eventListener().responseHeadersStart(realChain.call());
              responseBuilder = httpCodec.readResponseHeaders(true);
            }
      
            if (responseBuilder == null) {
              // Write the request body if the "Expect: 100-continue" expectation was met.
              realChain.eventListener().requestBodyStart(realChain.call());
              long contentLength = request.body().contentLength();
              CountingSink requestBodyOut =
                  new CountingSink(httpCodec.createRequestBody(request, contentLength));
              BufferedSink bufferedRequestBody = Okio.buffer(requestBodyOut);
      
              request.body().writeTo(bufferedRequestBody);
              bufferedRequestBody.close();
              realChain.eventListener()
                  .requestBodyEnd(realChain.call(), requestBodyOut.successfulCount);
            } else if (!connection.isMultiplexed()) {
              // If the "Expect: 100-continue" expectation wasn't met, prevent the HTTP/1 connection
              // from being reused. Otherwise we're still obligated to transmit the request body to
              // leave the connection in a consistent state.
              streamAllocation.noNewStreams();
            }
          }
      
          httpCodec.finishRequest();
      
          if (responseBuilder == null) {
            realChain.eventListener().responseHeadersStart(realChain.call());
            responseBuilder = httpCodec.readResponseHeaders(false);
          }
      
          Response response = responseBuilder
              .request(request)
              .handshake(streamAllocation.connection().handshake())
              .sentRequestAtMillis(sentRequestMillis)
              .receivedResponseAtMillis(System.currentTimeMillis())
              .build();
      
          int code = response.code();
          if (code == 100) {
            // server sent a 100-continue even though we did not request one.
            // try again to read the actual response
            responseBuilder = httpCodec.readResponseHeaders(false);
      
            response = responseBuilder
                    .request(request)
                    .handshake(streamAllocation.connection().handshake())
                    .sentRequestAtMillis(sentRequestMillis)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .build();
      
            code = response.code();
          }
      
          realChain.eventListener()
                  .responseHeadersEnd(realChain.call(), response);
      
          if (forWebSocket && code == 101) {
            // Connection is upgrading, but we need to ensure interceptors see a non-null response body.
            response = response.newBuilder()
                .body(Util.EMPTY_RESPONSE)
                .build();
          } else {
            response = response.newBuilder()
                .body(httpCodec.openResponseBody(response))
                .build();
          }
      
          if ("close".equalsIgnoreCase(response.request().header("Connection"))
              || "close".equalsIgnoreCase(response.header("Connection"))) {
            streamAllocation.noNewStreams();
          }
      
          if ((code == 204 || code == 205) && response.body().contentLength() > 0) {
            throw new ProtocolException(
                "HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
          }
      
          return response;
        }
    
      
      
          //StreamAllocation.java
          public HttpCodec newStream(
                OkHttpClient client, Interceptor.Chain chain, boolean doExtensiveHealthChecks) {
              int connectTimeout = chain.connectTimeoutMillis();
              int readTimeout = chain.readTimeoutMillis();
              int writeTimeout = chain.writeTimeoutMillis();
              int pingIntervalMillis = client.pingIntervalMillis();
              boolean connectionRetryEnabled = client.retryOnConnectionFailure();
          
              try {
                RealConnection resultConnection = findHealthyConnection(connectTimeout, readTimeout,
                    writeTimeout, pingIntervalMillis, connectionRetryEnabled, doExtensiveHealthChecks);
                HttpCodec resultCodec = resultConnection.newCodec(client, chain, this);
          
                synchronized (connectionPool) {
                  codec = resultCodec;
                  return resultCodec;
                }
              } catch (IOException e) {
                throw new RouteException(e);
              }
            }
  
总结：通过StreamAllocation 查找从ConnectionPool连接池查找一个RealConnection 然后创建一个HttpCodec（http编解码器）


9. ConnectionPool
  // 连接池用一个队列保存
     private final Deque<RealConnection> connections = new ArrayDeque<>();
    
     public ConnectionPool() {
        this(5, 5, TimeUnit.MINUTES);
      }
10. RealConnection  


    //建立连接
    private void connectSocket(int connectTimeout, int readTimeout, Call call,
        EventListener eventListener) throws IOException {
      ....
      ....
      try {
      Okio 有自己的流类型，那就是 Source 和 Sink，它们和 InputStream 与 OutputStream 类似，前者为输入流，后者为输出流。
        source = Okio.buffer(Okio.source(rawSocket));
        sink = Okio.buffer(Okio.sink(rawSocket));
      } catch (NullPointerException npe) {
        if (NPE_THROW_WITH_NULL.equals(npe.getMessage())) {
          throw new IOException(npe);
        }
      }
    }
      
  总结:连接池默认是维持5个连接数，也就是socket连接数,建立链接会返回一个source 和 sink，
  Okio 有自己的流类型，那就是 Source 和 Sink，它们和 InputStream 与 OutputStream 类似，前者为输入流，后者为输出流。
  
  

    
10. 最终通过 httpCodec.finishRequest();发送请求 通过OutputStream，而最后通过OutputStream读取网络请求

11.请求结束后Dispatcher调用finish移除队列

#### 总结：


    1. 通过OKHttpClient 创建了一个Dispatcher ；
    2. 通过调用OKHttpClient newCall创建了一个RealCall；
    3. RealCall调用enqueue传入一个callBack
    4. enqueue方法里面通过持有的OKHttpClient的dispatcher调用enqueue方法放入一个AsyncCall
    5. AsynCall实现了Runnable方法
    6. AsynCall的Runnable run方法实现：调用getResponseWithInterceptorChain去执行请求
    7. getResponseWithInterceptorChain 递归调用所有拦截器去执行，而到ConnectionInercepter建立链接，最后CallServerIntercepter才去发送请求
    8. Dispatcher内部会创建一个线程池，调用enqueue先加入队列，再去执行这个Runanble
    
    
12. Dispatcher 进一步分析


          public final class Dispatcher {
          /** 最大并发请求数为64 */
          private int maxRequests = 64;
          /** 每个主机最大请求数为5 */
          private int maxRequestsPerHost = 5;
        
          /** 线程池 */
          private ExecutorService executorService;
        
          /** 准备执行的请求 */
          private final Deque<AsyncCall> readyAsyncCalls = new ArrayDeque<>();
        
          /** 正在执行的异步请求，包含已经取消但未执行完的请求 */
          private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque<>();
        
          /** 正在执行的同步请求，包含已经取消单未执行完的请求 */
          private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>()；
          
          public synchronized ExecutorService executorService() {
              if (executorService == null) {
                 executorService = new ThreadPoolExecutor(
                                  //corePoolSize 最小并发线程数,如果是0的话，空闲一段时间后所有线程将全部被销毁
                                      0, 
                                  //maximumPoolSize: 最大线程数，当任务进来时可以扩充的线程最大值，当大于了这个值就会根据丢弃处理机制来处理
                                      Integer.MAX_VALUE, 
                                  //keepAliveTime: 当线程数大于corePoolSize时，多余的空闲线程的最大存活时间
                                      60, 
                                  //单位秒
                                      TimeUnit.SECONDS,
                                  //工作队列,先进先出
                                      new SynchronousQueue<Runnable>(),   
                                  //单个线程的工厂         
                                     Util.threadFactory("OkHttp Dispatcher", false));
              }
              return executorService;
            }

总结：可以看出，在Okhttp中，构建了一个核心为[0, Integer.MAX_VALUE]的线程池，它不保留任何最小线程数，随时创建更多的线程数，当线程空闲时只能活60秒，它使用了一个不存储元素的阻塞工作队列，一个叫做”OkHttp Dispatcher”的线程工厂。

也就是说，在实际运行中，当收到10个并发请求时，线程池会创建十个线程，当工作完成后，线程池会在60s后相继关闭所有线程。


Dispatcher线程池总结

1）调度线程池Disptcher实现了高并发，低阻塞的实现 
2）采用Deque作为缓存，先进先出的顺序执行 
3）任务在try/finally中调用了finished函数，控制任务队列的执行顺序，而不是采用锁，减少了编码复杂性提高性能


13. 其他拦截器分析




  
  
  
  