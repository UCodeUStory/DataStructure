#### Java泛型总结,kotlin也是一样

    
    泛型，即“参数化类型”。一提到参数，最熟悉的就是定义方法时有形参，然后调用此方法时传递实参。那么参数化类型怎么理解呢？
    
    顾名思义，就是将类型由原来的具体的类型参数化，类似于方法中的变量参数，此时类型也定义成参数形式（可以称之为类型形参），
    
    然后在使用/调用时传入具体的类型（类型实参）。
    
    泛型的本质是为了参数化类型（在不创建新的类型的情况下，通过泛型指定的不同类型来控制形参具体限制的类型）。也就是说在泛型使用过程中，
    
    操作的数据类型被指定为一个参数，这种参数类型可以用在类、接口和方法中，分别被称为泛型类、泛型接口、泛型方法。


### 方法返回值是个泛型，对返回值的要求任何类型都可以，方法内部使用的泛型由的接受者决定，所以此方法不能单独调用

        fun <T> customTransformer(): ObservableTransformer<T, T> {
                return ObservableTransformer { upstream ->
                    upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                }
            }


### 方法参数泛型,data的类型可以是任何类型，没有做限制


fun <T> getUrl(data:T){

}


#### 类泛型
 
     class Person<T>{ 
     
     }
     
#### 泛型限制

List<? extends T> 表示List中存放的都是T或者T的子类型

    List<? extends Number> foo = new ArrayList<Number>();
    List<? extends Number> foo = new ArrayList<Integer>();
    List<? extends Number> foo = new ArrayList<Double>();

List<? super T>表示List中存放的都是T或者T的父类型
    
    List<? super Integer> foo = new ArrayList<Integer>();
    List<? super Integer> foo = new ArrayList<Number>();
    List<? super Integer> foo = new ArrayList<Object>();
    
    
    总结
    参数写成：T<? super B>，对于这个泛型，?代表容器里的元素类型，由于只规定了元素必须是B的超类，导致元素没有明确统一的“根”（除了Object这个必然的根），所以这个泛型你其实无法使用它，对吧，除了把元素强制转成Object。所以，对把参数写成这样形态的函数，你函数体内，只能对这个泛型做插入操作，而无法读
    
    参数写成： T<? extends B>，由于指定了B为所有元素的“根”，你任何时候都可以安全的用B来使用容器里的元素，但是插入有问题，由于供奉B为祖先的子树有很多，不同子树并不兼容，由于实参可能来自于任何一颗子树，所以你的插入很可能破坏函数实参，所以，对这种写法的形参，禁止做插入操作，只做读取。
    
    ？ extends T 可以读取到T对象，而不能写入T对象和T的子对象
    
    ？ super T 可以读取到Object对象，可以写入T对象和T的子对象
    若想既可以读取又可以写入，则不要用通配符，直接用T
    
    类似于消费者生产者模式
    ? extends T 只读， ？ super T 只写入
    
    
    
    public class Collections { 
            public static <T> void copy(List<? super T> dest, List<? extends T> src) {
                for (int i = 0; i < src.size(); i++) 
                    dest.set(i, src.get(i)); 
                } 
           }
        }
