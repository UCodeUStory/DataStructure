### JDK 1.5 1.8 ConcurrentHashMap 源码剖析


#### ConcurrentHashMap 是一个并发散列映射表的实现，它允许完全并发的读取，并且支持给定数量的并发更新。相比于 HashTable 和同步包装器包装的 HashMap，使用一个全局的锁来同步不同线程间的并发访问，同一时间点，只能有一个线程持有锁，也就是说在同一时间点，只能有一个线程能访问容器，这虽然保证多线程间的安全并发访问，但同时也导致对容器的访问变成串行化的了。

1.6中采用ReentrantLock 分段锁的方式，使多个线程在不同的segment上进行写操作不会发现阻塞行为;

1.8中直接采用了内置锁synchronized，难道是因为1.8的虚拟机对内置锁已经优化的足够快了？


1.6 分段锁技术,细粒度划分锁，每一个数据一把锁
    
    public class MyConcurrentHashMap<K,V> {
        private final int LOCK_COUNT = 16;
        private final Map<K,V> map;
        private final Object[] locks ;
    
        public MyConcurrentHashMap() {
            this.map = new HashMap<K,V>();
            locks = new Object[LOCK_COUNT];
            for (int i=0;i<LOCK_COUNT;i++){
                locks[i] = new Object();
            }
        }
        
        private int keyHashCode(K k){
            return Math.abs(k.hashCode() % LOCK_COUNT);
        }
        
        public V get(K k){
            int keyHashCode = keyHashCode(k);
            synchronized (locks[keyHashCode % LOCK_COUNT]){
                return map.get(k);
            }
        }
        
    }
    
    
1.8 