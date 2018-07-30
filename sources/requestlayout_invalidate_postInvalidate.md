### requestLayout invalidate postInvalidate 区别和联系



1. requestLayout
当我们动态移动一个View的位置，或者View的大小、形状发生了变化的时候，我们可以在view中调用这个方法，
 会重新measure layout
 
2. invalidate 
 该方法的调用会引起View树的重绘，常用于内部调用(比如 setVisiblity())或者需要刷新界面的时候,需要在主线程(即UI线程)中调用该方法。那么我们来分析一下它的实现。 
 invalidate有多个重载方法，但最终都会调用invalidateInternal方法，在这个方法内部，进行了一系列的判断，判断View是否需要重绘，接着为该View设置标记位，然后把需要重绘的区域传递给父容器，即调用父容器的invalidateChild方法。 
 
 invalidate 回溯给父类，最终到ViewRootImpl 绘制
 
3. postInvalidate 可以在非UI想成调用
 
