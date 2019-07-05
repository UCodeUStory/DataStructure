### 代理模式


1. 介绍


    代理模式属于结构型模式。
    代理模式也叫委托模式。
    生活中，比如代购、打官司等等，实际上都是一种代理模式。
    
    
    
    public interface People {
            
            void buy();//购买
        }
    
2. 创建真实主题类


    国内的人想购买某些产品，定义具体的购买过程：
    
        public class Domestic implements People {
    
            @Override
            public void buy() {//具体实现
                System.out.println("国内要买一个包");
            }
        }
    
3.  创建代理类
    海外的代购党需要知道是谁（持有真实主题类的引用）想购买啥产品：
    
    
         public class Oversea implements People {
            People mPeople;//持有People类的引用
    
            public Oversea(People people) {
                mPeople = people;
            }
    
            @Override
            public void buy() {
                System.out.println("我是海外代购：");
                mPeople.buy();//调用了被代理者的buy()方法,
            }
        }
    
     客户端测试：
         public void test() {
            People domestic = new Domestic();        //创建国内购买人
            People oversea = new Oversea(domestic);  //创建海外代购类并将domestic作为构造函数传递
            oversea.buy();                           //调用海外代购的buy()
        }
    
    输出结果：
    我是海外代购：
    国内要买一个包
    
 静态代理与动态代理
 
 
      从代码的角度来分，代理可以分为两种：一种是静态代理，另一种是动态代理。
      静态代理就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。上面的例子实现就是静态代理。
      动态代理类的源码是在程序运行期间根据反射等机制动态的生成，所以不存在代理类的字节码文件。代理类和委托类的关系是在程序运行时确定。
      下面我们实现动态代理，Java提供了动态的代理接口InvocationHandler，实现该接口需要重写invoke()方法：

 创建动态代理类
 
 
        public class DynamicProxy implements InvocationHandler {//实现InvocationHandler接口
            private Object obj;//被代理的对象
    
            public DynamicProxy(Object obj) {
                this.obj = obj;
            }
    
            //重写invoke()方法
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("海外动态代理调用方法： "+method.getName());
                Object result = method.invoke(obj, args);//调用被代理的对象的方法
                return result;
            }
        }
    
 修改客户端的测试方法：
 
 
        public void test() {
            People domestic = new Domestic();                                 //创建国内购买人
            DynamicProxy proxy = new DynamicProxy(domestic);                  //创建动态代理
            ClassLoader classLoader = domestic.getClass().getClassLoader();   //获取ClassLoader
            People oversea = (People) Proxy.newProxyInstance(classLoader, new Class[]{People.class}, proxy); //通过 Proxy 创建海外代购实例 ，实际上通过反射来实现的。
            oversea.buy();//调用海外代购的buy()
        }
    
    输出结果：
    海外动态代理调用方法： buy
    国内要买一个包
    
- 静态代理与动态代理比较
    静态代理的缺点：
    
    
    静态代理如果接口新增一个方法，除了所有实现类（真实主题类）需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度。
    代理对象只服务于一种类型的对象，如果要服务多类型的对象。必须要为每一种对象都进行代理，静态代理在程序规模稍大时就无法胜任了。
    
    
- 动态代理的优点：
    
    
    可以通过一个代理类完成全部的代理功能，接口中声明的所有方法都被转移到调用处理器一个集中的方法中处理（InvocationHandler.invoke）。当接口方法数量较多时，我们可以进行灵活处理，而不需要像静态代理那样每一个方法进行中转。
    动态代理的应用使我们的类职责更加单一，复用性更强。
    
    
- 动态代理的缺点：
    
    
    不能对类进行代理，只能对接口进行代理，如果我们的类没有实现任何接口，那么就不能使用这种方式进行动态代理（因为$Proxy()这个类集成了Proxy,Java的集成不允许出现多个父类）。
    
    
- 应用场景
    
    
    
    当一个对象不能或者不想直接访问另一个对象时，可以通过一个代理对象来间接访问。为保证客户端使用的透明性，委托对象和代理对象要实现同样的接口。
    被访问的对象不想暴露全部内容时，可以通过代理去掉不想被访问的内容。
    
    根据适用范围，代理模式可以分为以下几种：
    
    
    远程代理：为一个对象在不同的地址空间提供局部代表，这样系统可以将Server部分的事项隐藏。
    
    虚拟代理：如果要创建一个资源消耗较大的对象，可以先用一个代理对象表示，在真正需要的时候才真正创建。
    
    保护代理：用代理对象控制对一个对象的访问，给不同的用户提供不同的访问权限。
    
    智能引用：在引用原始对象的时候附加额外操作，并对指向原始对象的引用增加引用计数。
    
    
    
 优点
    
    代理作为调用者和真实主题的中间层,降低了模块间和系统的耦合性。
    可以以一个小对象代理一个大对象,达到优化系统提高运行速度的目的。
    代理对象能够控制调用者的访问权限，起到了保护真实主题的作用。
    
 缺点
    
    由于在调用者和真实主题之间增加了代理对象，因此可能会造成请求的处理速度变慢。
    实现代理模式需要额外的工作（有些代理模式的实现非常复杂），从而增加了系统实现的复杂度。
    
