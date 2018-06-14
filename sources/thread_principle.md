### 线程池原理



        /**
         * Created by qiyue on 2018/6/14.
         */
        
        public class USThreadPool {
        
            private Set<WorkThread> threadSet = new HashSet<>();
        
            private Queue<Runnable> queue = new LinkedList<>();
        
            private static final String TAG = USThreadPool.class.getSimpleName();
        
            public USThreadPool(int size){
        
                for (int i=0;i<size;i++){
                    WorkThread workThread = new WorkThread();
                    workThread.start();
                    threadSet.add(workThread);
                }
            }
        
            public void submit(Runnable runnable){
                queue.add(runnable);
            }
        
        
            class WorkThread extends Thread{
                @Override
                public void run() {
                    super.run();
        
                    while (true){
                        Log.i(TAG,"Thread-id="+getId());
                        if(!queue.isEmpty()){
                            Log.i(TAG,"Thread-id2="+getId());
                            Runnable runnable = queue.poll();
                            Log.i(TAG,"Thread-id3="+getId());
                            runnable.run();
                        }
                    }
                }
            }
        }

*当我们创建一个线程池大小为5的线程池时，就会有5个WorkerThread 在工作不停的重queue队列获取任务然后执行*