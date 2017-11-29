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
   待补充
   
   
   
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