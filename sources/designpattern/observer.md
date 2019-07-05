### 观察者模式



-  观察者抽象


       public interface Observer {//抽象观察者
            public void update(String message);//更新方法
        }



-  创建具体观察者实现抽象观察者中的方法，这里创建两个类，一个男孩类和一个女孩类，定义他们收到通知后的反应：


    public class Boy implements Observer {

        private String name;//名字
        public Boy(String name) {
            this.name = name;
        }
        @Override
        public void update(String message) {//男孩的具体反应
            System.out.println(name + ",收到了信息:" + message+"屁颠颠的去取快递.");
        }
    }

    public class Girl implements Observer {

        private String name;//名字
        public Girl(String name) {
            this.name = name;
        }
        @Override
        public void update(String message) {//女孩的具体反应
            System.out.println(name + ",收到了信息:" + message+"让男朋友去取快递~");
        }
    }

- 创建抽象主题即抽象被观察者，定义添加，删除，通知等方法：


    public interface  Observable {//抽象被观察者
         void add(Observer observer);//添加观察者
 
         void remove(Observer observer);//删除观察者
    
         void notify(String message);//通知观察者
    }
- 创建具体主题即具体被观察者，也就是快递员，派送快递时根据快递信息来通知收件人让其来取件：


    public class Postman implements  Observable{//快递员
        
        private List<Observer> personList = new ArrayList<Observer>();//保存收件人（观察者）的信息
        @Override
        public void add(Observer observer) {//添加收件人
            personList.add(observer);
        }

        @Override
        public void remove(Observer observer) {//移除收件人
            personList.remove(observer);

        }

        @Override
        public void notify(String message) {//逐一通知收件人（观察者）
            for (Observer observer : personList) {
                observer.update(message);
            }
        }
    }

- 客户端测试


    public void test(){
        Observable postman=new Postman();
        
        Observer boy1=new Boy("路飞");
        Observer boy2=new Boy("乔巴");
        Observer girl1=new Girl("娜美");

        postman.add(boy1);
        postman.add(boy2);
        postman.add(girl1);
        
        postman.notify("快递到了,请下楼领取.");
    }


- 应用场景

    
    当一个对象的改变需要通知其它对象改变时，而且它不知道具体有多少个对象有待改变时。
    当一个对象必须通知其它对象，而它又不能假定其它对象是谁
    跨系统的消息交换场景，如消息队列、事件总线的处理机制。