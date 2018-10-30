### Java foreach原理


1. 常规写法


        for(int i=0; i<list.size; i++){
        //.....
        }

2. 简单写法


        List<String> list = new ArrayList<String>();
        for(String e : list){
        //
        }

3. foreach原理

    1. 对于list集合

            List<String> a = new ArrayList<String>();
            a.add("1");
            a.add("2");
            a.add("3");

            for(String temp : a){
               System.out.print(temp);
            }
      反编译：
             List a = new ArrayList();
             a.add("1");
             a.add("2");
             a.add("3");
             String temp;
             for(Iterator i$ = a.iterator(); i$.hasNext(); System.out.print(temp)){
                temp = (String)i$.next();
             }

      2. 遍历数组

            String[] arr = {"1","2"};
            for(String e : arr){
            System.out.println(e);
            }

      反编译后代码：

            String arr[] = { "1", "2" };
            String arr$[] = arr;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                String e = arr$[i$];
                System.out.println(e);
            }

 总结，遍历集合是对应的集合必须实现Iterator接口，遍历数组直接转成for i的形式