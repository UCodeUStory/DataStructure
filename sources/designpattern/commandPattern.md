### 命令模式

Command（命令角色）：接口或者抽象类，定义要执行的命令。

ConcreteCommand（具体命令角色）：命令角色的具体实现，通常会持有接收者，并调用接收者来处理命令。

Invoker（调用者角色）：负责调用命令对象执行请求，通常会持有命令对象（可以持有多个命令对象）。Invoker是Client真正触发命令并要求命令执行相应操作的地方（使用命令对象的入口）。

Receiver（接收者角色）：是真正执行命令的对象。任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能。

Client（客户端角色）：Client可以创建具体的命令对象，并且设置命令对象的接收者。


    Client
    
    public void test() {
            Receiver receiver = new Receiver();//创建命令接收者
            Command command = new ShutdownCommand(receiver);//创建一个命令的具体实现对象，并指定命令接收者
            Invoker invoker = new Invoker(command);//创建一个命令调用者，并指定具体命令
            invoker.action();//发起调用命令请求
    }


1. 命令模式同时也支持命令的撤销(Undo)操作和恢复(Redo)操作，比如我们平时关机时，也是可以撤销关机的。至于恢复操作，需要我们记下执行过的命令，在需要的时候重新执行一遍。

2. 优点

    调用者与接受者之间的解藕。
    易于扩展，扩展命令只需新增具体命令类即可，符合开放封闭原则。
    
3. 缺点

    过多的命令会造成过多的类，
    
    
4. Android 中Thread


     实际上Thread的使用就是一个简单的命令模式，先看下Thread的使用：
    
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //doSomeThing
                }
            }).start();
  
    
    Thread的start()方法即命令的调用者，
    
    
        @Override
        public void run() {
            if (target != null) {
                target.run();
            }
        }

    同时Thread的内部会调用Runnable的run()，这里Thread又充当了具体的命令角色，
    
    最后的Runnable则是接受者了，负责最后的功能处理。


5. Android 中Handler


    另一个比较典型的常用到命令模式就是Handler了，这里就不贴代码了，简单分析下各个类的角色：
    
    接受者：Handler，执行消息的处理操作。
    调用者：Looper，调用消息的的处理方法。
    命令角色：Message，消息类。