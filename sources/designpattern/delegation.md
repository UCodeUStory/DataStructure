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