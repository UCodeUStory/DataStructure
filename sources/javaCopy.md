### Java 克隆详解


1. 浅复制（浅克隆）这种浅复制，其实也就是把被复制的这个对象的一些变量值拿过来了。最后生成student2还是一个新的对象。
       
        
        public class CloneTest1
        {
            public static void main(String[] args) throws Exception
            {
                Student student = new Student();
                student.setAge(24);
                student.setName("niesong");
                Student student2 = (Student)student.clone();
                //这个是调用下面的那个方法，然后把这个这个对象Clone到student
                System.out.println("Age:" + student2.getAge() + " " + "Name:" + student2.getName());
                
                System.out.println("---------------------");
                student2.setAge(23);
                //克隆后得到的是一个新的对象，所以重新写的是student2这个对象的值
         
                System.out.println(student.getAge());
                System.out.println(student2.getAge());
            }
            
         
        }
//克隆的对象必须实现Cloneable这个接口，而且需要重写clone方法
 
        class Student implements Cloneable
        {
            private int age;
            //定义为private说明这个成员变量只能被被当前类中访问，如果外部需要获得，那么就只能通过getAge方法进行获取
            private String name;
            public int getAge()
            {
                return age;
            }
            public void setAge(int age)
            {
                this.age = age;
            }
            public String getName()
            {
                return name;
            }
            public void setName(String name)
            {
                this.name = name;
            }
            @Override
            public Object clone() throws CloneNotSupportedException
            {
                Object object = super.clone();
                return object;
            }
        }
2. 深复制（情况1使用的是在克隆的时候手动进行深克隆）

        
        public class CloneTest2
        {
            public static void main(String[] args) throws Exception
            {
                Teacher teacher = new Teacher();
                teacher.setAge(40);
                teacher.setName("teacher zhang");
                
                Student2 student2 = new Student2();
                student2.setAge(14);
                student2.setName("lisi");
                student2.setTeacher(teacher);
                
                Student2 student3 = (Student2)student2.clone();
                //这里是深复制，所以这时候Student2中的teacher就是teacher这个对象的一个复制，就和student3是student2的一个复制
                //所以下面teacher.setName只是对他原来的这个对象更改，但是复制的那个并没有更改
                System.out.println(student3.getAge());
                System.out.println(student3.getName());
                System.out.println(student3.getTeacher().getAge());
                teacher.setName("teacher niesong");//不会又任何影响
                System.out.println(student3.getTeacher().getName());
            
            }
         
        }
        class Student2 implements Cloneable
        {
            private int age;
            private String name;
            private Teacher teacher;
            public int getAge()
            {
                return age;
            }
            public void setAge(int age)
            {
                this.age = age;
            }
            public String getName()
            {
                return name;
            }
            public void setName(String name)
            {
                this.name = name;
            }
            public Teacher getTeacher()
            {
                return teacher;
            }
            public void setTeacher(Teacher teacher)
            {
                this.teacher = teacher;
            }
            @Override
            public Object clone() throws CloneNotSupportedException
            {
                //这一步返回的这个student2还只是一个浅克隆，
                Student2 student2 = (Student2)super.clone();
                //然后克隆的过程中获得这个克隆的student2，然后调用这个getTeacher这个方方法得到这个Teacher对象。然后实现克隆。在设置到这个student2中的Teacher。
                //这样实现了双层克隆使得那个teacher对象也得到了复制。
                student2.setTeacher((Teacher)student2.getTeacher().clone());
                //双层克隆使得那个teacher对象也得到了复制
                return student2;
            }
        }
        class Teacher implements Cloneable
        {
            private int age;
            private String name;
            public int getAge()
            {
                return age;
            }
            public void setAge(int age)
            {
                this.age = age;
            }
            public String getName()
            {
                return name;
            }
            public void setName(String name)
            {
                this.name = name;
            }
            @Override
            public Object clone() throws CloneNotSupportedException
            {
                return super.clone();
            }
            
        }
3. 利用serializable实现深复制（这个是利用Serializable，利用序列化的方式来实现深复制（深克隆），在其中利用了Io流的方式将这个对象写到IO流里面，然后在从IO流里面读取，这样就实现了一个复制，然后实现序列化的这个会将引用的那个对象也一并进行深复制，这样就实现了这个机制，同时在IO里面读取数据的时候还使用了装饰者模式）
        
        
        
        public class CloneTest3
        {
            public static void main(String[] args) throws Exception
            {
                Teacher3 teacher3 = new Teacher3();
                teacher3.setAge(23);
                teacher3.setName("niesong");
                
                Student3 student3 = new Student3();
                student3.setAge(50);
                student3.setName("wutao");
                student3.setTeacher3(teacher3);
                
                Student3 ss = (Student3)student3.deepCopt();
                System.out.println(ss.getAge());
                System.out.println(ss.getName());
                
                System.out.println("---------------------");
                System.out.println(ss.getTeacher3().getAge());
                System.out.println(ss.getTeacher3().getName());
                
                System.out.println("-----------------------");
                
                ss.getTeacher3().setAge(7777);
                ss.getTeacher3().setName("hhhhh");
                
                System.out.println(teacher3.getAge());
                System.out.println(teacher3.getName());
                //虽然上面的已经改了，但是改的是那个复制对象后的那个里面的，然后那个原来的那个里面的并没有改，下面验证：：：
                
                System.out.println("-----------------");
                
                System.out.println(ss.getTeacher3().getAge());
                System.out.println(ss.getTeacher3().getName());
                
                
            
                
                
            }
            
         
        }
        class Teacher3 implements Serializable
        {
        //  上面的那个警告可以直接消除，除了使用在设置中不显示这个警告，还可以使用下面的这两条语句中的任何一条语句
        //	这个serialVersionUID为了让该类别Serializable向后兼容
        //	private static final long serialVersionUID = 1L;
        //	private static final long serialVersionUID = 8940196742313994740L;
            private int age;
            private String name;
            public int getAge()
            {
                return age;
            }
            public void setAge(int age)
            {
                this.age = age;
            }
            public String getName()
            {
                return name;
            }
            public void setName(String name)
            {
                this.name = name;
            }
        }
            class Student3 implements Serializable
            {
                private static final long serialVersionUID = 1L;
                private int age;
                private String name;
                private Teacher3 teacher3;
                public int getAge()
                {
                    return age;
                }
                public void setAge(int age)
                {
                    this.age = age;
                }
                public String getName()
                {
                    return name;
                }
                public void setName(String name)
                {
                    this.name = name;
                }
                public Teacher3 getTeacher3()
                {
                    return teacher3;
                }
                public void setTeacher3(Teacher3 teacher3)
                {
                    this.teacher3 = teacher3;
                }
                //使得序列化student3的时候也会将teacher序列化
                public Object deepCopt()throws Exception
                {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream  oos = new ObjectOutputStream(bos);
                    oos.writeObject(this);
                    //将当前这个对象写到一个输出流当中，，因为这个对象的类实现了Serializable这个接口，所以在这个类中
                    //有一个引用，这个引用如果实现了序列化，那么这个也会写到这个输出流当中
                    
                    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    return ois.readObject();
                    //这个就是将流中的东西读出类，读到一个对象流当中，这样就可以返回这两个对象的东西，实现深克隆
                }
