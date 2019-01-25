

### WorkManager


1. 简介: 就是 ”管理一些要在后台工作的任务, – 即使你的应用没启动也能保证任务能被执行”。

2. 为啥不用AsyncTask, ThreadPool, RxJava?
  这三个和WorkManager并不是替代的关系. 这三个工具, 能帮助你在应用中开后台线程干活, 但是应用一被杀或被关闭, 这些工具就干不了活了。
  而WorkManager不是, 它在应用被杀, 甚至设备重启后仍能保证你安排给他的任务能得到执行。
  其实Google自己也说了:”WorkManager并不是为了那种在应用内的后台线程而设计出来的.
  如果你有这种需求你应该使用ThreadPool”。

3. 那为何不用JobScheduler, AlarmManger来做?
  其实这个想法很对. WorkManager在底层也是看你是什么版本来选到底是JobScheduler, AlamarManager来做。
  JobScheduler是Android 5.x才有的. 而AlarmManager一直存在. 所以WorkManager在底层, 会根据你的设备情况, 选用JobScheduler, Firebase的JobDispatcher, 或是AlarmManager。

#### WorkManager使用

1. 导入jar包

    Kotlin

         implementation "android.arch.work:work-runtime-ktx:1.0.0-alpha01"

    Java

         implementation "android.arch.work:work-runtime:1.0.0-alpha01"

2. 首先创建一个XXXWork继承Work类，实现doWork方法，doWork方法就可以执行你的任务了

   1. doWork没有参数，怎么传递参数呢？通过setInputData()和getInputData()
   2. 如何返回数据？通过setOutData()和getOutData()
   3. 结果如何监听？通过 workManager.getStatusById(uuid).observe()
   4. 监听状态有几种? ENQUEUED RUNNING SUCCEEDED

3.两种work类型，一种执行一次；一种周期性执行

       //notice 一次的请求
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(
                UploadWork.class
        ).setConstraints(constraints).setInputData(inputData).build();


        //notice 执行定时任务：设置工作的时间间隔
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(UploadWork
                .class, 1, TimeUnit.MINUTES).addTag(UploadWork.TAG)
                .setConstraints(constraints).setInputData(inputData).build();

4. 设置约束
     1. setRequiredNetworkType 网络
     2. setRequiresBatteryNotLow 电量
     3. setRequiresCharging 是否充电
     4. setRequiresDeviceIdle 是否是idle状态
     5. setRequiresStorageNotLow存储是否低

5. workManager优点

   1. Easy to schedule
   2. Easy to cancel
   3. Easy to query
   4. Support for all android versions

6. 示例：

     /**
         * 模拟一个网络请求
         */
        private void exeWorkByNetWork() {
            //notice 创建约束条件
            Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            //notice 输入数据
            Data inputData = new Data.Builder().putBoolean("isTest", true).build();

            //notice 构建请求类型，一次的请求
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(
                    UploadWork.class
            ).setConstraints(constraints).setInputData(inputData).build();

            //notice 设置结果回调
            WorkManager.getInstance().getStatusById(oneTimeWorkRequest.getId())
                    .observe(this, new Observer<WorkStatus>() {
                        @Override
                        public void onChanged(@Nullable WorkStatus workStatus) {

                            if (workStatus != null && workStatus.getState() == State.SUCCEEDED) {
                                //notice 取出回调数据
                                String result = workStatus.getOutputData().getString("result", "");
                                Log.i("result","workStatus="+workStatus.getState());
                                toast(result);

                            }
                        }
                    });
            //notice 执行
            WorkManager.getInstance().enqueue(oneTimeWorkRequest);


            /**
             * 你也可以让多个任务按顺序执行：

             WorkManager.getInstance(this)
             .beginWith(Work.from(LocationWork.class))
             .then(Work.from(LocationUploadWorker.class))
             .enqueue();
             你还可以让多个任务同时执行：

             WorkManager.getInstance(this).enqueue(Work.from(LocationWork.class,
             LocationUploadWorker.class));
             */
        }


