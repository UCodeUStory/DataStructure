### View工作原理

1. 在ActivityThread 中Activity创建完毕后，会将DecorView添加到Window中，同时创建ViewRootImpl
对象，并将ViewRootImpl和DecorView对象建立关联；因此View的绘制也是从ViewRoot的performTravelsals方法开始

PerfromTrasal方法依次调用如下方法
 
 1. performMeasure  measure   onMeasure   如果有子类继续调用子类measure onMeasure
 
 2. performLayout   layout   onLayout   .......
 
 
 3. performDraw   draw  onDraw  .........
 
 
 
 DecorView作为顶级View其实是一个FrameLayout  它内部包含了一个LInearLayout，这个LinearLayout里面有两个部分，具体还看主题和Android版本
 
 总之一定会有一个content区域，我们设置的setContentView就是设置到这个区域内
 
 SpecMode有三种
 
 
 1. Unspecified 不做限制要多大给多大，这种情况用于系统内部
 
 2. Excalty  精确的 match_parent 或者具体数值
 
 3. AT_MOST ：不超过父容器传来的限制最大值
  
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              