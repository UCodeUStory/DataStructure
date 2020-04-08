#### 面向对象设计七大原则

面向对象七大设计原则


1、  开闭原则

    核心思想：对扩展开放，对修改关闭。
    即在设计一个模块的时候，应当使这个模块可以在不被修改的前提下被扩展,这样兼容性更好
    
    书籍接口：
    
    public interface IBook{
        public String getName();
        public String getPrice();
        public String getAuthor();
    }
    小说类书籍：
    
    public class NovelBook implements IBook{
       private String name;
        private int price;  
        private String author;   
        public NovelBook(String name,int price,String author){ 
            this.name = name;     
            this.price = price;     
            this.author = author;
       }   
        public String getAutor(){     
            return this.author;
       }   
            
        public String getName(){
            return this.name;
       }  
     
       public int getPrice(){     
            return this.price;
       } 
    }
    Client类：
    
    public class Client{   
        public static void main(Strings[] args){
         IBook novel = new NovelBook("笑傲江湖",100,"金庸");
         System.out.println(
                "书籍名字："+novel.getName()+
                "书籍作者："+novel.getAuthor()+
                "书籍价格："+novel.getPrice()
                );
       }
    }
    项目投入使用后，书籍正常销售，但是我们经常因为各种原因，要打折来销售书籍，这是一个变化，我们要如何应对这样一个需求变化呢？
    
    我们有下面三种方法可以解决此问题：
    
    修改接口 
    在IBook接口中，增加一个方法getOffPrice(),专门用于进行打折处理，所有的实现类实现此方法。但是对于这样的一个修改方式，首先，作为接口，IBook应该稳定且可靠，不应该经常发生改变，否则接口作为契约的作用就失去了。其次，并不是所有的书籍都需要打折销售，仅仅因为NovelBook打折销售就修改接口使所有书都必须实现打折销售的逻辑，显然与实际业务不符。因此，此方案否定。
    
    修改实现类 
    修改NovelBook类的方法，直接在getPrice()方法中实现打折处理。此方法是有问题的，例如我们如果getPrice()方法中只需要读取书籍的打折前的价格呢？这不是有问题吗？当然我们也可以再增加getOffPrice()方法，这也是可以实现其需求，但是这就有二个读取价格的方法，因此，该方案也不是一个最优方案。
    
    通过扩展实现变化 
    我们可以增加一个子类OffNovelBook（继承自NovelBook）,覆写getPrice()方法。此方法修改少，对现有的代码没有影响，风险少，是最好的办法，同时也符合开闭原则。
    
    下面是修改后的类图：
    

      

2、  里氏替换原则

    在任何父类出现的地方都可以用他的子类来替代（子类应当可以替换父类，并出现在父类能够出现的任何地方）
    
    只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。
    
    
3、  单一职责原则

    核心：解耦和增强内聚性（高内聚，低耦合）
    类被修改的几率很大，因此应该专注于单一的功能。如果你把多个功能放在同一个类中，功能之间就形成了关联，
    改变其中一个功能，有可能中止另一个功能，这时就需要新一轮的测试来避免可能出现的问题。
    

4、  接口隔离原则

    核心思想：不应该强迫客户程序依赖他们不需要使用的方法。
    
    接口分离原则的意思就是：一个接口不需要提供太多的行为，一个接口应该只提供一种对外的功能，不应该把所有的操作都封装到一个接口当中.
    
    比如使用Retrofit时，我们需要定义ApiService,有时为了方便维护，把所有方法写到一个接口中，这就会再一个模块调用会出现不需要的使用的方法，这就违背了接口隔离原则
    在开发过程中也要注意取舍
    
    分离接口的两种实现方法：
    
    1.使用委托分离接口。（Separation through Delegation）
    
    2.使用多重继承分离接口。（Separation through Multiple Inheritance）

5、  依赖倒置原则

    核心：要依赖于抽象，不要依赖于具体的实现
    
    实现方式：
    1.通过构造函数传递依赖对象
    
    2.通过setter方法传递依赖对象
    
    3.接口声明实现依赖对象

6、  迪米特原则

    又叫最少知识原则
    核心思想： 一个对象应当对其他对象有尽可能少的了解,不和陌生人说话。
   
    （类间解耦，低耦合）意思就是降低各个对象之间的耦合，提高系统的可维护性；
    
    而不理会模块的内部工作原理，可以使各个模块的耦合成都降到最低，促进软件的复用
    
    个人理解，一个类的每个方法，自己干自己的事，里面尽量不要引入其他的类和对象
    
    解决方法：
    在模块之间只通过接口来通信
    
    
    
    只和朋友交流。迪米特还有一个英文解释叫做“Only talk to your immedate friends”，只和直接 的朋友通信，什么叫做直接的朋友呢？每个对象都必然会和其他对象有耦合关系，两个对象之间的耦合就 成为朋友关系，这种关系有很多比如组合、聚合、依赖等等
    
   
    /**
    * 
    * 首先来看 Teacher 有几个朋友，就一个 GroupLeader 类，这个 就是朋友类，朋友类是怎么定义的呢？
     出现在成员变量、方法的输入输出参数中的类被称为成员朋友类， 迪米特法则说是一个类只和朋友类交流，
     但是 commond 方法中我们与 Girl 类有了交流，声明了一个 List动态数组，也就是与一个陌生的类 Girl 有了交流，这个不好，
    */
    public class Teacher {
    //老师对学生发布命令, 清一下女生
        public void commond(GroupLeader groupLeader){
         List<Girl> listGirls = new ArrayList() ;
         //初始化女生
         for(int i=0;i<20;i++){
            listGirls.add(new Girl());
         }
         //告诉体育委员开始执行清查任务
         groupLeader.countGirls(listGirls);
     }
    
    // 修改后
    public class Teacher {
    //老师对学生发布命令, 清一下女生
        public void commond(GroupLeader groupLeader){
             //告诉体育委员开始执行清查任务
             groupLeader.countGirls();
        }
    }
    
    不要出现 getA().getB().getC().getD() 这种情况（在一种极端的情况下是允许出现这种访问：每一个点号后面的返回类型都相同）
    
    迪米特法则的核心观念就是类间解耦，弱耦合，只有弱耦合了以后，类的复用率才可以提高，其要求 的结果就是产生了大量的中转或跳转类，类只能和朋友交流，朋友少了你业务跑不起来，朋友多了，你项 目管理就复杂，大家在使用的时候做相互权衡吧
    
7、组合/聚合复用原则


        聚合复用
        class Classes{
        
                 privateStudent student;
        
                 publicClasses(Student student){
        
                           this.student=student;
        
                }
        
        }
        合成复用
        class House{
        
                 private Room room;
        
                 public House(){
        
                       room=new Room();
        
                  }
        
                 public void createHouse(){
        
                        room.createRoom();
        
                 }
        
          }



    为什么使用合成/聚合复用，而不使用继承复用？
    
    由于合成或聚合可以将已有对象纳入到新对象中，使之成为新对象的一部分，因此新对象可以调用已有对象的功能。这样做的好处有
    
    （1）      新对象存取成分对象的唯一方法是通过成分对象的接口。
    
    （2）      这种复用是黑箱复用，因为成分对象的内部细节是新对象看不见的。
    
    （3）      这种复用支持包装。
    
    （4）      这种复用所需的依赖较少。
    
    （5）      每一个新的类可以将焦点集中到一个任务上。
    
    （6）      这种复用可以再运行时间内动态进行，新对象可以动态地引用与成分对象类型相同的对象。
    
    一般而言，如果一个角色得到了更多的责任，那么可以使用合成/聚合关系将新的责任委派到合适的对象。当然，这种复用也有缺点。最主要的缺点就是通过这种复用建造的系统会有较多的对象需要管理。
    
    2、继承复用
    
    继承复用通过扩展一个已有对象的实现来得到新的功能，基类明显的捕获共同的属性和方法，而子类通过增加新的属性和方法来扩展超类的实现。继承是类型的复用。
    
    继承复用的优点。
    
    （1）      新的实现较为容易，因为超类的大部分功能可以通过继承关系自动进入子类。
    
    （2）      修改或扩展继承而来的实现较为容易。
    
    继承复用的缺点。
    
    （1）      继承复用破坏包装，因为继承将超类的实现细节暴露给了子类。因为超类的内部细节常常对子类是透明的，因此这种复用是透明的复用，又叫“白箱”复用。
    
    （2）      如果超类的实现改变了，那么子类的实现也不得不发生改变。因此，当一个基类发生了改变时，这种改变会传导到一级又一级的子类，使得设计师不得不相应的改变这些子类，以适应超类的变化。
    
    （3）      从超类继承而来的实现是静态的，不可能在运行时间内发生变化，因此没有足够的灵活性。
    
