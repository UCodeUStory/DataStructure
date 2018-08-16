### Java8 十大特性

JAVA8 十大新特性详解
2017年03月30日 22:14:17
阅读数：13345
一、接口的默认方法
在接口中新增了default方法和static方法，这两种方法可以有方法体 
1、static方法 
示例代码：
public interface DefalutTest {
    static int a =5;
    default void defaultMethod(){
        System.out.println("DefalutTest defalut 方法");
    }

    int sub(int a,int b);

    static void staticMethod() {
        System.out.println("DefalutTest static 方法");
    }
}

接口里的静态方法，即static修饰的有方法体的方法不会被继承或者实现，但是静态变量会被继承 
例如：我们添加一个接口DefalutTest的实现类DefaultTestImpl
public class DefaultTestImpl implements DefalutTest{

    @Override
    public int sub(int a, int b) {
        // TODO Auto-generated method stub
        return a-b;
    }

}

如下图所示是这个实现类中所有可调用的方法： 


在这些方法里面我们无法找到staticMethod方法，则说明接口中的static方法不能被它的实现类直接使用。但是我们看到了defaultMethod，说明实现类可以直接调用接口中的default方法； 
那么如何使用接口中的static方法呢？？？ 
接口.static方法调用，如：DefalutTest.staticMethod();
    public static void main(String[] args) {
        DefaultTestImpl dtl = new DefaultTestImpl();
        DefalutTest.staticMethod();
    }

当我们试图使用接口的子接口去调用父接口的static方法是，我们发现，无法调用，找不到方法： 

结论：接口中的static方法不能被继承，也不能被实现类调用，只能被自身调用
2、default方法 
准备一个子接口继承DefalutTest接口
public interface SubTest extends DefalutTest{

}
 
准备一个子接口的实现类
public class SubTestImp implements SubTest{

    @Override
    public int sub(int a, int b) {
        // TODO Auto-generated method stub
        return a-b;
    }

}


现在我们创建一个子接口实现类对象，并调用对象中的default方法：
public class Main {

    public static void main(String[] args) {
        SubTestImp stl = new SubTestImp();
        stl.defaultMethod();

    }
}

执行结果： 
DefalutTest defalut 方法
结论1：default方法可以被子接口继承亦可被其实现类所调用
现在我们在子接口中重写default方法，在进行调用：
public interface SubTest extends DefalutTest{

    default void defaultMethod(){
        System.out.println("SubTest defalut 方法");
    }
}

执行结果：SubTest defalut 方法
结论2：default方法被继承时，可以被子接口覆写
现在，我们去除接口间的继承关系，并使得SubTestImp同时实现父接口和子接口，我们知道此时父接口和子接口中存在同名同参数的default方法，这会怎么样？ 
如下图所示，实现类报错，实现类要求必须指定他要实现那个接口中的default方法 

结论3：如果一个类实现了多个接口，且这些接口中无继承关系，这些接口中若有相同的（同名，同参数）的default方法，则接口实现类会报错，接口实现类必须通过特殊语法指定该实现类要实现那个接口的default方法 
特殊语法：<接口>.super.<方法名>([参数]) 
示例代码：
public class SubTestImp implements SubTest,DefalutTest{

    @Override
    public int sub(int a, int b) {
        // TODO Auto-generated method stub
        return a-b;
    }

    @Override
    public void defaultMethod() {
        // TODO Auto-generated method stub
        DefalutTest.super.defaultMethod();
    }

}
 
使用示例：
//接口代码

    interface Formula {
        double calculate(int a);
        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    //实现
    Formula formula = new Formula() {
        @Override
        public double calculate(int a) {
            return sqrt(a * 100);
        }
    };
    formula.calculate(100);     // 100.0
    formula.sqrt(16);           // 4.0
 
二、Lambda 表达式
Lambda表达式可以看成是匿名内部类，使用Lambda表达式时，接口必须是函数式接口
基本语法：
            <函数式接口>  <变量名> = (参数1，参数2...) -> {
                    //方法体
        }
 
说明： 
(参数1，参数2…)表示参数列表；->表示连接符；{}内部是方法体 
1、=右边的类型会根据左边的函数式接口类型自动推断； 
2、如果形参列表为空，只需保留()； 
3、如果形参只有1个，()可以省略，只需要参数的名称即可； 
4、如果执行语句只有1句，且无返回值，{}可以省略，若有返回值，则若想省去{}，则必须同时省略return，且执行语句也保证只有1句； 
5、形参列表的数据类型会自动推断； 
6、lambda不会生成一个单独的内部类文件； 
7、lambda表达式若访问了局部变量，则局部变量必须是final的，若是局部变量没有加final关键字，系统会自动添加，此后在修改该局部变量，会报错；
示例代码：
public interface LambdaTest {

    abstract void print();
}

public interface LambdaTest2 {

    abstract void print(String a);
}

public interface DefalutTest {

    static int a =5;
    default void defaultMethod(){
        System.out.println("DefalutTest defalut 方法");
    }

    int sub(int a,int b);

    static void staticMethod() {
        System.out.println("DefalutTest static 方法");
    }
}

public class Main {

    public static void main(String[] args) {
        //匿名内部类--java8之前的实现方式
        DefalutTest dt = new DefalutTest(){
            @Override
            public int sub(int a, int b) {
                // TODO Auto-generated method stub
                return a-b;
            }
        };

        //lambda表达式--实现方式1
        DefalutTest dt2 =(a,b)->{
            return a-b;
        };
        System.out.println(dt2.sub(2, 1));

        //lambda表达式--实现方式2，省略花括号
        DefalutTest dt3 =(a,b)->a-b;
        System.out.println(dt3.sub(5, 6));

        //测试final
        int c = 5;
        DefalutTest dt4 =(a,b)->a-c;
        System.out.println(dt4.sub(5, 6));

        //无参方法，并且执行语句只有1条
        LambdaTest lt = ()-> System.out.println("测试无参");
        lt.print();
        //只有一个参数方法
        LambdaTest2 lt1 = s-> System.out.println(s);
        lt1.print("有一个参数");
    }
}
 
局部变量修改报错如图： 


若是强行修改也无法编译通过
Lambda表达式其他特性：
1、引用实例方法： 
语法：
    <函数式接口>  <变量名> = <实例>::<实例方法名>
    //调用
    <变量名>.接口方法([实际参数...])

将调用方法时的传递的实际参数，全部传递给引用的方法，执行引用的方法； 
示例代码： 
如我们引用PrintStream类中的println方法。我们知道System类中有一个PrintStream的实例为out，引用该实例方法：System.out::println：
public class Main {

    public static void main(String[] args) {

        LambdaTest2 lt1 = s-> System.out.println(s);
        lt1.print("有一个参数");

        //改写为：
        LambdaTest2 lt2 = System.out::println;
        lt2.print("实例引用方式调用");
    }
}

将lt2调用时的实际参数传递给了PrintStream类中的println方法，并调用该方法
2、引用类方法： 
语法：
    <函数式接口>  <变量名> = <类>::<类方法名称>
    //调用
    <变量名>.接口方法([实际参数...])
 
将调用方法时的传递的实际参数，全部传递给引用的方法，执行引用的方法； 
示例代码： 
我们可以以数组排序方式为例
public interface LambdaTest3 {

     abstract void sort(List<Integer> list,Comparator<Integer> c);
}

public class Main {

    public static void main(String[] args) {
        List<Integer>  list = new ArrayList<Integer>();
        list.add(50);
        list.add(18);
        list.add(6);
        list.add(99);
        list.add(32);
        System.out.println(list.toString()+"排序之前");
        LambdaTest3 lt3 = Collections::sort;
        lt3.sort(list, (a,b) -> {
            return a-b;
        });
        System.out.println(list.toString()+"排序之后");
    }
}

 
执行结果： 
[50, 18, 6, 99, 32]排序之前 
[6, 18, 32, 50, 99]排序之后
再来看Comparator接口，它属于函数式接口，所以我们在Comparator入参时，也采取了lambda表达式写法。
@FunctionalInterface
public interface Comparator<T> {
...
...
...
}
  
3、引用类的实例方法： 
定义、调用接口时，需要多传递一个参数，并且参数的类型与引用实例的类型一致 
语法：
    //定义接口
    interface <函数式接口>{
        <返回值> <方法名>(<类><类名称>,[其他参数...]); 
    }
    <函数式接口>  <变量名> = <类>::<类实例方法名>
    //调用
    <变量名>.接口方法(类的实例,[实际参数...])
 
将调用方法时的传递的实际参数，从第二个参数开始（第一个参数指定的类的实例），全部传递给引用的方法，执行引用的方法； 
示例代码：
public class LambdaClassTest {

    public int add(int a, int b){
        System.out.println("LambdaClassTest类的add方法");
        return a+b;
    }
}

public interface LambdaTest4 {

    abstract int add(LambdaClassTest lt,int a,int b);
}

public class Main {

    public static void main(String[] args) {
        LambdaTest4 lt4 = LambdaClassTest::add;
        LambdaClassTest lct = new LambdaClassTest();
        System.out.println(lt4.add(lct, 5, 8));
    }
}
  
4、引用构造器方法： 
语法：
    <函数式接口>  <变量名> = <类>::<new>
    //调用
    <变量名>.接口方法([实际参数...])
  
把方法的所有参数全部传递给引用的构造器，根据参数类型自动推断调用的构造器方法； 
示例代码：
public interface LambdaTest5 {

    abstract String creatString(char[] c);
}
public class Main {

    public static void main(String[] args) {
        LambdaTest5 lt5 = String::new;
        System.out.println(lt5.creatString(new char[]{'1','2','3','a'}));
    }
}
  
根据传入的参数类型，自动匹配构造函数
三、函数式接口
如果一个接口只有一个抽象方法，则该接口称之为函数式接口，因为 默认方法 不算抽象方法，所以你也可以给你的函数式接口添加默认方法。 
函数式接口可以使用Lambda表达式，lambda表达式会被匹配到这个抽象方法上 
我们可以将lambda表达式当作任意只包含一个抽象方法的接口类型，确保你的接口一定达到这个要求，你只需要给你的接口添加 @FunctionalInterface 注解，编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的
示例代码：
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
Integer converted = converter.convert("123");
System.out.println(converted);    // 123
  
五、Lambda 作用域
在lambda表达式中访问外层作用域和老版本的匿名对象中的方式很相似。你可以直接访问标记了final的外层局部变量，或者实例的字段以及静态变量。
六、访问局部变量
我们可以直接在lambda表达式中访问外层的局部变量，但是该局部变量必须是final的，即使没有加final关键字，之后我们无论在哪（lambda表达式内部或外部）修改该变量，均报错。
七、访问对象字段与静态变量
lambda内部对于实例的字段以及静态变量是即可读又可写。该行为和匿名对象是一致的；
示例代码：
class Lambda4 {
    static int outerStaticNum;
    int outerNum;
    void testScopes() {
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };
        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}
 
八、访问接口的默认方法
Predicate接口 
Predicate 接口只有一个参数，返回boolean类型。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）：
    public static void main(String[] args) {
        Predicate<String> predicate = (s) -> s.length() > 0;
        System.out.println(predicate.test("foo"));              // true
        System.out.println(predicate.negate().test("foo"));     // false
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println(nonNull.test(null));
        System.out.println(isNull.test(null));
        System.out.println(isEmpty.test("sss"));
        System.out.println(isNotEmpty.test(""));
    }

运行结果： 
true 
false 
false 
true 
false 
false
Function 接口 
Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）：
        Function<String, Integer> toInteger = Integer::valueOf;
        System.out.println(toInteger.apply("123").getClass());
        Function<String, Object> toInteger2 = toInteger.andThen(String::valueOf);
        System.out.println(toInteger2.apply("123").getClass());
 
输出： 
class java.lang.Integer 
class java.lang.String
Supplier 接口 
Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
Supplier<Person> personSupplier = Person::new;
personSupplier.get();   // new Person
 
Consumer 接口
Consumer 接口表示执行在单个参数上的操作。接口只有一个参数，且无返回值
        Supplier<LambdaClassTest> personSupplier = LambdaClassTest::new;
        Consumer<LambdaClassTest> greeter = (lt) -> System.out.println("Hello, " + lt.getTest());
        greeter.accept(personSupplier.get());
  
Comparator 接口
Comparator 是老Java中的经典接口， Java 8在此之上添加了多种默认方法：
Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
Person p1 = new Person("John", "Doe");
Person p2 = new Person("Alice", "Wonderland");
comparator.compare(p1, p2);             // > 0
comparator.reversed().compare(p1, p2);  // < 0
  
Optional 接口
Optional 不是函数是接口，这是个用来防止NullPointerException异常的辅助类型，这是下一届中将要用到的重要概念，现在先简单的看看这个接口能干什么： 
Optional 被定义为一个简单的容器，其值可能是null或者不是null。在Java 8之前一般某个函数应该返回非空对象但是偶尔却可能返回了null，而在Java 8中，不推荐你返回null而是返回Optional。
Optional<String> optional = Optional.of("bam");
optional.isPresent();           // true
optional.get();                 // "bam"
optional.orElse("fallback");    // "bam"
optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"

Stream 接口 重要！！！
创建stream–通过of方法
Stream<Integer> integerStream = Stream.of(1, 2, 3, 5);
Stream<String> stringStream = Stream.of("taobao");

创建stream–通过generator方法 
生成一个无限长度的Stream，其元素的生成是通过给定的Supplier（这个接口可以看成一个对象的工厂，每次调用返回一个给定类型的对象）
Stream.generate(new Supplier<Double>() {

    @Override

    public Double get() {

        return Math.random();

    }

});

Stream.generate(() -> Math.random());

Stream.generate(Math::random);

三条语句的作用都是一样的，只是使用了lambda表达式和方法引用的语法来简化代码。每条语句其实都是生成一个无限长度的Stream，其中值是随机的。这个无限长度Stream是懒加载，一般这种无限长度的Stream都会配合Stream的limit()方法来用。
创建stream–通过iterate方法 
也是生成无限长度的Stream，和generator不同的是，其元素的生成是重复对给定的种子值(seed)调用用户指定函数来生成的。其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环 
Stream.iterate(1, item -> item + 1).limit(10).forEach(System.out::println); 
这段代码就是先获取一个无限长度的正整数集合的Stream，然后取出前10个打印。千万记住使用limit方法，不然会无限打印下去。
通过Collection子类获取Stream

public interface Collection<E> extends Iterable<E> {

    //其他方法省略

    default Stream<E> stream() {

        return StreamSupport.stream(spliterator(), false);

    }

}
  
java.util.Stream 表示能应用在一组元素上一次执行的操作序列。Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，而中间操作返回Stream本身，这样你就可以将多个操作依次串起来。Stream 的创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set， Map不支持。Stream的操作可以串行执行或者并行执行。
Java 8扩展了集合类，可以通过 Collection.stream() 或者 Collection.parallelStream() 来创建一个Stream。 
Stream有串行和并行两种，串行Stream上的操作是在一个线程中依次完成，而并行Stream则是在多个线程上同时执行。
下面的例子展示了是如何通过并行Stream来提升性能： 
首先我们创建一个没有重复元素的大表：
int max = 1000000;
List<String> values = new ArrayList<>(max);
for (int i = 0; i < max; i++) {
    UUID uuid = UUID.randomUUID();
    values.add(uuid.toString());
}

然后我们计算一下排序这个Stream要耗时多久， 
串行排序：
long t0 = System.nanoTime();
long count = values.stream().sorted().count();
System.out.println(count);
long t1 = System.nanoTime();
long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
System.out.println(String.format("sequential sort took: %d ms", millis));
  
// 串行耗时: 899 ms 
并行排序：
long t0 = System.nanoTime();
long count = values.parallelStream().sorted().count();
System.out.println(count);
long t1 = System.nanoTime();
long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
System.out.println(String.format("parallel sort took: %d ms", millis));
 
// 并行排序耗时: 472 ms 
上面两个代码几乎是一样的，但是并行版的快了50%之多，唯一需要做的改动就是将stream()改为parallelStream()；
stream的其他应用： 
1、count()、max()、min()方法
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> collection = new ArrayList<Integer>();
        collection.add(14);
        collection.add(5);
        collection.add(43);
        collection.add(89);
        collection.add(64);
        collection.add(112);
        collection.add(55);
        collection.add(55);
        collection.add(58);
        //list长度
        System.out.println(collection.parallelStream().count());

        //求最大值,返回Option,通过Option.get()获取值
        System.out.println(collection.parallelStream().max((a,b)->{return a-b;}).get());

        //求最小值,返回Option,通过Option.get()获取值
        System.out.println(collection.parallelStream().min((a,b)->{return a-b;}).get());

    }
}
  
  
2、Filter 过滤方法 
过滤通过一个predicate接口来过滤并只保留符合条件的元素，该操作属于中间操作。
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> collection = new ArrayList<Integer>();
        collection.add(14);
        collection.add(5);
        collection.add(43);
        collection.add(89);
        collection.add(64);
        collection.add(112);
        collection.add(55);
        collection.add(55);
        collection.add(58);
        Long count =collection.stream().filter(num -> num!=null).
                filter(num -> num.intValue()>50).count();
        System.out.println(count);
    }
}
 
3、distinct方法 
去除重复
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> collection = new ArrayList<Integer>();
        collection.add(14);
        collection.add(5);
        collection.add(43);
        collection.add(89);
        collection.add(64);
        collection.add(112);
        collection.add(55);
        collection.add(55);
        collection.add(58);
        collection.stream().distinct().forEach(System.out::println);;
    }
}

4、Sort 排序 
排序是一个中间操作，返回的是排序好后的Stream。如果你不指定一个自定义的Comparator则会使用默认排序。
stringCollection
    .stream()
    .sorted()
    .filter((s) -> s.startsWith("a"))
    .forEach(System.out::println);
// "aaa1", "aaa2"
 
需要注意的是，排序只创建了一个排列好后的Stream，而不会影响原有的数据源，排序之后原数据stringCollection是不会被修改的：
System.out.println(stringCollection);
// ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1
  
5、Map 映射
对于Stream中包含的元素使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素。这个方法有三个对于原始类型的变种方法，分别是：mapToInt，mapToLong和mapToDouble。这三个方法也比较好理解，比如mapToInt就是把原始Stream转换成一个新的Stream，这个新生成的Stream中的元素都是int类型。之所以会有这样三个变种方法，可以免除自动装箱/拆箱的额外消耗；
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> collection = new ArrayList<String>();
        collection.add("14");
        collection.add("5");
        collection.add("43");
        collection.add("89");
        collection.add("64");
        collection.add("112");
        collection.add("55");
        collection.add("55");
        collection.add("58");
        //将String转化为Integer类型
        collection.stream().mapToInt(Integer::valueOf).forEach(System.out::println);
        //或
        collection.stream().mapToInt(a->Integer.parseInt(a)).forEach(System.out::println);
    }
}

也可以这样用：
List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
System.out.println(“sum is:”+nums.stream().filter(num -> num != null).distinct().mapToInt(num -> num * 2).
            peek(System.out::println).skip(2).limit(4).sum());

7、limit： 
对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素；
8、skip： 
返回一个丢弃原Stream的前N个元素后剩下元素组成的新Stream，如果原Stream中包含的元素个数小于N，那么返回空Stream；
9、Match 匹配 

        Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。所有的匹配操作都是最终操作，并返回一个boolean类型的值。
        boolean anyStartsWithA = 
            stringCollection
                .stream()
                .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true
        boolean allStartsWithA = 
            stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false
        boolean noneStartsWithZ = 
            stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true
  
10、Count 计数
计数是一个最终操作，返回Stream中元素的个数，返回值类型是long。
long startsWithB = 
    stringCollection
        .stream()
        .filter((s) -> s.startsWith("b"))
        .count();
System.out.println(startsWithB);    // 3
 
11、Reduce 规约 
这是一个最终操作，允许通过指定的函数来讲stream中的多个元素规约为一个元素，规越后的结果是通过Optional接口表示的：
Optional<String> reduced =
    stringCollection
        .stream()
        .sorted()
        .reduce((s1, s2) -> s1 + "#" + s2);
reduced.ifPresent(System.out::println);
// "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"

Map 
前面提到过，Map类型不支持stream，不过Map提供了一些新的有用的方法来处理一些日常任务。
Map<Integer, String> map = new HashMap<>();
for (int i = 0; i < 10; i++) {
    map.putIfAbsent(i, "val" + i);
}
map.forEach((id, val) -> System.out.println(val));
 
以上代码很容易理解， putIfAbsent 不需要我们做额外的存在性检查，而forEach则接收一个Consumer接口来对map里的每一个键值对进行操作。 
下面的例子展示了map上的其他有用的函数：
map.computeIfPresent(3, (num, val) -> val + num);
map.get(3);             // val33
map.computeIfPresent(9, (num, val) -> null);
map.containsKey(9);     // false
map.computeIfAbsent(23, num -> "val" + num);
map.containsKey(23);    // true
map.computeIfAbsent(3, num -> "bam");
map.get(3);             // val33
  
接下来展示如何在Map里删除一个键值全都匹配的项：
map.remove(3, "val3");
map.get(3);             // val33
map.remove(3, "val33");
map.get(3);             // null
 
另外一个有用的方法：
map.getOrDefault(42, "not found");  // not found
  
对Map的元素做合并也变得很容易了：
map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
map.get(9);             // val9
map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
map.get(9);             // val9concat
 
Merge做的事情是如果键名不存在则插入，否则则对原键对应的值做合并操作并重新插入到map中。
steam在实际项目中使用的代码片段：
//1、有list集合生成以productId为key值得map集合
Map<String, List<CartManager>> cartManagerGroup =
        carts.stream().collect(
                Collectors.groupingBy(CartManager::getProductId)
        );
//2、取得购物车中数量之和
IntStream  is = list.stream().mapToInt((CartManager c)->c.getQuantity()); 
is.sum();//数量之和

//3、所有订单中商品数量*订单金额求和
orderDetailsNew.parallelStream()
                            .mapToDouble(orderDetailMid -> orderDetailMid.getQuantity()*orderDetailMid.getFinalPrice()).sum()

//4、过滤出指定类型的订单，并生成新的集合
orderDetails.stream().
    filter(orderDetail ->    StringUtil.isEmpty(orderDetail.getPromotionsType())|| !orderDetail.getPromotionsType().equals(PromotionTypeEnum.ORDERGIFTPROMOTION.getType())).collect(Collectors.toList());

//5、过滤购物车未被选中商品并生成新的list
carts.stream().filter(cart -> cart.getSelectFlag()==1).collect(Collectors.toList());

//6、将list以商品促销委key转化为map
Map<String,List<PromotionsGiftProduct>> map = 
                promotionsGiftProducts.stream().collect(                    Collectors.groupingBy(PromotionsGiftProduct::getPromotionId));

//7、从list<Cart>中分离出只存储productId的列表list<String>
List<String> productIds = needUpdate.parallelStream()
                        .map(CartManager::getProductId)
                        .collect(Collectors.toList());
 
九、Date API
Java 8 在包java.time下包含了一组全新的时间日期API。 
Clock 时钟 
Clock类提供了访问当前日期和时间的方法，Clock是时区敏感的，可以用来取代 System.currentTimeMillis() 来获取当前的微秒数。某一个特定的时间点也可以使用Instant类来表示，Instant类也可以用来创建老的java.util.Date对象。
Clock clock = Clock.systemDefaultZone();
long millis = clock.millis();
Instant instant = clock.instant();
Date legacyDate = Date.from(instant);   // legacy java.util.Date
 
Timezones 时区
System.out.println(ZoneId.getAvailableZoneIds());
// prints all available timezone ids
ZoneId zone1 = ZoneId.of("Europe/Berlin");
ZoneId zone2 = ZoneId.of("Brazil/East");
System.out.println(zone1.getRules());
System.out.println(zone2.getRules());
// ZoneRules[currentStandardOffset=+01:00]
// ZoneRules[currentStandardOffset=-03:00]
  
LocalTime 本地时间 
LocalTime 定义了一个没有时区信息的时间，例如 晚上10点，或者 17:30:15。下面的例子使用前面代码创建的时区创建了两个本地时间。之后比较时间并以小时和分钟为单位计算两个时间的时间差：
LocalTime now1 = LocalTime.now(zone1);
LocalTime now2 = LocalTime.now(zone2);
System.out.println(now1.isBefore(now2));  // false
long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
System.out.println(hoursBetween);       // -3
System.out.println(minutesBetween);     // -239
  
LocalTime 提供了多种工厂方法来简化对象的创建，包括解析时间字符串。
LocalTime late = LocalTime.of(23, 59, 59);
System.out.println(late);       // 23:59:59
DateTimeFormatter germanFormatter =
    DateTimeFormatter
        .ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.GERMAN);
LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
System.out.println(leetTime);   // 13:37
 
LocalDate 本地日期 
LocalDate 表示了一个确切的日期，比如 2014-03-11。该对象值是不可变的，用起来和LocalTime基本一致。下面的例子展示了如何给Date对象加减天/月/年。另外要注意的是这些对象是不可变的，操作返回的总是一个新实例。
LocalDate today = LocalDate.now();
LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
LocalDate yesterday = tomorrow.minusDays(2);
LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();

System.out.println(dayOfWeek);    // FRIDAY
  
从字符串解析一个LocalDate类型和解析LocalTime一样简单：
DateTimeFormatter germanFormatter =
    DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.GERMAN);
LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
System.out.println(xmas);   // 2014-12-24
  
LocalDateTime 本地日期时间 
LocalDateTime 同时表示了时间和日期，相当于前两节内容合并到一个对象上了。LocalDateTime和LocalTime还有LocalDate一样，都是不可变的。LocalDateTime提供了一些能访问具体字段的方法。
LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
System.out.println(dayOfWeek);      // WEDNESDAY
Month month = sylvester.getMonth();
System.out.println(month);          // DECEMBER
long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
System.out.println(minuteOfDay);    // 1439
 
只要附加上时区信息，就可以将其转换为一个时间点Instant对象，Instant时间点对象可以很容易的转换为老式的java.util.Date。
Instant instant = sylvester
        .atZone(ZoneId.systemDefault())
        .toInstant();
Date legacyDate = Date.from(instant);
System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014
 
格式化LocalDateTime和格式化时间和日期一样的，除了使用预定义好的格式外，我们也可以自己定义格式：
DateTimeFormatter formatter =
    DateTimeFormatter
        .ofPattern("MMM dd, yyyy - HH:mm");
LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
String string = formatter.format(parsed);
System.out.println(string);     // Nov 03, 2014 - 07:13

和java.text.NumberFormat不一样的是新版的DateTimeFormatter是不可变的，所以它是线程安全的。 
关于时间日期格式的详细信息： 
http://download.java.net/jdk8/docs/api/java/time/format/DateTimeFormatter.html
十、Annotation 注解
在Java 8中支持多重注解了