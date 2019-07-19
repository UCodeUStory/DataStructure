### 桥接设计模式

1. 介绍

桥接模式属于结构型模式。
举个生活中的例子，一条数据线，一头USB接口的可以连接电脑、充电宝等等，另一头可以连接不同品牌的手机，通过这条数据线，两头不同的东西就可以连接起来，这就是桥接模式

2. 应用场景
 
一个类存在两个或以上的独立维度的变化，且这些维度都需要进行拓展。
不希望使用继承或因为多层次继承导致类的个数急剧增加时。
如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承关系，可以通过桥接模式使他们在抽象层建立一个关联关系。

3. 优点

分离了抽象与实现。让抽象部分和实现部分独立开来，分别定义接口，这有助于对系统进行分层，从而产生更好的结构化的系统。
良好的扩展性。抽象部分和实现部分都可以分别独立扩展，不会相互影响。

4. 缺点

增加了系统的复杂性。
不容易设计，抽象与实现的分离要设计得好比较有难度

 - Android中的源码分析
 
    桥接模式在Android中的源码应用还是非常广泛的。比如AbsListView跟ListAdapter之间就是一个桥接模式。
    
    
    - AbsListView 必须是一个抽象或接口；          ListAdapter也必须是一个抽象或接口
    
    - AbsListView  依赖于  ListAdaper 一个引用
    
    - ListView GridView 都继承了AbsListView ;          ArrayAdapter    SimpleAdapter  都继承了ListAdapter
    
    - ListView GridView 和   ArrayAdapter   SimpleAdapter 之间的桥梁  就是AbsListView  和ListAdapter
    
    -       
    