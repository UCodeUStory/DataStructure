### 委托模式


- 在委托模式中，有两个对象参与处理同一个请求，接受请求的对象将请求委托给另一个对象来处理。
委托模式是一项基本技巧，许多其他的模式，如状态模式、策略模式、访问者模式 本质上是在更特殊的场合采用了委托模式。委托模式使得我们可以用聚合来替代继承


    /** 
     * 委托者接口
     *  */
    public interface Subject {
        /** 
         * 添加被委托对象
         * @param obj:被委托对象
         */
        void addObserver(Observer obj);
        /** 
         * 移除所有对象
         */
        void removeAll();
        /**
         * 委托的事件
         * @param s:委托对象   
         * @param obj:被委托对象
         * @param o：传给被委托者的数据
         */
        void event(Subject s,Observer obj,Object o);
        /**
         * 委托的很多事件
         * @param s:委托对象  
         * @param obj:被委托对象
         */
        void eventAll(Subject s,Object obj);
        /**
         * 获取唯一标识name
         * @return name:委托对象的唯一标识（名字）
         */
        String getName();
    }
    
    
    /**
     * 被委托者接口
     */
    public interface Observer {
        /**
         * 被委托者所要执行的事（方法即处理程序）
         * @param s:委托者对象
         * @param data:委托需要做的事情数据
         */
        void doEvent(Subject s,Object data);
    }
    
    import java.util.*;
    /**
     * 委托对象的类
     */
    public class Delegation implements Subject{
        /**
         * 本类对象的唯一标识name
         * @param name
         */
        @SuppressWarnings("unused")
        private String name;
        /**
         * 存储被委托者对象的集合，位于java.util包中
         */
        List<Observer> l = new ArrayList<>();
        /**
         * 构造方法
         */
        Delegation(String name){
            this.name = name;
        }
        /**
         * 添加被委托对象的方法
         * @param obj:被委托对象
         */
        @Override
        public void addObserver(Observer obj) {
            // TODO Auto-generated method stub
            if (l==null) {
                throw new NullPointerException();
            }
            else {
                if (!l.contains(obj)) {
                    l.add(obj);
                }    
            }
        }    
        /**
         * 委托的事件方法
         */
        @Override
        public void event(Subject s,Observer obj,Object o) {
            // TODO Auto-generated method stub
            obj.doEvent(s,o);
        }
        /**
         * 移除所有被委托对象
         */
        @Override
        public void removeAll() {
            // TODO Auto-generated method stub
            l.clear();
        }
        /**
         * 全部被委托者要做的事件方法
         */
        @Override
        public void eventAll(Subject s,Object obj) {
            // TODO Auto-generated method stub
            for(Observer o:l) {
                o.doEvent(s,obj);
            }
        }
        /**
         * 获取唯一标识name
         * @return name
         */
        @Override
        public String getName() {
            return name;
        }
    }
    
    /**
     * 被委托的对象的类
     */
    class A implements Observer{
        /**
         * 被委托的对象的唯一标识
         */
        private String name;
        A(String name){
            this.name=name;
        }
        /**
         * 被委托对象要做的事情
         * @param data:事情数据
         */
        @Override
        public void doEvent(Subject s,Object data) {
            // TODO Auto-generated method stub
            System.out.println(s.getName()+"你好，"+"我是"+name+"，你让我"+data+"的事已经做完了！");
        }
        
    }
    
    
    
    /**
     * 测试类，整个程序的入口
     * @author 张三
     *
     */
    public class DelegationDemo {
        
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            Delegation d = new Delegation("张三");
            A a = new A("李四");
            d.addObserver(a);
            d.event(d, a, "买早餐");
            A b = new A("王五");
            d.addObserver(b);
            d.eventAll(d, "要美女的联系方式");
        }
    }
    
    
### Java 的委托机制


java委托机制与观察者模式：委托机制的实现不再需要提取观察者抽象类，观察者和通知者互不依赖。java利用反射即可实现，代码实例如下:


        public class Event {
            private Object object;
            
            private String methodName;
            
            private Object[] params;
            
            private Class[] paramTypes;
            
            public Event(Object object,String method,Object...args)
            {
                this.object = object;
                this.methodName = method;
                this.params = args;
                contractParamTypes(this.params);
            }
            
            private void contractParamTypes(Object[] params)
            {
                this.paramTypes = new Class[params.length];
                for (int i=0;i<params.length;i++)
                {
                    this.paramTypes[i] = params[i].getClass();
                }
            }
        
            public void invoke() throws Exception
            {
                Method method = object.getClass().getMethod(this.methodName, this.paramTypes);//判断是否存在这个函数
                if (null == method)
                {
                    return;
                }
                method.invoke(this.object, this.params);//利用反射机制调用函数
            }
        }
        
        public class EventHandler {
        
            private List<Event> objects;
            
            public EventHandler()
            {
                objects = new ArrayList<Event>();
            }
            
            public void addEvent(Object object, String methodName, Object...args)
            {
                objects.add(new Event(object, methodName, args));
            }
            
            public void notifyX() throws Exception
            {
                for (Event event : objects)
                {
                    event.invoke();
                }
            }
        }
        
        
        
        public abstract class Notifier {
            private EventHandler eventHandler = new EventHandler();
            
            public EventHandler getEventHandler()
            {
                return eventHandler;
            }
            
            public void setEventHandler(EventHandler eventHandler)
            {
                this.eventHandler = eventHandler;
            }
            
            public abstract void addListener(Object object,String methodName, Object...args);
            
            public abstract void notifyX();
        
        }
        
        
        public class ConcreteNotifier extends Notifier{
        
            @Override
            public void addListener(Object object, String methodName, Object... args) {
                this.getEventHandler().addEvent(object, methodName, args);
            }
        
            @Override
            public void notifyX() {
                try {
                    this.getEventHandler().notifyX();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }
        
        // 具体观察者不在依赖任何接口 抽象
        public class WatchingTVListener {
        
            public WatchingTVListener()
            {
                System.out.println("watching TV");
            }
            
            public void stopWatchingTV(Date date) 
            {
                System.out.println("stop watching" + date);
            }
        }
        // 具体观察者不在依赖任何接口 抽象
        public class PlayingGameListener {
            public PlayingGameListener()
            {
                System.out.println("playing");
            }
            
            public void stopPlayingGame(Date date)
            {
                System.out.println("stop playing" + date);
            }
        }
        
        public class Test {
            
            public static void main (String[] args)
            {
                Notifier goodNotifier = new ConcreteNotifier();
                
                PlayingGameListener playingGameListener = new PlayingGameListener();
                
                WatchingTVListener watchingTVListener = new WatchingTVListener();
                
                goodNotifier.addListener(playingGameListener, "stopPlayingGame", new Date());
                
                goodNotifier.addListener(watchingTVListener, "stopWatchingTV", new Date());
                
                goodNotifier.notifyX();
            }
        
        }