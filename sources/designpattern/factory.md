### 工厂模式


//抽象产品类

    public abstract class Product {
        public abstract void show();
    }

 创建具体产品类，继承Product类：

    //具体产品类A 
    
    public class ProductA extends Product {
        @Override
        public void show() {
            System.out.println("product A");
        }
    }
    //具体产品类B
    
    public class ProductB extends Product {
        @Override
        public void show() {
            System.out.println("product B");
        }
    }


 //抽象工厂类
 
         public abstract class Factory {
             public abstract Product create();
         }
 
 //创建具体工厂类，继承抽象工厂类，实现创建具体的产品：
 
         //具体工厂类A
         public class FactoryA extends Factory {
             @Override
             public Product create() {
                 return new ProductA();//创建ProductA
             }
         }
         //具体工厂类B
         public class FactoryB extends Factory {
             @Override
             public Product create() {
                 return new ProductB();//创建ProductB
             }
         }

优点
    
    符合开放封闭原则。新增产品时，只需增加相应的具体产品类和相应的工厂子类即可。
    符合单一职责原则。每个具体工厂类只负责创建对应的产品。

缺点

    一个具体工厂只能创建一种具体产品。
    增加新产品时，还需增加相应的工厂类，系统类的个数将成对增加，增加了系统的复杂度和性能开销。
    引入的抽象类也会导致类结构的复杂化。
