ButterKnife源码分析



 1. 编译器干了什么

    扫描注解，获取所有带有注解的文件， 生成一个className_ViewBinding.java的文件 ；例如MainActivity使用注解后生成

    MainActivity_ViewBinding.java

    
    public class MainActivity_ViewBinding implements Unbinder {
      private MainActivity target;
    
      @UiThread
      public MainActivity_ViewBinding(MainActivity target) {
        this(target, target.getWindow().getDecorView());
      }
    
      @UiThread
      public MainActivity_ViewBinding(MainActivity target, View source) {
        this.target = target;
    
        target.btn_test = Utils.findRequiredViewAsType(source, R.id.btn_test, "field 'btn_test'", Button.class);
      }
    
      @Override
      @CallSuper
      public void unbind() {
        MainActivity target = this.target;
        if (target == null) throw new IllegalStateException("Bindings already cleared.");
        this.target = null;
    
        target.btn_test = null;
      }
    }


2. Butterknife.bind() 干了什么?

        @NonNull @UiThread
        public static Unbinder bind(@NonNull Object target, @NonNull Dialog source) {
          View sourceView = source.getWindow().getDecorView();
          return createBinding(target, sourceView);
        }
        
        private static Unbinder createBinding(@NonNull Object target, @NonNull View source) {
          Class<?> targetClass = target.getClass();
          if (debug) Log.d(TAG, "Looking up binding for " + targetClass.getName());
          Constructor<? extends Unbinder> constructor = findBindingConstructorForClass(targetClass);
        
          if (constructor == null) {
            return Unbinder.EMPTY;
          }
        
          //noinspection TryWithIdenticalCatches Resolves to API 19+ only type.
          try {
            return constructor.newInstance(target, source);
          } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
          } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
          } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
              throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
              throw (Error) cause;
            }
            throw new RuntimeException("Unable to create binding instance.", cause);
          }
        }
        
        @Nullable @CheckResult @UiThread
        private static Constructor<? extends Unbinder> findBindingConstructorForClass(Class<?> cls) {
          Constructor<? extends Unbinder> bindingCtor = BINDINGS.get(cls);
          if (bindingCtor != null) {
            if (debug) Log.d(TAG, "HIT: Cached in binding map.");
            return bindingCtor;
          }
          String clsName = cls.getName();
          if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            if (debug) Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
            return null;
          }
          try {
            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "_ViewBinding");
            //noinspection unchecked
            bindingCtor = (Constructor<? extends Unbinder>) bindingClass.getConstructor(cls, View.class);
            if (debug) Log.d(TAG, "HIT: Loaded binding class and constructor.");
          } catch (ClassNotFoundException e) {
            if (debug) Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            bindingCtor = findBindingConstructorForClass(cls.getSuperclass());
          } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + clsName, e);
          }
          BINDINGS.put(cls, bindingCtor);
          return bindingCtor;
        }


通过对象获取class，通过class查询缓存 看看有没有，如果没有，就用classLoder加载一个 clsName_ViewBinding类文件，这个就是刚刚生成，然后拿到构造方法，传入布局和对象，创建clsName_ViewBinding 对象，而clsName_ViewBinding的构造方法就是进行findViewById了