### 热修复技术和原理


Dex的热修复总结
Dex的热修复目前来看基本上有四种方案：

阿里系的从native层入手，见AndFix
QQ空间的方案，插桩，见安卓App热补丁动态修复技术介绍
微信的方案，见微信Android热补丁实践演进之路，dexDiff和dexPatch，方法很牛逼，需要全量插入，但是这个全量插入的dex中需要删除一些过早加载的类，不然同样会报class is pre verified异常，还有一个缺点就是合成占内存和内置存储空间。微信读书的方式和微信类似，见Android Patch 方案与持续交付，不过微信读书是miniloader方式，启动时容易ANR，在我锤子手机上变现出来特别明显，长时间的卡图标现象。
美团的方案，也就是instant run的方案，见Android热更新方案Robust


#### 腾讯系
1. QZone
QQ空间超级补丁技术，基于虚拟机class loader动态加载，大致的过程就是：把BUG方法修复以后，放到一个单独的DEX里，应用启动后，将补丁dex插入到class loader的dexElements数组的最前面，让虚拟机优先去加载修复完后的方法。


2. Tinker
微信针对QQ空间超级补丁技术的不足提出了一个提供DEX差量包，整体替换DEX的方案。主要的原理是与QQ空间超级补丁技术基本相同，区别在于不再将patch.dex增加到elements数组中，而是差量的方式给出patch.dex，然后将patch.dex与应用的classes.dex合并，然后整体替换掉旧的DEX，达到修复的目的。



#### Tinker原理：

新dex与旧dex通过dex差分算法生成差异包 patch.dex
将patch dex下发到客户端，客户端将patch dex与旧dex合成为新的全量dex
将合成后的全量dex 插入到dex elements前面(此部分和QQ空间机制类似)，完成修复
可见，Tinker和QQ空间方案最大的不同是，Tinker 下发新旧DEX的差异包，然后将差异包和旧包合成新dex之后进行dex的全量替换，这样也就避免了QQ空间中的插桩操作。然后我们详细看下每一个流程的详细实现细节。
Tinker的差量包patch.dex是如何生成的，Tinker生成新旧dex的差异包使用了微信自研的dexdiff算法，dexdiff算法是基于dex文件的结构来设计的，首先我们详细看一下dex文件结构：
