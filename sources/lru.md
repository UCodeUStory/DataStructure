### LRU 算法

#### 介绍
1. LRU（Least recently used，最近最少使用）算法根据数据的历史访问记录来进行淘汰数据，其核心思想是“如果数据最近被访问过，那么将来被访问的几率也更高”。
#### 原理
2. 最常见的实现是使用一个链表保存缓存数据
    1. 新数据插入到链表头部； 
    2. 每当缓存命中（即缓存数据被访问），则将数据移到链表头部； 
    3. 当链表满的时候，将链表尾部的数据丢弃。 
#### 分析 
【命中率】 
当存在热点数据时，LRU的效率很好，但偶发性的、周期性的批量操作会导致LRU命中率急剧下降，缓存污染情况比较严重。 

命中时需要遍历链表，找到命中的数据块索引，然后需要将数据移到头部。

#### 实现3这种方式

1. 使用LinkHashMap inheritance（继承）方式

   *采用inheritance方式实现比较简单，而且实现了Map接口，在多线程环境使用时可以使用 Collections.synchronizedMap()方法实现线程安全操作，当然也可以为每个方法都手动加锁，下面就是手动为每个加锁实现方式*


    import java.util.ArrayList;  
    import java.util.Collection;  
    import java.util.LinkedHashMap;  
    import java.util.concurrent.locks.Lock;  
    import java.util.concurrent.locks.ReentrantLock;  
    import java.util.Map;  
    
    
    /** 
     * 类说明：利用LinkedHashMap实现简单的缓存， 必须实现removeEldestEntry方法，具体参见JDK文档 
     *  
     * @author qiyue 
     *  
     * @param <K> 
     * @param <V> 
     */ 
    public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {  
        private final int maxCapacity;  
    
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;  
    
        private final Lock lock = new ReentrantLock();  
    
        public LRULinkedHashMap(int maxCapacity) {  
            super(maxCapacity, DEFAULT_LOAD_FACTOR, true);  
            this.maxCapacity = maxCapacity;  
        }  
    
        @Override 
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {  
            return size() > maxCapacity;  
        }  
        @Override 
        public boolean containsKey(Object key) {  
            try {  
                lock.lock();  
                return super.containsKey(key);  
            } finally {  
                lock.unlock();  
            }  
        }  
    
    
        @Override 
        public V get(Object key) {  
            try {  
                lock.lock();  
                return super.get(key);  
            } finally {  
                lock.unlock();  
            }  
        }  
    
        @Override 
        public V put(K key, V value) {  
            try {  
                lock.lock();  
                return super.put(key, value);  
            } finally {  
                lock.unlock();  
            }  
        }  
    
        public int size() {  
            try {  
                lock.lock();  
                return super.size();  
            } finally {  
                lock.unlock();  
            }  
        }  
    
        public void clear() {  
            try {  
                lock.lock();  
                super.clear();  
            } finally {  
                lock.unlock();  
            }  
        }  
    
        public Collection<Map.Entry<K, V>> getAll() {  
            try {  
                lock.lock();  
                return new ArrayList<Map.Entry<K, V>>(super.entrySet());  
            } finally {  
                lock.unlock();  
            }  
        }  
    }
 
2. 使用LinkHashMap delegation（委托）方式

   *delegation方式实现更加优雅一些，但是由于没有实现Map接口，所以线程同步就需要自己搞定了*

        
        
        import java.util.LinkedHashMap;
        import java.util.Map;
        import java.util.Set;
        
        /**
         * Created by qiyue on 18-5-13.
         */
        public class LRUCache<K, V> {
        
            private final int MAX_CACHE_SIZE;
            private final float DEFAULT_LOAD_FACTOR = 0.75f;
            LinkedHashMap<K, V> map;
        
            public LRUCache(int cacheSize) {
                MAX_CACHE_SIZE = cacheSize;
                //根据cacheSize和加载因子计算hashmap的capactiy，+1确保当达到cacheSize上限时不会触发hashmap的扩容，
                int capacity = (int) Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1;
                map = new LinkedHashMap(capacity, DEFAULT_LOAD_FACTOR, true) {
                    @Override
                    protected boolean removeEldestEntry(Map.Entry eldest) {
                        return size() > MAX_CACHE_SIZE;
                    }
                };
            }
        
            public synchronized void put(K key, V value) {
                map.put(key, value);
            }
        
            public synchronized V get(K key) {
                return map.get(key);
            }
        
            public synchronized void remove(K key) {
                map.remove(key);
            }
        
            public synchronized Set<Map.Entry<K, V>> getAll() {
                return map.entrySet();
            }
        
            public synchronized int size() {
                return map.size();
            }
        
            public synchronized void clear() {
                map.clear();
            }
        
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry entry : map.entrySet()) {
                    sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
                }
                return sb.toString();
            }
        }

3. 一种是自己设计数据结构，使用链表+HashMap方式

       1. 自定义数据结构实现1


        import java.util.HashMap;
        
        /**
         * Created by qiyue on 18-5-12.
         */
        public class LRUCache<K, V> {
        
            private final int MAX_CACHE_SIZE;
            private Entry first;
            private Entry last;
        
            private HashMap<K, Entry<K, V>> hashMap;
        
            public LRUCache(int cacheSize) {
                MAX_CACHE_SIZE = cacheSize;
                hashMap = new HashMap<K, Entry<K, V>>();
            }
        
            public synchronized void put(K key, V value) {
                Entry entry = getEntry(key);
                if (entry == null) {
                    if (hashMap.size() >= MAX_CACHE_SIZE) {
                        hashMap.remove(last.key);
                        removeLast();
                    }
                    entry = new Entry();
                    entry.key = key;
                }
                entry.value = value;
                moveToFirst(entry);
                hashMap.put(key, entry);
            }
        
            public synchronized V get(K key) {
                Entry<K, V> entry = getEntry(key);
                if (entry == null) return null;
                moveToFirst(entry);
                return entry.value;
            }
        
            public synchronized void remove(K key) {
                Entry entry = getEntry(key);
                if (entry != null) {
                    if (entry.pre != null) entry.pre.next = entry.next;
                    if (entry.next != null) entry.next.pre = entry.pre;
                    if (entry == first) first = entry.next;
                    if (entry == last) last = entry.pre;
                }
                hashMap.remove(key);
            }
        
            private synchronized void moveToFirst(Entry entry) {
                if (entry == first) return;
                if (entry.pre != null) entry.pre.next = entry.next;
                if (entry.next != null) entry.next.pre = entry.pre;
                if (entry == last) last = last.pre;
        
                if (first == null || last == null) {
                    first = last = entry;
                    return;
                }
        
                entry.next = first;
                first.pre = entry;
                first = entry;
                entry.pre = null;
            }
        
            private synchronized void removeLast() {
                if (last != null) {
                    last = last.pre;
                    if (last == null) first = null;
                    else last.next = null;
                }
            }
        
        
            private synchronized Entry<K, V> getEntry(K key) {
                return hashMap.get(key);
            }
        
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                Entry entry = first;
                while (entry != null) {
                    sb.append(String.format("%s:%s ", entry.key, entry.value));
                    entry = entry.next;
                }
                return sb.toString();
            }
        
            class Entry<K, V> {
                public Entry pre;
                public Entry next;
                public K key;
                public V value;
            }
        }


      2. 自定义数据结构实现2：：注意下面是非线程安全的使用时需要加锁
      
      
            public class LRUCache {  
                /** 
                 * 链表节点 
                 * @author Administrator 
                 * 
                 */  
                class CacheNode {  
                    CacheNode prev;//前一节点  
                    CacheNode next;//后一节点  
                    Object value;//值  
                    Object key;//键  
                    CacheNode() {  
                    }  
                }  
              
                public LRUCache(int i) {  
                    currentSize = 0;  
                    cacheSize = i;  
                    nodes = new Hashtable(i);//缓存容器  
                }  
                  
                /** 
                 * 获取缓存中对象 
                 * @param key 
                 * @return 
                 */  
                public Object get(Object key) {  
                    CacheNode node = (CacheNode) nodes.get(key);  
                    if (node != null) {  
                        moveToHead(node);  
                        return node.value;  
                    } else {  
                        return null;  
                    }  
                }  
                  
                /** 
                 * 添加缓存 
                 * @param key 
                 * @param value 
                 */  
                public void put(Object key, Object value) {  
                    CacheNode node = (CacheNode) nodes.get(key);  
                      
                    if (node == null) {  
                        //缓存容器是否已经超过大小.  
                        if (currentSize >= cacheSize) {  
                            if (last != null)//将最少使用的删除  
                                nodes.remove(last.key);  
                            removeLast();  
                        } else {  
                            currentSize++;  
                        }  
                          
                        node = new CacheNode();  
                    }  
                    node.value = value;  
                    node.key = key;  
                    //将最新使用的节点放到链表头，表示最新使用的.  
                    moveToHead(node);  
                    nodes.put(key, node);  
                }  
              
                /** 
                 * 将缓存删除 
                 * @param key 
                 * @return 
                 */  
                public Object remove(Object key) {  
                    CacheNode node = (CacheNode) nodes.get(key);  
                    if (node != null) {  
                        if (node.prev != null) {  
                            node.prev.next = node.next;  
                        }  
                        if (node.next != null) {  
                            node.next.prev = node.prev;  
                        }  
                        if (last == node)  
                            last = node.prev;  
                        if (first == node)  
                            first = node.next;  
                    }  
                    return node;  
                }  
              
                public void clear() {  
                    first = null;  
                    last = null;  
                }  
              
                /** 
                 * 删除链表尾部节点 
                 *  表示 删除最少使用的缓存对象 
                 */  
                private void removeLast() {  
                    //链表尾不为空,则将链表尾指向null. 删除连表尾（删除最少使用的缓存对象）  
                    if (last != null) {  
                        if (last.prev != null)  
                            last.prev.next = null;  
                        else  
                            first = null;  
                        last = last.prev;  
                    }  
                }  
                  
                /** 
                 * 移动到链表头，表示这个节点是最新使用过的 
                 * @param node 
                 */  
                private void moveToHead(CacheNode node) {  
                    if (node == first)  
                        return;  
                    if (node.prev != null)  
                        node.prev.next = node.next;  
                    if (node.next != null)  
                        node.next.prev = node.prev;  
                    if (last == node)  
                        last = node.prev;  
                    if (first != null) {  
                        node.next = first;  
                        first.prev = node;  
                    }  
                    first = node;  
                    node.prev = null;  
                    if (last == null)  
                        last = first;  
                }  
                private int cacheSize;  
                private Hashtable nodes;//缓存容器  
                private int currentSize;  
                private CacheNode first;//链表头  
                private CacheNode last;//链表尾  
            } 
    
#### 以上3中方式都没有问题，看个人喜好


     