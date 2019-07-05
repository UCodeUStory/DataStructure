### 状态模式


**对于可以切换状态的状态模式不满足“开闭原则”的要求。**


1.概述

在软件开发过程中，应用程序可能会根据不同的情况作出不同的处理。最直接的解决方案是将这些所有可能发生的情况全都考虑到。然后使用if... ellse语句来做状态判断来进行不同情况的处理。但是对复杂状态的判断就显得“力不从心了”。随着增加新的状态或者修改一个状体（if else(或switch case)语句的增多或者修改）可能会引起很大的修改，而程序的可读性，扩展性也会变得很弱。维护也会很麻烦。那么我就考虑只修改自身状态的模式。

例子1：按钮来控制一个电梯的状态，一个电梯开们，关门，停，运行。每一种状态改变，都有可能要根据其他状态来更新处理。例如，开门状体，你不能在运行的时候开门，而是在电梯定下后才能开门。

例子2：我们给一部手机打电话，就可能出现这几种情况：用户开机，用户关机，用户欠费停机，用户消户等。 所以当我们拨打这个号码的时候：系统就要判断，该用户是否在开机且不忙状态，又或者是关机，欠费等状态。但不管是那种状态我们都应给出对应的处理操作。

2.问题

对象如何在每一种状态下表现出不同的行为？

3.解决方案

状态模式：允许一个对象在其内部状态改变时改变它的行为。对象看起来似乎修改了它的类。

在很多情况下，一个对象的行为取决于一个或多个动态变化的属性，这样的属性叫做状态，这样的对象叫做有状态的(stateful)对象，这样的对象状态是从事先定义好的一系列值中取出的。当一个这样的对象与外部事件产生互动时，其内部状态就会改变，从而使得系统的行为也随之发生变化。
4.适用性

在下面的两种情况下均可使用State模式:
if else(或switch case)语句，且这些分支依赖于该对象的状态。这个状态通常用一个或多个枚举常量表示。通常 , 有多个操作包含这一相同的条件结构。 State模式将每一个条件分支放入一个独立的类中。这使得你可以根据对象自身的情况将对象的状态作为一个对象，这一对象可以不依赖于其他对象而独立变化。
5.结构


6.模式的组成

环境类（Context）:  定义客户感兴趣的接口。维护一个ConcreteState子类的实例，这个实例定义当前状态。
抽象状态类（State）:  定义一个接口以封装与Context的一个特定状态相关的行为。
具体状态类（ConcreteState）:  每一子类实现一个与Context的一个状态相关的行为。

7.效果

State模式有下面一些效果:
状态模式的优点：
1 ) 它将与特定状态相关的行为局部化，并且将不同状态的行为分割开来: State模式将所有与一个特定的状态相关的行为都放入一个对象中。因为所有与状态相关的代码都存在于某一个State子类中, 所以通过定义新的子类可以很容易的增加新的状态和转换。另一个方法是使用数据值定义内部状态并且让 Context操作来显式地检查这些数据。但这样将会使整个Context的实现中遍布看起来很相似的条件if else语句或switch case语句。增加一个新的状态可能需要改变若干个操作, 这就使得维护变得复杂了。State模式避免了这个问题, 但可能会引入另一个问题, 因为该模式将不同状态的行为分布在多个State子类中。这就增加了子类的数目，相对于单个类的实现来说不够紧凑。但是如果有许多状态时这样的分布实际上更好一些, 否则需要使用巨大的条件语句。正如很长的过程一样，巨大的条件语句是不受欢迎的。它们形成一大整块并且使得代码不够清晰，这又使得它们难以修改和扩展。 State模式提供了一个更好的方法来组织与特定状态相关的代码。决定状态转移的逻辑不在单块的 i f或s w i t c h语句中, 而是分布在State子类之间。将每一个状态转换和动作封装到一个类中，就把着眼点从执行状态提高到整个对象的状态。这将使代码结构化并使其意图更加清晰。

2) 它使得状态转换显式化: 当一个对象仅以内部数据值来定义当前状态时 , 其状态仅表现为对一些变量的赋值，这不够明确。为不同的状态引入独立的对象使得转换变得更加明确。而且, State对象可保证Context不会发生内部状态不一致的情况，因为从 Context的角度看，状态转换是原子的—只需重新绑定一个变量(即Context的State对象变量)，而无需为多个变量赋值

3) State对象可被共享 如果State对象没有实例变量—即它们表示的状态完全以它们的类型来编码—那么各Context对象可以共享一个State对象。当状态以这种方式被共享时, 它们必然是没有内部状态, 只有行为的轻量级对象。

状态模式的缺点:
1) 状态模式的使用必然会增加系统类和对象的个数。
2) 状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱。
8.实现

我们用电梯的例子来说明：

简单地实现代码：

             
            
            abstract class ILift {  
                //电梯的四个状态  
                const OPENING_STATE = 1;  //门敞状态  
                const CLOSING_STATE = 2;  //门闭状态  
                const RUNNING_STATE = 3;  //运行状态  
                const STOPPING_STATE = 4; //停止状态；  
              
                  
                //设置电梯的状态  
                public abstract function setState($state);  
              
                //首先电梯门开启动作  
                public abstract function open();  
              
                //电梯门有开启，那当然也就有关闭了  
                public abstract function close();  
              
                //电梯要能上能下，跑起来  
                public abstract function run();  
              
                //电梯还要能停下来，停不下来那就扯淡了  
                public abstract function stop();  
            }  
              
            /** 
             * 电梯的实现类  
             */   
            class Lift extends  ILift {  
                private $state;  
              
                public function setState($state) {  
                    $this->state = $state;  
                }  
                //电梯门关闭  
                public function close() {  
                    //电梯在什么状态下才能关闭  
                    switch($this->state){  
                        case ILift::OPENING_STATE:  //如果是则可以关门，同时修改电梯状态  
                            $this->setState(ILift::CLOSING_STATE);  
                        break;  
                        case ILift::CLOSING_STATE:  //如果电梯就是关门状态，则什么都不做  
                            //do nothing;  
                            return ;  
                        break;  
                        case ILift::RUNNING_STATE: //如果是正在运行，门本来就是关闭的，也说明都不做  
                            //do nothing;  
                            return ;  
                        break;  
                        case ILift::STOPPING_STATE:  //如果是停止状态，本也是关闭的，什么也不做  
                            //do nothing;  
                            return ;  
                        break;  
                    }  
                            echo 'Lift colse <br>';  
                }  
              
                //电梯门开启  
                public function open() {  
                    //电梯在什么状态才能开启  
                    switch($this->state){  
                        case ILift::OPENING_STATE: //如果已经在门敞状态，则什么都不做  
                            //do nothing;  
                            return ;  
                        break;  
                        case ILift::CLOSING_STATE: //如是电梯时关闭状态，则可以开启  
                            $this->setState(ILift::OPENING_STATE);  
                        break;  
                        case ILift::RUNNING_STATE: //正在运行状态，则不能开门，什么都不做  
                        //do nothing;  
                            return ;  
                        break;  
                        case ILift::STOPPING_STATE: //停止状态，淡然要开门了  
                            $this->setState(ILift::OPENING_STATE);  
                        break;  
                    }  
                    echo 'Lift open <br>';  
                }  
                ///电梯开始跑起来  
                public function run() {  
                    switch($this->state){  
                        case ILift::OPENING_STATE: //如果已经在门敞状态，则不你能运行，什么都不做  
                            //do nothing;  
                            return ;  
                        break;  
                        case ILift::CLOSING_STATE: //如是电梯时关闭状态，则可以运行  
                            $this->setState(ILift::RUNNING_STATE);  
                        break;  
                        case ILift::RUNNING_STATE: //正在运行状态，则什么都不做  
                            //do nothing;  
                            return ;  
                        break;  
                        case ILift::STOPPING_STATE: //停止状态，可以运行  
                            $this->setState(ILift::RUNNING_STATE);  
                    }  
                    echo 'Lift run <br>';  
                }  
              
                //电梯停止  
                public function stop() {  
                    switch($this->state){  
                        case ILift::OPENING_STATE: //如果已经在门敞状态，那肯定要先停下来的，什么都不做  
                            //do nothing;  
                            return ;  
                        break;  
                        case ILift::CLOSING_STATE: //如是电梯时关闭状态，则当然可以停止了  
                            $this->setState(ILift::CLOSING_STATE);  
                        break;  
                        case ILift::RUNNING_STATE: //正在运行状态，有运行当然那也就有停止了  
                            $this->setState(ILift::CLOSING_STATE);  
                        break;  
                        case ILift::STOPPING_STATE: //停止状态，什么都不做  
                            //do nothing;  
                            return ;  
                        break;  
                    }  
                    echo 'Lift stop <br>';  
                }  
                  
            }  
            $lift = new Lift();   
                 
            //电梯的初始条件应该是停止状态   
            $lift->setState(ILift::STOPPING_STATE);   
            //首先是电梯门开启，人进去   
            $lift->open();   
                 
            //然后电梯门关闭   
            $lift->close();   
                 
            //再然后，电梯跑起来，向上或者向下   
            $lift->run();      
             //最后到达目的地，电梯挺下来   
            $lift->stop();  
            显然我们已经完成了我们的基本业务操作，但是，我们在程序中使用了大量的switch…case这样的判断（if…else也是一样),首先是程序的可阅读性很差，其次扩展非常不方便。一旦我们有新的状态加入的话，例如新加通电和断点状态。我们势必要在每个业务方法里边增加相应的case语句。也就是四个函数open，close，run，stop都需要修改相应case语句。
            
            状态模式：把不同状态的操作分散到不同的状态对象里去完成。看看状态类的uml类图：
            

     
    /** 
     *  
     * 定义一个电梯的接口  
     */   
    abstract class LiftState{  
      
        //定义一个环境角色，也就是封装状态的变换引起的功能变化  
        protected  $_context;  
      
        public function setContext(Context $context){  
            $this->_context = $context;  
        }  
      
        //首先电梯门开启动作  
        public abstract function open();  
      
        //电梯门有开启，那当然也就有关闭了  
        public abstract function close();  
      
        //电梯要能上能下，跑起来  
        public abstract function run();  
      
        //电梯还要能停下来，停不下来那就扯淡了  
        public abstract function stop();  
      
    }  
      
      
    /** 
     * 环境类:定义客户感兴趣的接口。维护一个ConcreteState子类的实例，这个实例定义当前状态。 
     */   
    class Context {  
        //定义出所有的电梯状态  
        static  $openningState = null;  
        static  $closeingState = null;  
        static  $runningState  = null;  
        static  $stoppingState = null;  
      
        public function __construct() {  
            self::$openningState = new OpenningState();  
            self::$closeingState = new ClosingState();  
            self::$runningState =  new RunningState();  
            self::$stoppingState = new StoppingState();  
      
        }  
      
        //定一个当前电梯状态  
        private  $_liftState;  
      
        public function getLiftState() {  
            return $this->_liftState;  
        }  
      
        public function setLiftState($liftState) {  
            $this->_liftState = $liftState;  
            //把当前的环境通知到各个实现类中  
            $this->_liftState->setContext($this);  
        }  
      
      
        public function open(){  
            $this->_liftState->open();  
        }  
      
        public function close(){  
            $this->_liftState->close();  
        }  
      
        public function run(){  
            $this->_liftState->run();  
        }  
      
        public function stop(){  
            $this->_liftState->stop();  
        }  
    }  
      
    /** 
     * 在电梯门开启的状态下能做什么事情  
     */   
    class OpenningState extends LiftState {  
      
        /** 
         * 开启当然可以关闭了，我就想测试一下电梯门开关功能 
         * 
         */  
        public function close() {  
            //状态修改  
            $this->_context->setLiftState(Context::$closeingState);  
            //动作委托为CloseState来执行  
            $this->_context->getLiftState()->close();  
        }  
      
        //打开电梯门  
        public function open() {  
            echo 'lift open...', '<br/>';  
        }  
        //门开着电梯就想跑，这电梯，吓死你！  
        public function run() {  
            //do nothing;  
        }  
      
        //开门还不停止？  
        public function stop() {  
            //do nothing;  
        }  
      
    }  
      
    /** 
     * 电梯门关闭以后，电梯可以做哪些事情  
     */   
    class ClosingState extends LiftState {  
      
        //电梯门关闭，这是关闭状态要实现的动作  
        public function close() {  
            echo 'lift close...', '<br/>';  
      
        }  
        //电梯门关了再打开，逗你玩呢，那这个允许呀  
        public function open() {  
            $this->_context->setLiftState(Context::$openningState);  //置为门敞状态  
            $this->_context->getLiftState()->open();  
        }  
      
        //电梯门关了就跑，这是再正常不过了  
        public function run() {  
            $this->_context->setLiftState(Context::$runningState); //设置为运行状态；  
            $this->_context->getLiftState()->run();  
        }  
      
        //电梯门关着，我就不按楼层  
          
        public function stop() {  
            $this->_context->setLiftState(Context::$stoppingState);  //设置为停止状态；  
            $this->_context->getLiftState()->stop();  
        }  
      
    }  
      
    /** 
     * 电梯在运行状态下能做哪些动作  
     */   
    class RunningState extends LiftState {  
      
        //电梯门关闭？这是肯定了  
        public function close() {  
            //do nothing  
        }  
      
        //运行的时候开电梯门？你疯了！电梯不会给你开的  
        public function open() {  
            //do nothing  
        }  
      
        //这是在运行状态下要实现的方法  
        public function run() {  
            echo 'lift run...', '<br/>';  
        }  
      
        //这个事绝对是合理的，光运行不停止还有谁敢做这个电梯？！估计只有上帝了  
        public function stop() {  
            $this->_context->setLiftState(Context::$stoppingState); //环境设置为停止状态；  
            $this->_context->getLiftState()->stop();  
        }  
      
    }  
      
      
      
    /** 
     * 在停止状态下能做什么事情  
     */   
    class StoppingState extends LiftState {  
      
        //停止状态关门？电梯门本来就是关着的！  
        public function close() {  
            //do nothing;  
        }  
      
        //停止状态，开门，那是要的！  
        public function open() {  
            $this->_context->setLiftState(Context::$openningState);  
            $this->_context->getLiftState()->open();  
        }  
        //停止状态再跑起来，正常的很  
        public function run() {  
            $this->_context->setLiftState(Context::$runningState);  
            $this->_context->getLiftState()->run();  
        }  
        //停止状态是怎么发生的呢？当然是停止方法执行了  
        public function stop() {  
            echo 'lift stop...', '<br/>';  
        }  
      
    }  
      
    /** 
     * 模拟电梯的动作  
     */   
    class Client {  
      
        public static function main() {  
            $context = new Context();  
            $context->setLiftState(new ClosingState());  
      
            $context->open();  
            $context->close();  
            $context->run();  
            $context->stop();  
        }  
    }  
    Client::main();  
    
    
    
9.与其他相关模式

1）职责链模式，
职责链模式和状态模式都可以解决If分支语句过多，
从定义来看，状态模式是一个对象的内在状态发生改变（一个对象，相对比较稳定，处理完一个对象下一个对象的处理一般都已确定），
而职责链模式是多个对象之间的改变（多个对象之间的话，就会出现某个对象不存在的现在，就像我们举例的公司请假流程，经理可能不在公司情况），这也说明他们两个模式处理的情况不同。
这两个设计模式最大的区别就是状态模式是让各个状态对象自己知道其下一个处理的对象是谁。
而职责链模式中的各个对象并不指定其下一个处理的对象到底是谁，只有在客户端才设定。
用我们通俗的编程语言来说，就是
状态模式：
  相当于If else if else；

  设计路线：各个State类的内部实现(相当于If，else If内的条件)

  执行时通过State调用Context方法来执行。

职责链模式：

  相当于Swich case

  设计路线：客户设定，每个子类(case)的参数是下一个子类(case)。

  使用时，向链的第一个子类的执行方法传递参数就可以。

就像对设计模式的总结，有的人采用的是状态模式，从头到尾，提前一定定义好下一个处理的对象是谁，而我采用的是职责链模式，随时都有可能调整链的顺序。

10.总结与分析

       状态模式的主要优点在于封装了转换规则，并枚举可能的状态，它将所有与某个状态有关的行为放到一个类中，并且可以方便地增加新的状态，只需要改变对象状态即可改变对象的行为，还可以让多个环境对象共享一个状态对象，从而减少系统中对象的个数；其缺点在于使用状态模式会增加系统类和对象的个数，且状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱，对于可以切换状态的状态模式不满足“开闭原则”的要求。