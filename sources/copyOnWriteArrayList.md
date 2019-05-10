### CopyOnWirteArrayList 原理


面试官问：“ArrayList是线程安全的吗？如果ArrayList线程不安全的话，那有没有安全的类似ArrayList的容器”

applicant：“线程安全的ArrayList我们可以使用Vector，或者说我们可以使用Collections下的方法来包装一下”

面试官继续问：“嗯，我相信你也知道Vector是一个比较老的容器了，还有没有其他的呢？”

如果你  emmmmmmm 

面试官：”ok,ok,ok,今天的面试时间也差不多了，你回去等通知吧。“ 

以上基本就没戏了


1. Vector确实是线程安全的，但是效率极低，几乎所有方法都加了锁

2. Collections.synchronizedList(new ArrayList()) 也可以确保线程安全，也是几乎都是每个方法都加上synchronized关键字的，只不过它不是加在方法的声明处，而是方法的内部


    public static <T> List<T> synchronizedList(List<T> list) {
        return (list instanceof RandomAccess ?
                new SynchronizedRandomAccessList<>(list) :
                new SynchronizedList<>(list));
    }
    
    

   调用 Collections.synchronizedList()会返回一个SynchronizedList 
   
   
   
   
    static class SynchronizedList<E>
           extends SynchronizedCollection<E>
           implements List<E> {
           private static final long serialVersionUID = -7754090372962971524L;
   
           final List<E> list;
   
           SynchronizedList(List<E> list) {
               super(list);
               this.list = list;
           }
           SynchronizedList(List<E> list, Object mutex) {
               super(list, mutex);
               this.list = list;
           }
   
           public boolean equals(Object o) {
               if (this == o)
                   return true;
               synchronized (mutex) {return list.equals(o);}
           }
           public int hashCode() {
               synchronized (mutex) {return list.hashCode();}
           }
   
           public E get(int index) {
               synchronized (mutex) {return list.get(index);}
           }
           public E set(int index, E element) {
               synchronized (mutex) {return list.set(index, element);}
           }
           public void add(int index, E element) {
               synchronized (mutex) {list.add(index, element);}
           }
           public E remove(int index) {
               synchronized (mutex) {return list.remove(index);}
           }
   
           public int indexOf(Object o) {
               synchronized (mutex) {return list.indexOf(o);}
           }
           public int lastIndexOf(Object o) {
               synchronized (mutex) {return list.lastIndexOf(o);}
           }
   
           public boolean addAll(int index, Collection<? extends E> c) {
               synchronized (mutex) {return list.addAll(index, c);}
           }
   
           public ListIterator<E> listIterator() {
               return list.listIterator(); // Must be manually synched by user
           }
   
           public ListIterator<E> listIterator(int index) {
               return list.listIterator(index); // Must be manually synched by user
           }
   
           public List<E> subList(int fromIndex, int toIndex) {
               synchronized (mutex) {
                   return new SynchronizedList<>(list.subList(fromIndex, toIndex),
                                               mutex);
               }
           }
   
           @Override
           public void replaceAll(UnaryOperator<E> operator) {
               synchronized (mutex) {list.replaceAll(operator);}
           }
           @Override
           public void sort(Comparator<? super E> c) {
               synchronized (mutex) {list.sort(c);}
           }
   
           /**
            * SynchronizedRandomAccessList instances are serialized as
            * SynchronizedList instances to allow them to be deserialized
            * in pre-1.4 JREs (which do not have SynchronizedRandomAccessList).
            * This method inverts the transformation.  As a beneficial
            * side-effect, it also grafts the RandomAccess marker onto
            * SynchronizedList instances that were serialized in pre-1.4 JREs.
            *
            * Note: Unfortunately, SynchronizedRandomAccessList instances
            * serialized in 1.4.1 and deserialized in 1.4 will become
            * SynchronizedList instances, as this method was missing in 1.4.
            */
           private Object readResolve() {
               return (list instanceof RandomAccess
                       ? new SynchronizedRandomAccessList<>(list)
                       : this);
           }
       }
       
       
   
   SynchronizedList 代理了ArrayList的所有方法，每个方法也都加入了synchronized 同步锁，所以效率也是低的
   
3. CopyOnWriteArrayList 源码分析


      public CopyOnWriteArrayList() {
            setArray(new Object[0]);
      }

      public boolean add(E e) {
            synchronized (lock) {
                Object[] elements = getArray();
                int len = elements.length;
                Object[] newElements = Arrays.copyOf(elements, len + 1);
                newElements[len] = e;
                setArray(newElements);
                return true;
            }
        }
        
        
        如果有多个调用者（callers）同时请求相同资源（如内存或磁盘上的数据存储），他们会共同获取相同的指针指向相同的资源，直到某个调用者试图修改资源的内容时，系统才会真正复制一份专用副本（private copy）给该调用者，而其他调用者所见到的最初的资源仍然保持不变。优点是如果调用者没有修改该资源，就不会有副本（private copy）被建立，因此多个调用者只是读取操作时可以共享同一份资源。
        
4. 相比Vector和SynchronizedList 遍历都加锁效率会很低，CopyOnWriteArrayList 是 只有写的时候加锁，读的时候不去加锁，这样读的时候会读老集合，


  内存占用：如果CopyOnWriteArrayList经常要增删改里面的数据，经常要执行add()、set()、remove()的话，那是比较耗费内存的。
  
  因为我们知道每次add()、set()、remove()这些增删改操作都要复制一个数组出来。
  数据一致性：CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
  
  从上面的例子也可以看出来，比如线程A在迭代CopyOnWriteArrayList容器的数据。线程B在线程A迭代的间隙中将CopyOnWriteArrayList部分的数据修改了(已经调用setArray()了)。但是线程A迭代出来的是原有的数据。
   
5. CopyOnWriteArrayList 适用于读多，写少， 数据集合小的