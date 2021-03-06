#### 迭代算法和递归算法区别

举个例子：我想求1+2+3+4+..+100的值。
迭代的做法：从1到100，顺着往下累加。1+2=3,3+3=6,6+4=10,10+5=15……
            程序表示，
            int i=1,sum=0;
            while(i<=100){
                 sum = sum +i; 
            }
递归的做法：我要求1到100的累加值，如果我已经得到1到99的累加值，将这个值加上100就是1到100的累加值；要得到1到99的累加值，如果已经得到1到98的累加值，将这个值加上99，就是1到99的累加值……最后我要得到1到2的累加值，我如果得到1自身累加值，再加上2即可，1自身的累加值显然就是1了。于是现在我们得到了1到2的累加值，将这个值加3就得到了1到3的累加值，……最后直到得到1到100的累加值。
       程序表示，其中函数会调用自身，这就是递归方法的典型特征
       int GetSum(int n)
      {
           if(n<=0) return 0;
           else return n+GetSum(n-1);
      }

上述例子中，其实递归最后得到结果也是用迭代方法完成的，只是在程序的处理上直观看不出来。两者都能很好的完成计算任务，

不同之处在于思维方式上，从而导致不同的计算方法：迭代是正向思维，从头到尾思考问题；递归是逆向思维，他假设我们已经得到了部分结果(假设我已经知道了1到99的累加值，把这个值加上100我们就得到了1到100的累加值了)，从尾部追溯到头部，从而让问题简化(当然这个例子中看不出来，这里只是方便理解）