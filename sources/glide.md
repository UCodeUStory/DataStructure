### Glide 源码分析

1. Glide.with(context)  调用GlideBuilder创建一个Glide ,最终返回一个RequestManager
2. GlideBuilder.build 会创建这些

        return new Glide(
            context,
            engine,
            memoryCache,
            bitmapPool,
            arrayPool,
            requestManagerRetriever,
            connectivityMonitorFactory,
            logLevel,
            defaultRequestOptions.lock());
        }

3. RequestManager.load()方法,


         // RequestManager 380行  通过前面创建的glide 创建个RequestBuilder
         public <ResourceType> RequestBuilder<ResourceType> as(Class<ResourceType> resourceClass) {
           return new RequestBuilder<>(glide, this, resourceClass);
         }

          // RequestBuilder 190行

          @SuppressWarnings("unchecked")
          public RequestBuilder<TranscodeType> load(@Nullable Object model) {
            return loadGeneric(model);
          }
          private RequestBuilder<TranscodeType> loadGeneric(@Nullable Object model) {
             this.model = model;
             isModelSet = true;
             return this;
           }

4. RequestBuilder.apply 配置请求参数

5. RequestBuilder.into


        //RequestBuilder  381 通过GlideContext 封装Target
        public Target<TranscodeType> into(ImageView view) {
         .....
         return into(context.buildImageViewTarget(view, transcodeClass));
        }

        // RequestBuilder 349行
        public <Y extends Target<TranscodeType>> Y into(@NonNull Y target) {
           Util.assertMainThread();
           Preconditions.checkNotNull(target);
           if (!isModelSet) {
             throw new IllegalArgumentException("You must call #load() before calling #into()");
           }

           Request previous = target.getRequest();

           if (previous != null) {
             requestManager.clear(target);
           }

           requestOptions.lock();
           Request request = buildRequest(target);
           target.setRequest(request);
           requestManager.track(target, request);

           return target;
         }
 6. RequestBuilder 633行 构建request SingleRequest

  private Request obtainRequest(Target<TranscodeType> target,
          RequestOptions requestOptions, RequestCoordinator requestCoordinator,
          TransitionOptions<?, ? super TranscodeType> transitionOptions, Priority priority,
          int overrideWidth, int overrideHeight) {
        requestOptions.lock();

        return SingleRequest.obtain(
            context,
            model,
            transcodeClass,
            requestOptions,
            overrideWidth,
            overrideHeight,
            priority,
            target,
            requestListener,
            requestCoordinator,
            context.getEngine(),
            transitionOptions.getTransitionFactory());
      }

  7. RequestManager 446 发送请求


      void track(Target<?> target, Request request) {
        targetTracker.track(target);
        requestTracker.runRequest(request);
      }


  8. SingleRequest.runRequest

          // 399行
          onSizeReady()

          engine.load()

  9. Engine load()
      // 212 行，开启一个线程
      engineJob.start(decodeJob);

  10. EngineJob start() 行


           public void start(DecodeJob<R> decodeJob) {
              this.decodeJob = decodeJob;
              // 过去Glide线程池，核心线程是1，最大线程4，Keep alive 10秒
              GlideExecutor executor = decodeJob.willDecodeFromCache()
                  ? diskCacheExecutor
                  : getActiveSourceExecutor();
              executor.execute(decodeJob);
            }

   11. DecodeJob run()  212行


          - 调用 runWrapped 244



                  private void runWrapped() {
                       switch (runReason) {
                        case INITIALIZE:
                          stage = getNextStage(Stage.INITIALIZE);
                          currentGenerator = getNextGenerator();
                          runGenerators();
                          break;
                        case SWITCH_TO_SOURCE_SERVICE:
                          runGenerators();
                          break;
                        case DECODE_DATA:
                          decodeFromRetrievedData();
                          break;
                        default:
                          throw new IllegalStateException("Unrecognized run reason: " + runReason);
                      }
                    }

          - 调用 runGenerators  277


           private void runGenerators() {
              currentThread = Thread.currentThread();
              startFetchTime = LogTime.getLogTime();
              boolean isStarted = false;
              while (!isCancelled && currentGenerator != null
                  && !(isStarted = currentGenerator.startNext())) {
                stage = getNextStage(stage);
                currentGenerator = getNextGenerator();

                if (stage == Stage.SOURCE) {
                  reschedule();
                  return;
                }
              }
              // We've run out of stages and generators, give up.
              if ((stage == Stage.FINISHED || isCancelled) && !isStarted) {
                notifyFailed();
              }

              // Otherwise a generator started a new load and we expect to be called back in
              // onDataFetcherReady.
            }

    12. DataFetcherGenerator ResourceCacheGenerator startNext()
        // 从磁盘缓存中查找
        cacheFile = helper.getDiskCache().get(currentKey);



        // 然后放到activiteResource 里面
        // 但是用完后，再放入LruCache 里面



#### activeResources写入
        // Engine 283

        @Override
        public void onEngineJobComplete(Key key, EngineResource<?> resource) {
            Util.assertMainThread();
            // A null resource indicates that the load failed, usually due to an exception.
            if (resource != null) {
                resource.setResourceListener(key, this);
                if (resource.isCacheable()) {
                    activeResources.put(key, new ResourceWeakReference(key, resource, getReferenceQueue()));
                }
            }
            jobs.remove(key);
        }

#### EngineResource   private int acquired;用来做引用计数

#### 当引用计数为0的时候会将图片放到LruCache中。  Engine 313


    @Override
    public void onResourceReleased(Key cacheKey, EngineResource resource) {
        Util.assertMainThread();
        activeResources.remove(cacheKey);
        if (resource.isCacheable()) {
            cache.put(cacheKey, resource);
        } else {
            resourceRecycler.recycle(resource);
        }
    }



当滑动图片列表的时候，系统会根据需要将这些图片资源给回收掉，所以activeResources.get(key).get()得到的就会为空，为空也没有必要添加到LruCache中了。
但是这样子一来，activeResources中就会有很多没有用的项了，而它们又没有被移除掉。为了解决MessageQueue.IdleHandler这个问题。
（IdleHandler也可以用来解决App启动延时加载的问题，具体可以看Android启动优化之延时加载）



    private static class RefQueueIdleHandler implements MessageQueue.IdleHandler {
        private final Map<Key, WeakReference<EngineResource<?>>> activeResources;
        private final ReferenceQueue<EngineResource<?>> queue;

        public RefQueueIdleHandler(Map<Key, WeakReference<EngineResource<?>>> activeResources,
                ReferenceQueue<EngineResource<?>> queue) {
            this.activeResources = activeResources;
            this.queue = queue;
        }

        @Override
        public boolean queueIdle() {
            // 被回收掉的要移除
            ResourceWeakReference ref = (ResourceWeakReference) queue.poll();
            if (ref != null) {
                activeResources.remove(ref.key);
            }

            return true;
        }
    }



Glide加载默认情况下可以分为三级缓存，哪三级呢？他们分别是内存、磁盘和网络。


默认情况下，Glide 会在开始一个新的图片请求之前检查以下多级的缓存：

1.活动资源 (Active Resources) - 现在是否有另一个 View 正在展示这张图片
2.内存缓存 (Memory cache) - 该图片是否最近被加载过并仍存在于内存中
3.资源类型（Resource） - 该图片是否之前曾被解码、转换并写入过磁盘缓存
4.数据来源 (Data) - 构建这个图片的资源是否之前曾被写入过文件缓存


