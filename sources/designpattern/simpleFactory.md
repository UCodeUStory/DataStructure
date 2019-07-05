### 简单工厂


1. 抽象产品类 


        public abstract class Product {
            public abstract void show();
        }

2. 创建具体产品类，继承Product类：


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

3. 创建工厂类，创建具体的产品：


        public class Factory {
            
            public static Product create(String productName) {
                Product product = null;
                //通过switch语句控制生产哪种商品
                switch (productName) {
                    case "A":
                        product = new ProductA();
                        break;
                    case "B":
                        product = new ProductB();
                        break;
                }
                return product;
            }
        }


工厂方法模式有抽象工厂类，简单工厂模式没有抽象工厂类,且其工厂类的工厂方法是静态的。

工厂方法模式新增产品时只需新建一个工厂类即可，符合开放封闭原则；而简单工厂模式需要直接修改工厂类，违反了开放封闭原则。


优化：由于简单工厂模式新增产品时需要直接修改工厂类，违反了开放封闭原则。因此可以使用反射来创建实例对象，确保能够遵循开放封闭原则。


反射实现工厂类


    public class Factory {
    
        public static <T extends Product> T create(Class<T> clz) {
            Product product = null;
            try {
                product = (Product) Class.forName(clz.getName()).newInstance();//反射出实例
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T) product;
    }

