### AsyncTask 源码分析


1. 面根据CPU数量创建一个 2到4的核心线程池，最大线程池时CPU*2 +1，存货30秒，队列为128
并且默认是Serial_Excutor,而非并发


线程一个一个的执行，通过来一个请求让其加入到一个队列中，然后在这个请求执行完后去执行下一个

这里设计很气面，同时设置一个变量判断当前是否有正在执行的

            public synchronized void execute(final Runnable r) {
                    mTasks.offer(new Runnable() {
                        public void run() {
                            try {
                                r.run();
                            } finally {
                                scheduleNext();
                            }
                        }
                    });
                    // 第一次，或者前面都执行完了
                    if (mActive == null) {
                        scheduleNext();
                    }
                }
    
            protected synchronized void scheduleNext() {
                if ((mActive = mTasks.poll()) != null) {
                    THREAD_POOL_EXECUTOR.execute(mActive);
                }
            }




    
     private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        // We want at least 2 threads and at most 4 threads in the core pool,
        // preferring to have 1 less than the CPU count to avoid saturating
        // the CPU with background work
        private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
        private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        private static final int KEEP_ALIVE_SECONDS = 30;
    
        private static final ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);
    
            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
            }
        };
        
        private static final BlockingQueue<Runnable> sPoolWorkQueue =
                    new LinkedBlockingQueue<Runnable>(128);
        
            /**
             * An {@link Executor} that can be used to execute tasks in parallel.
             */
            public static final Executor THREAD_POOL_EXECUTOR;
        
            static {
                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                        CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                        sPoolWorkQueue, sThreadFactory);
                threadPoolExecutor.allowCoreThreadTimeOut(true);
                THREAD_POOL_EXECUTOR = threadPoolExecutor;
            }


    
       private static class SerialExecutor implements Executor {
            final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
            Runnable mActive;
    
            public synchronized void execute(final Runnable r) {
                mTasks.offer(new Runnable() {
                    public void run() {
                        try {
                            r.run();
                        } finally {
                            scheduleNext();
                        }
                    }
                });
                if (mActive == null) {
                    scheduleNext();
                }
            }
    
            protected synchronized void scheduleNext() {
                if ((mActive = mTasks.poll()) != null) {
                    THREAD_POOL_EXECUTOR.execute(mActive);
                }
            }
        }