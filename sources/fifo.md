### LinkHashMap 实现FIFO


- FIFO是First Input First Output的缩写，也就是常说的先入先出，默认情况下LinkedHashMap就是按照添加顺序保存，我们只需重写下removeEldestEntry方法即可轻松实现一个FIFO缓存，简化版的实现代码如下


    final int cacheSize = 5;
    LinkedHashMap<Integer, String> lru = new LinkedHashMap<Integer, String>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > cacheSize;
        }
    };