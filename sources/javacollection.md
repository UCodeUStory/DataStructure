
### Java高级集合中难点

### WeakHashMap

### HashMap

### TreeMap

TreeMap特点

TreeMap是非线程安全的。 

可以采用这种方式将TreeMap设置为同步的：Map m = Collections.synchronizedSortedMap(new TreeMap(…));

TreeMap是用键来进行升序顺序来排序的。通过Comparable 或 Comparator来排序。 

TreeMap是SortedMap接口的基于红黑树的实现。此类保证了映射按照升序顺序排列关键字， 根据使用的构造方法不同，可能会按照键的类的自然顺序进行排序，或者按照创建时所提供的比较器（自定义）进行排序。 


