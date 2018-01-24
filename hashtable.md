## 散列表详解


散列表（Hash table，也叫哈希表），是根据关键码值(Key value)而直接进行访问的数据结构。也就是说，
它通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度。这个映射函数叫做散列函数，存放记录的数组叫做散列表。


给定表M，存在函数f(key)，对任意给定的关键字值key，代入函数后若能得到包含该关键字的记录在表中的地址，
则称表M为哈希(Hash）表，函数f(key)为哈希(Hash) 函数。


### 散列函数


  A、简单计算就是组成成员的hash值直接相加即可。比如ObjectA有三个属性，propA、propB和propC，最直接的计算方式就是propA.hashcode+propB.hashcode+propC.hashcode。
 
  B、但是如果遇到有顺序相关的怎么办？比如String类型是由char数组组成，并且这些数组是有顺序的。如果使用第一种计算方法，
  则“ABCD”和“BCDA”就会产生同样的hashCode，那么怎么办呢？最直接想到的办法就是加权，不同的index加不同的权值，
  这个权值的确定最直接的方法就是某个常数值的几次幂。比如为String的计算hash值为K^0*A.hashCode+K^1*B.hashCode+K^2*C.hashCode+K^3*D.hashCode。K的选择也有说法，
  最好不要是偶数，因为偶数的相乘会造成信息的丢失（乘以2就是左移1位，一旦溢出就会造成信息的丢失，这种计算会造成溢出后的值与某个看似不相关的数值得到的结果是一样的），
  所以最好是奇数，在这一点上比较推荐使用7，因为7=8-1=2^3-1，这样计算的时候，直接左移几位再进行一次普通的加减法即可（Java中常用的是31（32-1=2^5-1））。
  
  
  
  哈希方法学
  
  哈希函数通常是由他们产生哈希值的方法来定义的，有两种主要的方法：
  1.基于加法和乘法的散列
  这种方式是通过遍历数据中的元素然后每次对某个初始值进行加操作，其中加的值和这个数据的一个元素相关。通常这对某个元素值的计算要乘以一个素数。
  基于加法和乘法的散列
   
  2.基于移位的散列 
  和加法散列类似，基于移位的散列也要利用字符串数据中的每个元素，但是和加法不同的是，后者更多的而是进行位的移位操作。通常是结合了左移和右移，移的位数的也是一个素数。每个移位过程的结果只是增加了一些积累计算，最后移位的结果作为最终结果。
  
  ## 常用的哈希函数
  
  1.RS 
  从Robert Sedgwicks的 Algorithms in C一书中得到了。我(原文作者)已经添加了一些简单的优化的算法，以加快其散列过程。
 
 
  
      public long RSHash(String str)  
         {  
            int b     = 378551;  
            int a     = 63689;  
            long hash = 0;  
            for(int i = 0; i < str.length(); i++)  
            {  
               hash = hash * a + str.charAt(i);  
               a    = a * b;  
            }  
            return hash;  
         }  
   2.JS Justin Sobel写的一个位操作的哈希函数。
 
     public long JSHash(String str)  
        {  
           long hash = 1315423911;  
           for(int i = 0; i < str.length(); i++)  
           {  
              hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));  
           }  
           return hash;  
        }  
   3.PJW 
     该散列算法是基于贝尔实验室的彼得J温伯格的的研究。在Compilers一书中（原则，技术和工具），建议采用这个算法的散列函数的哈希方法
     
     public long PJWHash(String str)  
        {  
           long BitsInUnsignedInt = (long)(4 * 8);  
           long ThreeQuarters     = (long)((BitsInUnsignedInt  * 3) / 4);  
           long OneEighth         = (long)(BitsInUnsignedInt / 8);  
           long HighBits          = (long)(0xFFFFFFFF) << (BitsInUnsignedInt - OneEighth);  
           long hash              = 0;  
           long test              = 0;  
           for(int i = 0; i < str.length(); i++)  
           {  
              hash = (hash << OneEighth) + str.charAt(i);  
              if((test = hash & HighBits)  != 0)  
              {  
                 hash = (( hash ^ (test >> ThreeQuarters)) & (~HighBits));  
              }  
           }  
           return hash;  
        }
   4..ELF 和PJW很相似，在Unix系统中使用的较多。
   
       public long ELFHash(String str)  
          {  
             long hash = 0;  
             long x    = 0;  
             for(int i = 0; i < str.length(); i++)  
             {  
                hash = (hash << 4) + str.charAt(i);  
                if((x = hash & 0xF0000000L) != 0)  
                {  
                   hash ^= (x >> 24);  
                }  
                hash &= ~x;  
             }  
             return hash;  
          }  
   5.BKDR 这个算法来自Brian Kernighan 和 Dennis Ritchie的 The C Programming Language。这是一个很简单的哈希算法,使用了一系列奇怪的数字,形式如31,3131,31...31,看上去和DJB算法很相似。(参照我之前一篇博客，这个就是Java的字符串哈希函数)
     
     public long BKDRHash(String str)  
        {  
           long seed = 131; // 31 131 1313 13131 131313 etc..  
           long hash = 0;  
           for(int i = 0; i < str.length(); i++)  
           {  
              hash = (hash * seed) + str.charAt(i);  
           }  
           return hash;  
        }  
   6.6.SDBM
     这个算法在开源的SDBM中使用，似乎对很多不同类型的数据都能得到不错的分布。
   
     public long SDBMHash(String str)  
        {  
           long hash = 0;  
           for(int i = 0; i < str.length(); i++)  
           {  
              hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;  
           }  
           return hash;  
        } 
   7.DJB
    这个算法是Daniel J.Bernstein 教授发明的，是目前公布的最有效的哈希函数。
    
    public long DJBHash(String str)  
       {  
          long hash = 5381;  
          for(int i = 0; i < str.length(); i++)  
          {  
             hash = ((hash << 5) + hash) + str.charAt(i);  
          }  
          return hash;  
       }  

   8.DEK
     由伟大的Knuth在《编程的艺术 第三卷》的第六章排序和搜索中给出。
     
     public long DEKHash(String str)  
        {  
           long hash = str.length();  
           for(int i = 0; i < str.length(); i++)  
           {  
              hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);  
           }  
           return hash;  
        }  
### 解决冲突的的方法

- 分离链接法
- 开发定址法
- - 线性探测法
- - 平方探测法
- - 双散列法
- - 再散列


### Java中标准散列表
    HashMap 和 HashTable HashSet   
    能够放入HashMap 的key 必须实现 equals和hashcode方法
    
    Map
    |--Hashtable:底层是哈希表数据结构，不可以存入null键null值，该集合是线程同步的。jdk1.0效率低
    |--HashMap：底层是哈希表数据结构，允许使用null值和null键，该集合是不同步的。jdk1.2效率高
    |--TreeMap：底层是二叉树数据结构。线程不同步，可以用于给map集合中的键进行排序。
    和Set很像。
    其实大家，Set底层就是使用了Map集合。
    
    三、HashTable和HashMap区别
          1、继承的父类不同
          Hashtable继承自Dictionary类，而HashMap继承自AbstractMap类。但二者都实现了Map接口。
          2、线程安全性不同
          javadoc中关于hashmap的一段描述如下：此实现不是同步的。如果多个线程同时访问一个哈希映射，而其中至少一个线程从结构上修改了该映射，则它必须保持外部同步。
          Hashtable 中的方法是Synchronize的，而HashMap中的方法在缺省情况下是非Synchronize的。在多线程并发的环境下，可以直接使用Hashtable，不需要自己为它的方法实现同步，但使用HashMap时就必须要自己增加同步处理。（结构上的修改是指添加或删除一个或多个映射关系的任何操作；仅改变与实例已经包含的键关联的值不是结构上的修改。）这一般通过对自然封装该映射的对象进行同步操作来完成。如果不存在这样的对象，则应该使用 Collections.synchronizedMap 方法来“包装”该映射。最好在创建时完成这一操作，以防止对映射进行意外的非同步访问，如下所示：
          Map m = Collections.synchronizedMap(new HashMap(...));
          Hashtable 线程安全很好理解，因为它每个方法中都加入了Synchronize。这里我们分析一下HashMap为什么是线程不安全的：
          HashMap底层是一个Entry数组，当发生hash冲突的时候，hashmap是采用链表的方式来解决的，在对应的数组位置存放链表的头结点。对链表而言，新加入的节点会从头结点加入。
    我们来分析一下多线程访问：
          （1）在hashmap做put操作的时候会调用下面方法：
    [java] view plain copy
    // 新增Entry。将“key-value”插入指定位置，bucketIndex是位置索引。      
        void addEntry(int hash, K key, V value, int bucketIndex) {      
            // 保存“bucketIndex”位置的值到“e”中      
            Entry<K,V> e = table[bucketIndex];      
            // 设置“bucketIndex”位置的元素为“新Entry”，      
            // 设置“e”为“新Entry的下一个节点”      
            table[bucketIndex] = new Entry<K,V>(hash, key, value, e);      
            // 若HashMap的实际大小 不小于 “阈值”，则调整HashMap的大小      
            if (size++ >= threshold)      
                resize(2 * table.length);      
        }  
          在hashmap做put操作的时候会调用到以上的方法。现在假如A线程和B线程同时对同一个数组位置调用addEntry，两个线程会同时得到现在的头结点，然后A写入新的头结点之后，B也写入新的头结点，那B的写入操作就会覆盖A的写入操作造成A的写入操作丢失
    （      2）删除键值对的代码
    [java] view plain copy
    <span style="font-size: 18px;">      </span>// 删除“键为key”的元素      
        final Entry<K,V> removeEntryForKey(Object key) {      
            // 获取哈希值。若key为null，则哈希值为0；否则调用hash()进行计算      
            int hash = (key == null) ? 0 : hash(key.hashCode());      
            int i = indexFor(hash, table.length);      
            Entry<K,V> prev = table[i];      
            Entry<K,V> e = prev;      
         
            // 删除链表中“键为key”的元素      
            // 本质是“删除单向链表中的节点”      
            while (e != null) {      
                Entry<K,V> next = e.next;      
                Object k;      
                if (e.hash == hash &&      
                    ((k = e.key) == key || (key != null && key.equals(k)))) {      
                    modCount++;      
                    size--;      
                    if (prev == e)      
                        table[i] = next;      
                    else     
                        prev.next = next;      
                    e.recordRemoval(this);      
                    return e;      
                }      
                prev = e;      
                e = next;      
            }      
         
            return e;      
        }  
          当多个线程同时操作同一个数组位置的时候，也都会先取得现在状态下该位置存储的头结点，然后各自去进行计算操作，之后再把结果写会到该数组位置去，其实写回的时候可能其他的线程已经就把这个位置给修改过了，就会覆盖其他线程的修改
          （3）addEntry中当加入新的键值对后键值对总数量超过门限值的时候会调用一个resize操作，代码如下：
    [java] view plain copy
    // 重新调整HashMap的大小，newCapacity是调整后的容量      
        void resize(int newCapacity) {      
            Entry[] oldTable = table;      
            int oldCapacity = oldTable.length;     
            //如果就容量已经达到了最大值，则不能再扩容，直接返回    
            if (oldCapacity == MAXIMUM_CAPACITY) {      
                threshold = Integer.MAX_VALUE;      
                return;      
            }      
         
            // 新建一个HashMap，将“旧HashMap”的全部元素添加到“新HashMap”中，      
            // 然后，将“新HashMap”赋值给“旧HashMap”。      
            Entry[] newTable = new Entry[newCapacity];      
            transfer(newTable);      
            table = newTable;      
            threshold = (int)(newCapacity * loadFactor);      
        }  
          这个操作会新生成一个新的容量的数组，然后对原数组的所有键值对重新进行计算和写入新的数组，之后指向新生成的数组。
          当多个线程同时检测到总数量超过门限值的时候就会同时调用resize操作，各自生成新的数组并rehash后赋给该map底层的数组table，结果最终只有最后一个线程生成的新数组被赋给table变量，其他线程的均会丢失。而且当某些线程已经完成赋值而其他线程刚开始的时候，就会用已经被赋值的table作为原始数组，这样也会有问题。
          3、是否提供contains方法
          HashMap把Hashtable的contains方法去掉了，改成containsValue和containsKey，因为contains方法容易让人引起误解。
          Hashtable则保留了contains，containsValue和containsKey三个方法，其中contains和containsValue功能相同。

    通过上面源码的比较，我们可以得到第四个不同的地方
          4、key和value是否允许null值
          其中key和value都是对象，并且不能包含重复key，但可以包含重复的value。
          通过上面的ContainsKey方法和ContainsValue的源码我们可以很明显的看出：
          Hashtable中，key和value都不允许出现null值。但是如果在Hashtable中有类似put(null,null)的操作，编译同样可以通过，因为key和value都是Object类型，但运行时会抛出NullPointerException异常，这是JDK的规范规定的。
    HashMap中，null可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为null。当get()方法返回null值时，可能是 HashMap中没有该键，也可能使该键所对应的值为null。因此，在HashMap中不能由get()方法来判断HashMap中是否存在某个键， 而应该用containsKey()方法来判断。
          5、两个遍历方式的内部实现上不同
          Hashtable、HashMap都使用了 Iterator。而由于历史原因，Hashtable还使用了Enumeration的方式 。
          6、hash值不同
          哈希值的使用不同，HashTable直接使用对象的hashCode。而HashMap重新计算hash值。
          hashCode是jdk根据对象的地址或者字符串或者数字算出来的int类型的数值。
          Hashtable计算hash值，直接用key的hashCode()，而HashMap重新计算了key的hash值，Hashtable在求hash值对应的位置索引时，用取模运算，而HashMap在求位置索引时，则用与运算，且这里一般先用hash&0x7FFFFFFF后，再对length取模，&0x7FFFFFFF的目的是为了将负的hash值转化为正值，因为hash值有可能为负数，而&0x7FFFFFFF后，只有符号外改变，而后面的位都不变。
          7、内部实现使用的数组初始化和扩容方式不同
          HashTable在不指定容量的情况下的默认容量为11，而HashMap为16，Hashtable不要求底层数组的容量一定要为2的整数次幂，而HashMap则要求一定为2的整数次幂。
          Hashtable扩容时，将容量变为原来的2倍加1，而HashMap扩容时，将容量变为原来的2倍。
          Hashtable和HashMap它们两个内部实现方式的数组的初始大小和扩容的方式。HashTable中hash数组默认大小是11，增加的方式是 old*2+1。
    
  
   LinkHashMap 与 HashMap区别
   
       HashMap 是一个最常用的Map,它根据键的HashCode 值存储数据,根据键可以直接获取它的值，具有很快的访问速度，遍历时，取得数据的顺序是完全随机的。
       
       LinkedHashMap保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的.也可以在构造时用带参数，按照应用次数排序。在遍历的时候会比HashMap慢，不过有种情况例外，当HashMap容量很大，实际数据较少时，遍历起来可能会比LinkedHashMap慢，
       因为LinkedHashMap的遍历速度只和实际数据有关，和容量无关，而HashMap的遍历速度和他的容量有关。
   
      TreeMap实现SortMap接口，能够把它保存的记录根据键排序,默认是按键值的升序排序，也可以指定排序的比较器，当用Iterator 遍历TreeMap时，得到的记录是排过序的。
      一般情况下，我们用的最多的是HashMap,HashMap里面存入的键值对在取出的时候是随机的,它根据键的HashCode值存储数据,根据键可以直接获取它的值，具有很快的访问速度。在Map 中插入、删除和定位元素，HashMap 是最好的选择。
      TreeMap取出来的是排序后的键值对。但如果您要按自然顺序或自定义顺序遍历键，那么TreeMap会更好。
      LinkedHashMap 是HashMap的一个子类，如果需要输出的顺序和输入的相同,那么用LinkedHashMap可以实现,它还可以按读取顺序来排列，像连接池中可以应用。
      
   Set集合
   
   HashSet
   HashSet有以下特点
    不能保证元素的排列顺序，顺序有可能发生变化
    不是同步的
    集合元素可以是null,但只能放入一个null
   当向HashSet集合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据 hashCode值来决定该对象在HashSet中存储位置。
   简单的说，HashSet集合判断两个元素相等的标准是两个对象通过equals方法比较相等，并且两个对象的hashCode()方法返回值相 等
   注意，如果要把一个对象放入HashSet中，重写该对象对应类的equals方法，也应该重写其hashCode()方法。其规则是如果两个对 象通过equals方法比较返回true时，其hashCode也应该相同。另外，对象中用作equals比较标准的属性，都应该用来计算 hashCode的值。
   
   TreeSet类
   TreeSet是SortedSet接口的唯一实现类，TreeSet可以确保集合元素处于排序状态。TreeSet支持两种排序方式，自然排序 和定制排序，其中自然排序为默认的排序方式。向TreeSet中加入的应该是同一个类的对象。
   TreeSet判断两个对象不相等的方式是两个对象通过equals方法返回false，或者通过CompareTo方法比较没有返回0
   自然排序
   自然排序使用要排序元素的CompareTo（Object obj）方法来比较元素之间大小关系，然后将元素按照升序排列。
   Java提供了一个Comparable接口，该接口里定义了一个compareTo(Object obj)方法，该方法返回一个整数值，实现了该接口的对象就可以比较大小。
   obj1.compareTo(obj2)方法如果返回0，则说明被比较的两个对象相等，如果返回一个正数，则表明obj1大于obj2，如果是 负数，则表明obj1小于obj2。
   如果我们将两个对象的equals方法总是返回true，则这两个对象的compareTo方法返回应该返回0
   定制排序
   自然排序是根据集合元素的大小，以升序排列，如果要定制排序，应该使用Comparator接口，实现 int compare(T o1,T o2)方法。
   
   最重要：
   
   1、TreeSet 是二差树实现的,Treeset中的数据是自动排好序的，不允许放入null值。 
   
   2、HashSet 是哈希表实现的,HashSet中的数据是无序的，可以放入null，但只能放入一个null，两者中的值都不能重复，就如数据库中唯一约束。 
   
   3、HashSet要求放入的对象必须实现HashCode()方法，放入的对象，是以hashcode码作为标识的，而具有相同内容的 String对象，hashcode是一样，所以放入的内容不能重复。但是同一个类的对象可以放入不同的实例 。
   
## 哈希是一个在现实世界中将数据映射到一个标识符的工具，下面是哈希函数的一些常用领域：

1.字符串哈希
在数据存储领域，主要是数据的索引和对容器的结构化支持，比如哈希表。
2.加密哈希
用于数据/用户核查和验证。一个强大的加密哈希函数很难从结果再得到原始数据。加密哈希函数用于哈希用户的密码，用来代替密码本身存在某个服务器撒很难过。加密哈希函数也被视为不可逆的压缩功能，能够代表一个信号标识的大量数据，可以非常有用的判断当前的数据是否已经被篡改(比如MD5)，也可以作为一个数据标志使用，以证明了通过其他手段加密文件的真实性。
3.几何哈希
这个哈希表用于在计算机视觉领域，为在任意场景分类物体的探测。
最初选择的过程涉及一个地区或感兴趣的对象。从那里使用，如Harris角检测器（HCD的），尺度不变特征变换（SIFT）或速成式的强大功能（冲浪），一组功能的仿射提取这被视为代表仿射不变特征检测算法表示对象或地区。这一套有时被称为宏观功能或功能的星座。对发现的功能的性质和类型的对象或地区被列为它可能仍然是可能的匹配两个星座的特点，即使可能有轻微的差异（如丢失或异常特征）两集。星座，然后说是功能分类设置。
哈希值是计算从星座的特性。这通常是由最初定义一个地方的哈希值是为了居住空间中完成- 在这种情况下，散列值是一个多层面的价值，定义的空间正常化。再加上计算的哈希值另一个进程，决定了两个哈希值之间的距离是必要的过程-一个距离测量是必需的，而不是一个确定性的平等经营者由于对星座的哈希值计算到了可能的差距问题。也因为简单的欧氏距离度量的本质上是无效的，其结果是自动确定特定空间的距离度量已成为学术界研究的活跃领域处理这类空间的非线性性质。
几何散列包括各种汽车分类的重新检测中任意场景的目的，典型的例子。检测水平可以多种多样，从刚检测是否是车辆，到特定型号的车辆，在特定的某个车辆。
4.布隆过滤器

布隆过滤器允许一个非常大范围内的值被一个小很多的内存锁代表。在计算机科学，这是众所周知的关联查询，并在关联容器的核心理念。
Bloom Filter的实现通过多种不同的hash函数使用，也可通过允许一个特定值的存在有一定的误差概率会员查询结果的。布隆过滤器的保证提供的是，对于任何会员国的查询就永远不会再有假阴性，但有可能是假阳性。假阳性的概率可以通过改变控制为布隆过滤器，并通过不同的hash函数的数量所使用的表的大小。
随后的研究工作集中在的散列函数和哈希表以及Mitzenmacher的布隆过滤器等领域。建议对这种结构，在数据被散列熵最实用的用法有助于哈希函数熵，这是理论成果上缔结一项最佳的布隆过滤器（一个提供给定一个最低的进一步导致假阳性的可能性表的大小或反之亦然）提供假阳性的概率定义用户可以建造最多也作为两种截然不同的两两独立的哈希散列函数已知功能，大大提高了查询效率的成员。
布隆过滤器通常存在于诸如拼写检查器，字符串匹配算法，网络数据包分析工具和网络/ Internet缓存的应用程序。