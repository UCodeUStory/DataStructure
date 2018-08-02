#### Application 多进程问题

在做项目时，遇到一个大坑，就是我的APP 的Application 的onCreate方法，竟然执行了好几次，这就导致我在onCreate里面做了一些初始化的操作被重复执行了，导致奇怪的bug产生。后来冷静下来分析一下，才发现有一些第三方组件，比如百度推送之类的，它们是单独开了一个进程，那么每个进程会自己初始化自己的Application，那自然onCreate方法会多次执行。准确的说就是你的APP里有多少个进程，就会初始化多少次Application 。

但是有的东西就是只需要在Application 的onCreate 里只初始化一次。那怎么解决呢？看代码：



    
    public class MyApplication extends Application {
        private final static String PROCESS_NAME = "com.test";
        private static MyApplication myApplication = null;
     
        public static MyApplication getApplication() {
            return myApplication;
        }
     
        /**
         * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
         */
        public static boolean isAppMainProcess() {
            try {
                int pid = android.os.Process.myPid();
                String process = getAppNameByPID(MyApplication.getApplication(), pid);
                if (TextUtils.isEmpty(process)) {
                    return true;
                } else if (PROCESS_NAME.equalsIgnoreCase(process)) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
     
        /**
         * 根据Pid得到进程名
         */
        public static String getAppNameByPID(Context context, int pid) {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
            return "";
        }
     
        @Override
        public void onCreate() {
            super.onCreate();
     
            myApplication = this;
     
            if (isAppMainProcess()) {
                //do something for init
                //这里就只会初始化一次
            }
        }
    }