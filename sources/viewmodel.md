### ViewModel 源码分析

1. ViewModel

     ViewModel，从字面上理解的话，它肯定是跟视图(View)以及数据(Model)相关的。正像它字面意思一样，它是负责准备和管理和UI组件(Fragment/Activity)相关的数据类，也就是说ViewModel是用来管理UI相关的数据的，同时ViewModel还可以用来负责UI组件间的通信。

2. ViewModel 解决什么问题

  1. 在MVP模式中，我们Presenter是无声明周期感知的，当异步处理数据很容易造成内存泄露，通常解决办法写一些声明周期函数，提供回调
     （ViewModel感知声明周期 不会内存泄露）
  2. 当Activity因为配置变化而销毁重建时，一般数据会重新请求，其实这是一种浪费，最好就是能够保留上次的数据，通常解决方法在onSaveinstance保存数据，单不适合保存大量数据，不方便序列化，会导致OOM，或通过Presenter单例模式，浪费资源
      (ViewModel 可以在Activity重建是保存数据，怎么做到的？？下面一会细说）
  3. UI controllers其实只需要负责展示UI数据、响应用户交互和系统交互即可，在MVP模式中的P解决了这一问题，ViewModel 类似这个P的责任


3. 原理剖析
   主要类：
   1. ViewModelProviders  提供不同种类方法创建ViewModelProvider
   2. ViewModelProvider  用来创建ViewModel的，里面持有 Factory  和ViewModelStore
   3. Factory  一个抽象类，实现类决定如何创建ViewModel,有一个默认实现ViewModelProvider里，如果我们实现的ViewModel参数不仅仅一个application,就需要自定义工厂方法
   4. ViewModel 我们最终需要的对象
   5. ViewModelStores 用来创建ViewModelStore,但首先要先创建HoldFragment
   6. HoldFragmentManager 维护两个Map
   7. HoldFragment 用来感知生命周期变化，做相应处理
   8. ViewModelStore 维护一个Map 来保存ViewModel


重点说一下：HolderFragment ,
1. 在ViewModelStores 我们会调用 holderFragmentFor，有两个方法，一个是传递FragmentActivity,一个是传递Fragment

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        public static HolderFragment holderFragmentFor(FragmentActivity activity) {
               return sHolderFragmentManager.holderFragmentFor(activity);
        }

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        public static HolderFragment holderFragmentFor(Fragment fragment) {
            return sHolderFragmentManager.holderFragmentFor(fragment);
        }

2. 继续看HolderFragmentManager


        HolderFragment holderFragmentFor(FragmentActivity activity) {
            FragmentManager fm = activity.getSupportFragmentManager();
            // 有没有已经attach的, 有就直接返回 HolderFragment，这个放在就是再Activity重建的时候，并且HolderFrament 设置 setRetainInstance(true)时，才会有值，否者其他情况都为Null
            HolderFragment holder = findHolderFragment(fm);
            if (holder != null) {
                return holder;
            }
            // 这个集合在HolderFragment onCreate前保存，在onCreate后被移除
            holder = mNotCommittedActivityHolders.get(activity);
            if (holder != null) {
                return holder;
            }
            // 此方法监听一下Activity声明周期，只会调用一次
            if (!mActivityCallbacksIsAdded) {
                mActivityCallbacksIsAdded = true;
                activity.getApplication().registerActivityLifecycleCallbacks(mActivityCallbacks);
            }
            // 创建一个新的HolderFragment，不会被立即onAttach
            holder = createHolderFragment(fm);
            // 先添加到缓存，当我们同时在一个Activity中创建很多个ViewModel 时从缓存这里拿也就是先Activity onCreate 执行完才执行attach 、onCreate方法
            // 注意 这里和ViewModel在哪个位置创建有关，如果在Activity onCreate 创建这个里面会被保存，每次从map里面拿，如果写在onResume里面
            // 就会从findHolderFragment拿， 同时mNotCommittedActivityHolders集合里面也没有元素了，因为onResume里创建都会被立即执行attach onCreate，而onCreate里面调用了remove方法
            mNotCommittedActivityHolders.put(activity, holder);
            return holder;
        }


        private static HolderFragment createHolderFragment(FragmentManager fragmentManager) {
            HolderFragment holder = new HolderFragment();
            fragmentManager.beginTransaction().add(holder, HOLDER_TAG).commitAllowingStateLoss();
            return holder;
        }

         private static HolderFragment findHolderFragment(FragmentManager manager) {
                    if (manager.isDestroyed()) {
                        throw new IllegalStateException("Can't access ViewModels from onDestroy");
                    }

                    Fragment fragmentByTag = manager.findFragmentByTag(HOLDER_TAG);
                    if (fragmentByTag != null && !(fragmentByTag instanceof HolderFragment)) {
                        throw new IllegalStateException("Unexpected "
                                + "fragment instance was returned by HOLDER_TAG");
                    }
                    return (HolderFragment) fragmentByTag;
                }


3.  总结

    1. 之前一直不懂为什么中间要多一层HolderFragment，而不直接依赖于ViewModelStore； 原因在于Fragment可以设置setRetainInstance(true) 来保证Fragment
    不会在Activity重建时也重建 并配合 findHolderFragment(fm) 来获取之前已绑定的Fragment

    2. 之前一直不理解HolderFragmentManager中mNotCommittedActivityHolders集合的生命周期，并且在全局监听 onDestroy 移除一次，HolderFragment onCreate 后也移除一次

       我们要知道一件事情 Activity onCreate方法执行完后  才会执行Fragment的 onAttach  onCreate onCreateView onActivityCreate

       因此当我们在Activity onCreate 创建很多个ViewModel 时，先findHolderFragment肯定查不到（只有Activity onCreate执行完）我们就会先放入map缓存一个，然后后面都从缓存获取，等到Activity onCreate 执行结束，HolderFragment的  onCreate执行，然后调用
       sHolderFragmentManager.holderFragmentCreated(this);从map中移除

       实验，如果Activity onResume 创建 先放入map缓存，然后立刻执行HolderFragment onCreate 移除，接下来的就会从findHolderFragment获取到，因为已经onAttach

    3. 最后当界面结束的时候还会调用mActivityCallbacks的onDestroy来再一次移除map中数据，这个实验，基本集合都已经为Null，这应该是作者害怕使用者在其他线程调用导致多线程map移除不掉的问题导致内存泄露，或者还未绑定界面意外崩溃程序死亡等情况，如果在onCreate中创建就不会有问题，所以使用的时候就乖乖在onCreate中使用吧




