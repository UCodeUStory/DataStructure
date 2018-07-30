### onMeasure 多次调用问题

比如FlowLayout onMeasure调用四次

父视图使用unspecified dimensions来将它的每个子视图都测量一次来算出它们到底需要多大尺寸，而这些子视图没被限制的尺寸的和太大或太小，所以会用精确数值再次调用measure()（也就是说，如果子视图不满意它们获得的区域大小，那么父视图将会干涉并设置第二次测量规则）。其中measure()方法会调用onMeasure()方法。
代码中，由于把每行剩余空间重新分配，会调用了requestLayout()方法，这个方法又会导致measure()和onLayout()方法的再次调用。
最后你会发现 onMeasure()方法调用了 1次*2*2=4次  onLayout()方法调用了 1次*2 =2次




2.关于onmeasure两次调用

但是一直很疑惑UNSPECIFIED什么情况发生，今天测了下，发现在使用weigt时会调用，因为此时定义的值为0dp，这不就是UNSPECIFIED嘛！

第一次应该是通过xml的设置进行计算

 UNSPECIFIED时第一轮的onmeasure会多调用一次

 UNSPECIFIED 0--0（第一个值是传入的参数size，第二个值是测量的大小，这都是执行完super.onmeaure后打印出的）
 EXACTLY 1440--1440

第二次是在ondraw执行前再调用一次，比如

tv.post(new Runnable() {

@Override
public void run() {
MainActivity.this.tv.setText("dddd");
System.out.println("-"+tv.getMeasuredWidth());

}
});

此时第二次的onmeasure就起到了作用


3. 第一次启动的时候还会多绘制一次有可能是3次

由源代码可以看出，当newSurface为真时，performTraversals函数并不会调用performDraw函数，而是调用scheduleTraversals函数，从而再次调用一次performTraversals函数，从而再次进行一次测量，布局和绘制过程。 
 我们由断点调试可以轻易看到，第一次performTraversals时的newSurface为真，而第二次时是假。 
 