### 听说constraintLayout用起来很难

我们的性能比较结果表明：ConstraintLayout 在测量/布局阶段的性能比 RelativeLayout大约高 40%：

 对齐属性  就是哪一条边和哪一条边对齐


    layout_constraintLeft_toLeftOf
    layout_constraintLeft_toRightOf
    layout_constraintRight_toLeftOf
    layout_constraintRight_toRightOf
    layout_constraintTop_toTopOf
    layout_constraintTop_toBottomOf
    layout_constraintBottom_toTopOf
    layout_constraintBottom_toBottomOf
    layout_constraintBaseline_toBaselineOf
    layout_constraintStart_toEndOf
    layout_constraintStart_toStartOf
    layout_constraintEnd_toStartOf
    layout_constraintEnd_toEndOf  
    
    
1. 实现一个控件水平居中或垂直居中怎么弄？
    
    - RelayoutLayout 
        - android:layout_centerInParent="true"
        - android:layout_centerVertical="true"
        - android:layout_centerHorizontal="true"
        
    - ConstraintLayout
    
        - 居中
       
              app:layout_constraintBottom_toBottomOf="parent"
              app:layout_constraintLeft_toLeftOf="parent"
              app:layout_constraintRight_toRightOf="parent"
              app:layout_constraintTop_toTopOf="parent"
                    
        - 仅水平居中
        
              app:layout_constraintLeft_toLeftOf="parent"
              app:layout_constraintRight_toRightOf="parent"      
              
        - 仅水垂直中
                
              app:layout_constraintTop_toTopOf="parent"
              app:layout_constraintBottom_toBottomOf="parent"
              
              
   