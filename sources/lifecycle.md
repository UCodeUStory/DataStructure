#### Lifecycle 源码分析



#### Activity事件是如何分发的
     //SupportActivity 有个LifecycleRegistry 一个lifecycle登记处
     private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

     public Lifecycle getLifecycle() {
         return this.mLifecycleRegistry;
     }
    
     //SupportActivity里面
     protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ReportFragment.injectIfNeededIn(this);
        }


    public class ReportFragment extends Fragment {
        private static final String REPORT_FRAGMENT_TAG = "android.arch.lifecycle"
                + ".LifecycleDispatcher.report_fragment_tag";
    
        public static void injectIfNeededIn(Activity activity) {
            // ProcessLifecycleOwner should always correctly work and some activities may not extend
            // FragmentActivity from support lib, so we use framework fragments for activities
            android.app.FragmentManager manager = activity.getFragmentManager();
            if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {
                manager.beginTransaction().add(new ReportFragment(), REPORT_FRAGMENT_TAG).commit();
                // Hopefully, we are the first to make a transaction.
                manager.executePendingTransactions();
            }
        }
    
        static ReportFragment get(Activity activity) {
            return (ReportFragment) activity.getFragmentManager().findFragmentByTag(
                    REPORT_FRAGMENT_TAG);
        }
    
        private ActivityInitializationListener mProcessListener;
    
        private void dispatchCreate(ActivityInitializationListener listener) {
            if (listener != null) {
                listener.onCreate();
            }
        }
    
        private void dispatchStart(ActivityInitializationListener listener) {
            if (listener != null) {
                listener.onStart();
            }
        }
    
        private void dispatchResume(ActivityInitializationListener listener) {
            if (listener != null) {
                listener.onResume();
            }
        }
    
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            dispatchCreate(mProcessListener);
            dispatch(Lifecycle.Event.ON_CREATE);
        }
    
        @Override
        public void onStart() {
            super.onStart();
            dispatchStart(mProcessListener);
            dispatch(Lifecycle.Event.ON_START);
        }
    
        @Override
        public void onResume() {
            super.onResume();
            dispatchResume(mProcessListener);
            dispatch(Lifecycle.Event.ON_RESUME);
        }
    
        @Override
        public void onPause() {
            super.onPause();
            dispatch(Lifecycle.Event.ON_PAUSE);
        }
    
        @Override
        public void onStop() {
            super.onStop();
            dispatch(Lifecycle.Event.ON_STOP);
        }
    
        @Override
        public void onDestroy() {
            super.onDestroy();
            dispatch(Lifecycle.Event.ON_DESTROY);
            // just want to be sure that we won't leak reference to an activity
            mProcessListener = null;
        }
    
        private void dispatch(Lifecycle.Event event) {
            Activity activity = getActivity();
            if (activity instanceof LifecycleRegistryOwner) {
                ((LifecycleRegistryOwner) activity).getLifecycle().handleLifecycleEvent(event);
                return;
            }
    
            if (activity instanceof LifecycleOwner) {
                Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
                if (lifecycle instanceof LifecycleRegistry) {
                    ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
                }
            }
        }
    
        void setProcessListener(ActivityInitializationListener processListener) {
            mProcessListener = processListener;
        }
    
        interface ActivityInitializationListener {
            void onCreate();
    
            void onStart();
    
            void onResume();
        }
    }
    
    
       利用ReportActivity,他是无视图的Fragment
    
        无视图Fragment的技巧的优点：
    
        他与创建他的Activity的生命周期一致（让这个Fragment具有生命周期感知能力），可以在不修改原有Activity生命周期代码的情况下，用Fragment来从外部插入方法。可拔插，解耦合，非常灵活。



-  LifecycleRegistry 

          // 早起版本Activity还没有实现LifecycleOwner
          
          public class MainActivity extends Activity implements LifecycleOwner {
              private LifecycleRegistry mLifecycleRegistry;
          
              @Override
              protected void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_main);
          
                  mLifecycleRegistry = new LifecycleRegistry(this);
                  mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
                  mLifecycleRegistry.addObserver(new TestObserver());
              }
          
              @NonNull
              @Override
              public Lifecycle getLifecycle() {
                  return mLifecycleRegistry;
              }
          
              @Override
              public void onStart() {
                  super.onStart();
                  mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
              }
          
              @Override
              public void onResume() {
                  super.onResume();
                  mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
              }
          
              @Override
              public void onPause() {
                  mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
                  super.onPause();
              }
          
              @Override
              public void onStop() {
                  mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                  super.onStop();
              }
          
              @Override
              public void onDestroy() {
                  mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
                  super.onDestroy();
              }
          }
          
       

          public class LifecycleRegistry extends Lifecycle {
        
          public LifecycleRegistry(@NonNull LifecycleOwner provider) {
                //保存了Activity弱引用，防止Activity被销毁其他地方还持有lifecycle
                mLifecycleOwner = new WeakReference<>(provider);
                mState = INITIALIZED;
            }
            
          public void addObserver(@NonNull LifecycleObserver observer) {
          
 ####  handleLifecycleEvent分发状态
 
 
     public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
         State next = getStateAfter(event);
         moveToState(next);
     }
 
     private void moveToState(State next) {
         if (mState == next) {
             return;
         }
         mState = next;
         if (mHandlingEvent || mAddingObserverCounter != 0) {
             mNewEventOccurred = true;
             // we will figure out what to do on upper level.
             return;
         }
         mHandlingEvent = true;
         //这个方法里面遍历观察者进行分发
         sync();
         mHandlingEvent = false;
     }