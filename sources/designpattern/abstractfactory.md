### 抽象工厂


//抽象工厂类，电脑工厂类



    public abstract class ComputerFactory {
        public abstract CPU createCPU();

        public abstract Memory createMemory();

        public abstract HD createHD();
    }


    
    
    public class LenovoComputerFactory extends ComputerFactory {

        @Override
        public CPU createCPU() {
            return new IntelCPU();
        }

        @Override
        public Memory createMemory() {
            return new SamsungMemory();
        }

        @Override
        public HD createHD() {
            return new SeagateHD();
        }
    }
    
    //具体工厂类--华硕电脑
    public class AsusComputerFactory extends ComputerFactory {

        @Override
        public CPU createCPU() {
            return new AmdCPU();
        }

        @Override
        public Memory createMemory() {
            return new KingstonMemory();
        }

        @Override
        public HD createHD() {
            return new WdHD();
        }
    }
    
    //具体工厂类--惠普电脑
    public class HpComputerFactory extends ComputerFactory {

        @Override
        public CPU createCPU() {
            return new IntelCPU();
        }

        @Override
        public Memory createMemory() {
            return new KingstonMemory();
        }

        @Override
        public HD createHD() {
            return new WdHD();
        }
    }


生产多个产品组合的对象时。

优点

代码解耦，创建实例的工作与使用实例的工作分开，使用者不必关心类对象如何创建。

缺点

如果增加新的产品,则修改抽象工厂和所有的具体工厂,违反了开放封闭原则


工厂方法模式与抽象工厂模式比较

在工厂方法模式中具体工厂负责生产具体的产品，每一个具体工厂对应一种具体产品，工厂方法具有唯一性。
抽象工厂模式则可以提供多个产品对象，而不是单一的产品对象。
