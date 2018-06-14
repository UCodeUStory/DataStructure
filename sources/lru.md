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

#### 实现

1. 使用LinkedHashMap实现，LinkedHashMap有一个removeEldestEntry(Map.Entry eldest)方法，通过覆盖这个方法，
加入一定的条,当put进新的值方法返回true时，便移除该map中最老的键和值。


    import java.util.ArrayList;  
    import java.util.Collection;  
    import java.util.LinkedHashMap;  
    import java.util.concurrent.locks.Lock;  
    import java.util.concurrent.locks.ReentrantLock;  
    import java.util.Map;  
    
    
    /** 
     * 类说明：利用LinkedHashMap实现简单的缓存， 必须实现removeEldestEntry方法，具体参见JDK文档 
     *  
     * @author dennis 
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
    
2. 通过双向链表链表实现


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
     